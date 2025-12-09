package com.enbeon.books.config;

import net.minecraft.GameVersion;
import net.minecraft.MinecraftVersion;
import net.minecraft.resource.PackVersion;
import net.minecraft.resource.ResourceType;

public class ConditionalConfigLogic {
    public static boolean shouldIncludeEnchantment(String enchantmentName) {
        GameVersion gameVersion = MinecraftVersion.create();
        PackVersion resourcePackVersion = gameVersion.packVersion(ResourceType.CLIENT_RESOURCES);
        // Lunge wasn't available before 25w41a, where the pack format increased to 70.0
        if (enchantmentName.equals("lunge")) return resourcePackVersion.major() >= 70;
        // All other enchantments should be available by default
        return true;
    }
}
