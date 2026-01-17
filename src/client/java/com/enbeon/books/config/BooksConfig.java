package com.enbeon.books.config;

import com.enbeon.books.EnbeonsCustomBooks;
import com.enbeon.books.EnbeonsCustomBooksClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BooksConfig {
    // Static fields
    static final Path configLocation =
            FabricLoader.getInstance().getConfigDir().resolve("enbeons-custom-books-config.json");

    static final ArrayList<String> defaultPrecedence = new ArrayList<>(List.of(
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
            "lunge",
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

    static List<String> prunedDefaultPrecedence() {
        return defaultPrecedence
                .stream()
                .filter(ConditionalConfigLogic::shouldIncludeEnchantment)
                .collect(Collectors.toList());
    }

    static HashMap<Component, String> texts = new HashMap<>();

    // Config values
    boolean modEnabled = true;
    boolean mendingAnimated = true;
    boolean oldUnbreaking = false;
    ArrayList<String> enchantmentPrecedence = defaultPrecedence;

    public boolean isModEnabled() {
        return modEnabled;
    }

    public boolean isMendingAnimated() {
        return mendingAnimated;
    }

    public boolean isOldUnbreaking() {
        return oldUnbreaking;
    }

    public ArrayList<String> getEnchantmentPrecedence() {
        return enchantmentPrecedence;
    }

    public static void saveConfig() {
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Files.writeString(configLocation, gson.toJson(EnbeonsCustomBooksClient.CONFIG), StandardCharsets.UTF_8);
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

            // Ensure that all enchantments are present in the config (if they should be included)
            boolean configChanged = false;
            for (int i = 0; i < prunedDefaultPrecedence().size(); i++) {
                String enchantmentName = prunedDefaultPrecedence().get(i);
                if (!newConfig.enchantmentPrecedence.contains(enchantmentName)) {
                    configChanged = true;
                    // If the list is not long enough to put the enchantment in the right place, just append it
                    newConfig.enchantmentPrecedence.add(Math.min(i, newConfig.enchantmentPrecedence.size()), enchantmentName);
                }
            }

            // Apply loaded config
            EnbeonsCustomBooksClient.CONFIG = newConfig;
            // And re-save if required
            if (configChanged) saveConfig();
        } catch (IOException e) {
            EnbeonsCustomBooks.LOGGER.warn("Failed to load config");
        }
    }

    public static ArrayList<Component> decodePrecedence(List<String> input) {
        ArrayList<Component> output = new ArrayList<>();
        for (String enchantmentName : input) {
            if (enchantmentName == null) continue;
            String keyBuilder = "enchantment.";
            keyBuilder += (
                    enchantmentName.contains(":")
                    ? enchantmentName.replace(':', '.')
                    : "minecraft." + enchantmentName
            );
            Component enchantmentText = Component.translatable(keyBuilder);
            output.add(enchantmentText);
            texts.put(enchantmentText, enchantmentName);
        }
        return output;
    }

    public static ArrayList<String> encodePrecedence(List<Component> input) {
        ArrayList<String> output = new ArrayList<>();
        for (Component enchantmentText : input) {
            String enchantmentName = texts.get(enchantmentText);
            if (enchantmentName != null) {
                output.add(enchantmentName);
            }
        }
        return output;
    }
}