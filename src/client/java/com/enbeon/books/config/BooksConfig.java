package com.enbeon.books.config;

import com.enbeon.books.EnbeonsCustomBooks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.enbeon.books.EnbeonsCustomBooksClient.CONFIG;
import static com.enbeon.books.EnbeonsCustomBooksClient.configLocation;

public class BooksConfig {
    boolean modEnabled = true;
    boolean mendingAnimated = true;

    // TODO: make a screen to allow customising the order
    String[] enchantmentPrecedence = {
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
    };

    public boolean isModEnabled() {
        return modEnabled;
    }

    public boolean isMendingAnimated() {
        return mendingAnimated;
    }

    public String[] getEnchantmentPrecedence() {
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
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            CONFIG = gson.fromJson(Files.readString(configLocation), BooksConfig.class);
        } catch (IOException e) {
            EnbeonsCustomBooks.LOGGER.warn("Failed to load config");
        }
    }
}
