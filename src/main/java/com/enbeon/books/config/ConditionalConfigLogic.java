package com.enbeon.books.config;

import net.minecraft.DetectedVersion;
import net.minecraft.WorldVersion;
//? if >=1.21.9 {
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
//?}

public class ConditionalConfigLogic {
    public static boolean shouldIncludeEnchantment(String enchantmentName) {
        WorldVersion gameVersion = DetectedVersion.tryDetectVersion();
        //? if >=1.21.9
        PackFormat resourcePackVersion = gameVersion.packVersion(PackType.CLIENT_RESOURCES);
        // Lunge wasn't available before 25w41a, where the pack format increased to 70.0
        if (enchantmentName.equals("lunge")) {
            //? if >=1.21.9 {
            return resourcePackVersion.major() >= 70;
            //?} else
            //return false;
        }
        // All other enchantments should be available by default
        return true;
    }
}
