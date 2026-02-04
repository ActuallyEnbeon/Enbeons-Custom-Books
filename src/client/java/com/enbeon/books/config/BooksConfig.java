package com.enbeon.books.config;

import com.enbeon.books.EnbeonsCustomBooks;
import com.enbeon.books.EnbeonsCustomBooksClient;
import com.enbeon.books.config.controllers.ComponentItemWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
    boolean booksDisplayedInPrecedenceScreen = true;
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

    public boolean areBooksDisplayedInPrecedenceScreen() {
        return booksDisplayedInPrecedenceScreen;
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

    @SuppressWarnings("deprecation")
    private static ItemStack getBookAsItem(HolderLookup.RegistryLookup<Enchantment> enchantmentLookup, String enchantmentName) {
        // If the enchantmentLookup is null, an item cannot be created
        if (enchantmentLookup == null) return null;
        // Get Holder for the enchanted book Item
        Holder.Reference<Item> itemHolder = Items.ENCHANTED_BOOK.asItem().builtInRegistryHolder();
        itemHolder.bindComponents(DataComponentMap.EMPTY);
        // Create Enchantment ResourceKey and use it to create ItemEnchantments
        ResourceKey<Enchantment> enchantmentResourceKey = ResourceKey.create(
                Registries.ENCHANTMENT, Identifier.bySeparator(enchantmentName, ':')
        );
        Optional<Holder.Reference<Enchantment>> enchantmentHolder = enchantmentLookup.get(enchantmentResourceKey);
        if (enchantmentHolder.isEmpty()) return null;
        ItemEnchantments.Mutable itemEnchantmentsMutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        itemEnchantmentsMutable.set(enchantmentHolder.get(), 1);
        // Create and return ItemStack
        return new ItemStack(
                itemHolder, 1,
                DataComponentPatch.builder()
                        .set(DataComponents.ITEM_MODEL, Identifier.withDefaultNamespace("enchanted_book"))
                        .set(DataComponents.STORED_ENCHANTMENTS, itemEnchantmentsMutable.toImmutable())
                        .set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .build()
        );
    }

    public static ArrayList<ComponentItemWrapper> decodePrecedence(List<String> input) {
        ArrayList<ComponentItemWrapper> output = new ArrayList<>();
        // Lookup enchantment registry to allow for book creation (renders books in the config screen)
        HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = null;
        // Only need to create the lookup if books will be displayed
        if (EnbeonsCustomBooksClient.CONFIG.areBooksDisplayedInPrecedenceScreen()) {
            enchantmentLookup = VanillaRegistries.createLookup().lookupOrThrow(Registries.ENCHANTMENT);
        }
        // Create ComponentTextureWrappers for all enchantments and add them to output
        for (String enchantmentName : input) {
            if (enchantmentName == null) continue;
            // Get enchantment name as a Component
            String keyBuilder = "enchantment.";
            keyBuilder += (
                    enchantmentName.contains(":")
                    ? enchantmentName.replace(':', '.')
                    : "minecraft." + enchantmentName
            );
            Component enchantmentText = Component.translatable(keyBuilder);
            // Get enchanted book as an ItemStack and store this enchantment on it
            ItemStack item = getBookAsItem(enchantmentLookup, enchantmentName);
            // Create wrapper instance and add to output
            output.add(new ComponentItemWrapper(enchantmentText, item));
            texts.put(enchantmentText, enchantmentName);
        }
        return output;
    }

    public static ArrayList<String> encodePrecedence(List<ComponentItemWrapper> input) {
        ArrayList<String> output = new ArrayList<>();
        for (ComponentItemWrapper wrapper : input) {
            String enchantmentName = texts.get(wrapper.component());
            if (enchantmentName != null) {
                output.add(enchantmentName);
            }
        }
        return output;
    }
}