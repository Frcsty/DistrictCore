package com.github.frcsty.districtcore.collectors.collector.chunk;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CollectorChunk {

    private Map<Material, Integer> contents = new HashMap<>();
    private List<Material> validMaterials = new ArrayList<>();
    private String world;

    public CollectorChunk(final List<String> components) {
        for (String component : components) {
            final String[] comp = component.split(";");
            addMaterial(Integer.valueOf(comp[0]), 0);
            validMaterials.add(Material.getMaterial(Integer.valueOf(comp[0])));
        }
    }

    private void addMaterial(final int material, final int amount) {
        this.contents.put(Material.getMaterial(material), contents.getOrDefault(Material.getMaterial(material), 0) + amount);
    }

    public void addMaterial(final Material material, final int amount) {
        this.contents.put(material, contents.getOrDefault(material, 0) + amount);
    }

    public Map<Material, Integer> getContents() {
        return this.contents;
    }

    public void clearMaterial(final int material) {
        this.contents.put(Material.getMaterial(material), 0);
    }

    public List<Material> getValidMaterials() {
        return this.validMaterials;
    }

    public String getWorld() {
        return this.world;
    }

    public void setWorld(final String world) {
        this.world = world;
    }
}
