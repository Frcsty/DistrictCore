package com.github.frcsty.districtcore.plugins.tools.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.*;

import java.util.*;

public class Condense {

    private final Map<ItemStack, SimpleRecipe> condenseList = new HashMap<>();

    public void run(final Block block) {
        Map<Material, Integer> is = new HashMap<>();
        final Inventory inventory = ((InventoryHolder) block.getState()).getInventory();


        for (ItemStack stack : inventory.getContents()) {
            if (stack == null || stack.getType() == Material.AIR) {
                continue;
            }
            is.put(stack.getType(), is.getOrDefault(stack.getType(), 0) + stack.getAmount());
        }

        for (final Material itemStack : is.keySet()) {
            condenseStack(inventory, new ItemStack(itemStack, is.get(itemStack)));
        }
        block.getState().update();
    }

    private void condenseStack(final Inventory inventory, final ItemStack stack) {
        final SimpleRecipe condenseType = getCondenseType(stack);
        if (condenseType != null) {
            final ItemStack input = condenseType.getInput();
            final ItemStack result = condenseType.getResult();

            boolean pass = false;
            for (Recipe revRecipe : Bukkit.getServer().getRecipesFor(input)) {
                if (getStackOnRecipeMatch(revRecipe, result) != null) {
                    pass = true;
                    break;
                }
            }
            if (!pass) {
                return;
            }

            int amount = 0;
            int excess = 0;

            for (final ItemStack contents : inventory.getContents()) {
                if (contents != null && contents.isSimilar(stack)) {
                    inventory.remove(contents);
                    excess += contents.getAmount();
                    amount += contents.getAmount();
                }
            }

            int output = ((amount / input.getAmount()) * result.getAmount());
            amount -= amount % input.getAmount();
            excess = stack.getAmount() - amount;

            if (amount > 0) {
                if (excess > 0) {
                    input.setAmount(excess);
                    inventory.addItem(input);
                }
                result.setAmount(output);
                inventory.addItem(result);
            }
        }
    }

    private SimpleRecipe getCondenseType(final ItemStack stack) {
        if (condenseList.containsKey(stack)) {
            return condenseList.get(stack);
        }

        final Iterator<Recipe> intr = Bukkit.getServer().recipeIterator();
        List<SimpleRecipe> bestRecipes = new ArrayList<>();
        while (intr.hasNext()) {
            final Recipe recipe = intr.next();
            final Collection<ItemStack> recipeItems = getStackOnRecipeMatch(recipe, stack);

            if (recipeItems != null && (recipeItems.size() == 4 || recipeItems.size() == 9) && (recipeItems.size() > recipe.getResult().getAmount())) {
                final ItemStack input = stack.clone();
                input.setAmount(recipeItems.size());
                final SimpleRecipe newRecipe = new SimpleRecipe(recipe.getResult(), input);
                bestRecipes.add(newRecipe);
            }
        }
        if (!bestRecipes.isEmpty()) {
            if (bestRecipes.size() > 1) {
                bestRecipes.sort(SimpleRecipeComparator.INSTANCE);
            }
            SimpleRecipe recipe = bestRecipes.get(0);
            condenseList.put(stack, recipe);
            return recipe;
        }
        condenseList.put(stack, null);
        return null;
    }

    private Collection<ItemStack> getStackOnRecipeMatch(final Recipe recipe, final ItemStack stack) {
        final Collection<ItemStack> inputList;

        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe sRecipe = (ShapedRecipe) recipe;
            if (sRecipe.getShape().length != sRecipe.getShape()[0].length()) {
                // Only accept square recipes
                return null;
            }
            inputList = sRecipe.getIngredientMap().values();
        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe slRecipe = (ShapelessRecipe) recipe;
            inputList = slRecipe.getIngredientList();
        } else {
            return null;
        }

        boolean match = true;
        Iterator<ItemStack> iter = inputList.iterator();
        while (iter.hasNext()) {
            ItemStack inputSlot = iter.next();
            if (inputSlot == null) {
                iter.remove();
                continue;
            }

            if (inputSlot.getDurability() == Short.MAX_VALUE) {
                inputSlot.setDurability((short) 0);
            }
            if (!inputSlot.isSimilar(stack)) {
                match = false;
            }
        }

        if (match) {
            return inputList;
        }
        return null;
    }

    private static class SimpleRecipe implements Recipe {
        private final ItemStack result;
        private final ItemStack input;

        private SimpleRecipe(ItemStack result, ItemStack input) {
            this.result = result;
            this.input = input;
        }

        @Override
        public ItemStack getResult() {
            return result.clone();
        }

        ItemStack getInput() {
            return input.clone();
        }
    }

    private static class SimpleRecipeComparator implements Comparator<SimpleRecipe> {

        private static final SimpleRecipeComparator INSTANCE = new SimpleRecipeComparator();

        @Override
        public int compare(SimpleRecipe o1, SimpleRecipe o2) {
            return Integer.compare(o2.getInput().getAmount(), o1.getInput().getAmount());
        }
    }
}
