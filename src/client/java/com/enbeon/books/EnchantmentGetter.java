package com.enbeon.books;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.stream.Stream;

public class EnchantmentGetter {
    public static Identifier getEnchantment(Set<RegistryEntry<Enchantment>> enchantments, Identifier fallback) {
        // Priority 0: Treasure enchants
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.FROST_WALKER)) {
                return makeID("powder_snow_bucket");
            } else if (enchantment.matchesKey(Enchantments.MENDING)) {
                return makeID("ender_pearl");
            } else if (enchantment.matchesKey(Enchantments.SOUL_SPEED)) {
                return makeID("blaze_rod");
            } else if (enchantment.matchesKey(Enchantments.SWIFT_SNEAK)) {
                return makeID("amethyst_shard");
            } else if (enchantment.matchesKey(Enchantments.WIND_BURST)) {
                return makeID("wind_charge");
            }
        }

        // Priority 1: Weight 1
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.CHANNELING)) {
                return makeID("lightning_rod");
            } else if (enchantment.matchesKey(Enchantments.INFINITY)) {
                return makeID("arrow");
            } else if (enchantment.matchesKey(Enchantments.SILK_TOUCH)) {
                return makeID("blaze_powder");
            } else if (enchantment.matchesKey(Enchantments.THORNS)) {
                return makeID("netherite_sword");
            }
        }

        // Priority 2: Weight 2
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.AQUA_AFFINITY)) {
                return makeID("heart_of_the_sea");
            } else if (enchantment.matchesKey(Enchantments.BLAST_PROTECTION)) {
                return makeID("tnt");
            } else if (enchantment.matchesKey(Enchantments.BREACH)) {
                return makeID("end_crystal");
            } else if (enchantment.matchesKey(Enchantments.DEPTH_STRIDER)) {
                return makeID("cobweb");
            } else if (enchantment.matchesKey(Enchantments.FIRE_ASPECT)) {
                return makeID("magma_cream");
            } else if (enchantment.matchesKey(Enchantments.FLAME)) {
                return makeID("tipped_arrow");
            } else if (enchantment.matchesKey(Enchantments.FORTUNE)) {
                return makeID("emerald");
            } else if (enchantment.matchesKey(Enchantments.IMPALING)) {
                return makeID("trident");
            } else if (enchantment.matchesKey(Enchantments.LOOTING)) {
                return makeID("gold_ingot");
            } else if (enchantment.matchesKey(Enchantments.LUCK_OF_THE_SEA)) {
                return makeID("leather_boots");
            } else if (enchantment.matchesKey(Enchantments.LURE)) {
                return makeID("salmon");
            } else if (enchantment.matchesKey(Enchantments.MULTISHOT)) {
                return makeID("crossbow");
            } else if (enchantment.matchesKey(Enchantments.PUNCH)) {
                return makeID("bow");
            } else if (enchantment.matchesKey(Enchantments.RESPIRATION)) {
                return makeID("pufferfish");
            } else if (enchantment.matchesKey(Enchantments.RIPTIDE)) {
                return makeID("elytra");
            } else if (enchantment.matchesKey(Enchantments.SWEEPING_EDGE)) {
                return makeID("iron_sword");
            }
        }

        // Priority 3: Weight 5
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.BANE_OF_ARTHROPODS)) {
                return makeID("spider_eye");
            } else if (enchantment.matchesKey(Enchantments.DENSITY)) {
                return makeID("heavy_core");
            } else if (enchantment.matchesKey(Enchantments.FEATHER_FALLING)) {
                return makeID("feather");
            } else if (enchantment.matchesKey(Enchantments.FIRE_PROTECTION)) {
                return makeID("golden_helmet");
            } else if (enchantment.matchesKey(Enchantments.KNOCKBACK)) {
                return makeID("diamond_sword");
            } else if (enchantment.matchesKey(Enchantments.LOYALTY)) {
                return makeID("string");
            } else if (enchantment.matchesKey(Enchantments.PROJECTILE_PROTECTION)) {
                return makeID("iron_helmet");
            } else if (enchantment.matchesKey(Enchantments.QUICK_CHARGE)) {
                return makeID("potion");
            } else if (enchantment.matchesKey(Enchantments.SMITE)) {
                return makeID("rotten_flesh");
            } else if (enchantment.matchesKey(Enchantments.UNBREAKING)) {
                return makeID("iron_ingot");
            }
        }

        // Priority 4: Weight 10
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.EFFICIENCY)) {
                return makeID("golden_pickaxe");
            } else if (enchantment.matchesKey(Enchantments.PIERCING)) {
                return makeID("golden_sword");
            } else if (enchantment.matchesKey(Enchantments.POWER)) {
                return makeID("wooden_sword");
            } else if (enchantment.matchesKey(Enchantments.PROTECTION)) {
                return makeID("iron_chestplate");
            } else if (enchantment.matchesKey(Enchantments.SHARPNESS)) {
                return makeID("stone_sword");
            }
        }

        // Priority 5: Curses
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.BINDING_CURSE)) {
                return makeID("leather_helmet");
            } else if (enchantment.matchesKey(Enchantments.VANISHING_CURSE)) {
                return makeID("recovery_compass");
            }
        }

        return fallback;
    }

    private static Identifier makeID(String path) {
        // TODO: change namespace to this mod's ID
        return Identifier.of("minecraft", path);
    }
}
