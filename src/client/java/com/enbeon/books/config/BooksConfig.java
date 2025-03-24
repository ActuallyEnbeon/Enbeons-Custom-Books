package com.enbeon.books.config;

import com.enbeon.books.EnbeonsCustomBooks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.enbeon.books.EnbeonsCustomBooksClient.CONFIG;
import static com.enbeon.books.EnbeonsCustomBooksClient.configLocation;

public class BooksConfig {
    boolean modEnabled = true;
    boolean mendingAnimated = true;

    // TODO: make a screen to allow customising the order
    ArrayList<String> enchantmentPrecedence = new ArrayList<>(List.of(
            // Treasure enchantments
            "mending",
            "frost_walker",
            "soul_speed",
            "swift_sneak",
            "wind_burst",
            // Curses
            "binding_curse",
            "vanishing_curse",
            // Weight 1
            "channeling",
            "infinity",
            "silk_touch",
            "thorns",
            // Weight 2
            "aqua_affinity",
            "blast_protection",
            "breach",
            "depth_strider",
            "fire_aspect",
            "flame",
            "fortune",
            "impaling",
            "looting",
            "luck_of_the_sea",
            "lure",
            "multishot",
            "punch",
            "respiration",
            "riptide",
            "sweeping_edge",
            // Weight 5
            "bane_of_arthropods",
            "density",
            "feather_falling",
            "fire_protection",
            "knockback",
            "loyalty",
            "projectile_protection",
            "quick_charge",
            "smite",
            "unbreaking",
            // Weight 10
            "efficiency",
            "piercing",
            "power",
            "protection",
            "sharpness"
    ));

    public boolean isModEnabled() {
        return modEnabled;
    }

    public boolean isMendingAnimated() {
        return mendingAnimated;
    }

    public ArrayList<String> getEnchantmentPrecedence() {
        return enchantmentPrecedence;
    }

    public static void saveConfig() {
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Files.writeString(configLocation, gson.toJson(CONFIG), StandardCharsets.UTF_8);
        } catch (IOException e) {
            EnbeonsCustomBooks.LOGGER.warn("Failed to save config");
        }
    }

    public static void loadConfig() {
        if (!Files.exists(configLocation)) {
            saveConfig();
            return;
        }
        try {
            // Load config from file
            Gson gson = new Gson();
            BooksConfig newConfig = gson.fromJson(Files.readString(configLocation), BooksConfig.class);

            // Ensure that all enchantments are present in the config
            for (int i = 0; i < CONFIG.enchantmentPrecedence.size(); i++) {
                String enchantmentName = CONFIG.enchantmentPrecedence.get(i);
                if (!newConfig.enchantmentPrecedence.contains(enchantmentName)) {
                    newConfig.enchantmentPrecedence.add(i, enchantmentName);
                }
            }

            // Apply loaded config
            CONFIG = newConfig;
        } catch (IOException e) {
            EnbeonsCustomBooks.LOGGER.warn("Failed to load config");
        }
    }
}
