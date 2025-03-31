package com.enbeon.books.config;

import com.enbeon.books.EnbeonsCustomBooks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.enbeon.books.EnbeonsCustomBooksClient.CONFIG;

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

    private static final HashMap<Text, String> texts = new HashMap<>();

    // Config values
    boolean modEnabled = true;
    boolean mendingAnimated = true;
    ArrayList<String> enchantmentPrecedence = defaultPrecedence;

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
            boolean configChanged = false;
            for (int i = 0; i < defaultPrecedence.size(); i++) {
                String enchantmentName = defaultPrecedence.get(i);
                if (!newConfig.enchantmentPrecedence.contains(enchantmentName)) {
                    configChanged = true;
                    newConfig.enchantmentPrecedence.add(i, enchantmentName);
                }
            }

            // Apply loaded config
            CONFIG = newConfig;
            // And re-save if required
            if (configChanged) saveConfig();
        } catch (IOException e) {
            EnbeonsCustomBooks.LOGGER.warn("Failed to load config");
        }
    }

    public static ArrayList<Text> decodePrecedence(ArrayList<String> input) {
        ArrayList<Text> output = new ArrayList<>();
        for (String enchantmentName : input) {
            String keyBuilder = "enchantment.";
            keyBuilder += (
                    enchantmentName.contains(":")
                    ? enchantmentName.replace(':', '.')
                    : "minecraft." + enchantmentName
            );
            Text enchantmentText = Text.translatable(keyBuilder);
            output.add(enchantmentText);
            texts.put(enchantmentText, enchantmentName);
        }
        return output;
    }

    public static ArrayList<String> encodePrecedence(List<Text> input) {
        ArrayList<String> output = new ArrayList<>();
        for (Text enchantmentText : input) {
            String enchantmentName = texts.get(enchantmentText);
            if (enchantmentName != null) {
                output.add(enchantmentName);
            }
        }
        return output;
    }
}