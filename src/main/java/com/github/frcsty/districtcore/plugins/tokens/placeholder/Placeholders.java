package com.github.frcsty.districtcore.plugins.tokens.placeholder;

import com.github.frcsty.districtcore.plugins.tokens.token.TokenManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Placeholders extends PlaceholderExpansion {

    private final TokenManager manager;
    private final DecimalFormat format = new DecimalFormat("#,###");

    public Placeholders(final TokenManager manager) {
        this.manager = manager;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        switch (params) {
            case "amount": return String.valueOf(manager.getTokens(player));
            case "amount-formatted": return formatAmount(manager.getTokens(player));
            case "amount-commas": return format.format(manager.getTokens(player));
            default: return null;
        }
    }

    @Override
    public String getIdentifier() {
        return "tokens";
    }

    @Override
    public String getAuthor() {
        return "Frcsty";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    private String formatAmount(long amount) {
        if (amount < 1000L) {
            return format(amount);
        }
        if (amount < 1000000L) {
            return format(amount / 1000L) + "k";
        }
        if (amount < 1000000000L) {
            return format(amount / 1000000L) + "M";
        }
        if (amount < 1000000000000L) {
            return format(amount / 1000000000L) + "B";
        }
        if (amount < 1000000000000000L) {
            return format(amount / 1000000000000L) + "T";
        }
        if (amount < 1000000000000000000L) {
            return format(amount / 1000000000000000L) + "Q";
        }

        return String.valueOf(amount);
    }

    private String format(long amount) {
        final NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(amount);
    }
}
