package com.enbeon.books;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Set;

import static com.enbeon.books.EnbeonsCustomBooks.CONFIG;

public class EnchantmentGetter {
    public static Identifier getEnchantment(Set<RegistryEntry<Enchantment>> enchantments, Identifier fallback) {
        // Priority -1: Treasure enchants
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.FROST_WALKER)) {
                return makeID("frost_walker");
            } else if (enchantment.matchesKey(Enchantments.MENDING)) {
                return makeID(CONFIG.mendingAnimation() ? "mending" : "mending_static");
            } else if (enchantment.matchesKey(Enchantments.SOUL_SPEED)) {
                return makeID("soul_speed");
            } else if (enchantment.matchesKey(Enchantments.SWIFT_SNEAK)) {
                return makeID("swift_sneak");
            } else if (enchantment.matchesKey(Enchantments.WIND_BURST)) {
                return makeID("wind_burst");
            }
        }

        // Priority 0: Curses
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.BINDING_CURSE)) {
                return makeID("binding_curse");
            } else if (enchantment.matchesKey(Enchantments.VANISHING_CURSE)) {
                return makeID("vanishing_curse");
            }
        }

        // Priority 1: Weight 1
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.CHANNELING)) {
                return makeID("channeling");
            } else if (enchantment.matchesKey(Enchantments.INFINITY)) {
                return makeID("infinity");
            } else if (enchantment.matchesKey(Enchantments.SILK_TOUCH)) {
                return makeID("silk_touch");
            } else if (enchantment.matchesKey(Enchantments.THORNS)) {
                return makeID("thorns");
            }
        }

        // Priority 2: Weight 2
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.AQUA_AFFINITY)) {
                return makeID("aqua_affinity");
            } else if (enchantment.matchesKey(Enchantments.BLAST_PROTECTION)) {
                return makeID("blast_protection");
            } else if (enchantment.matchesKey(Enchantments.BREACH)) {
                return makeID("breach");
            } else if (enchantment.matchesKey(Enchantments.DEPTH_STRIDER)) {
                return makeID("depth_strider");
            } else if (enchantment.matchesKey(Enchantments.FIRE_ASPECT)) {
                return makeID("fire_aspect");
            } else if (enchantment.matchesKey(Enchantments.FLAME)) {
                return makeID("flame");
            } else if (enchantment.matchesKey(Enchantments.FORTUNE)) {
                return makeID("fortune");
            } else if (enchantment.matchesKey(Enchantments.IMPALING)) {
                return makeID("impaling");
            } else if (enchantment.matchesKey(Enchantments.LOOTING)) {
                return makeID("looting");
            } else if (enchantment.matchesKey(Enchantments.LUCK_OF_THE_SEA)) {
                return makeID("luck_of_the_sea");
            } else if (enchantment.matchesKey(Enchantments.LURE)) {
                return makeID("lure");
            } else if (enchantment.matchesKey(Enchantments.MULTISHOT)) {
                return makeID("multishot");
            } else if (enchantment.matchesKey(Enchantments.PUNCH)) {
                return makeID("punch");
            } else if (enchantment.matchesKey(Enchantments.RESPIRATION)) {
                return makeID("respiration");
            } else if (enchantment.matchesKey(Enchantments.RIPTIDE)) {
                return makeID("riptide");
            } else if (enchantment.matchesKey(Enchantments.SWEEPING_EDGE)) {
                return makeID("sweeping_edge");
            }
        }

        // Priority 3: Weight 5
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.BANE_OF_ARTHROPODS)) {
                return makeID("bane_of_arthropods");
            } else if (enchantment.matchesKey(Enchantments.DENSITY)) {
                return makeID("density");
            } else if (enchantment.matchesKey(Enchantments.FEATHER_FALLING)) {
                return makeID("feather_falling");
            } else if (enchantment.matchesKey(Enchantments.FIRE_PROTECTION)) {
                return makeID("fire_protection");
            } else if (enchantment.matchesKey(Enchantments.KNOCKBACK)) {
                return makeID("knockback");
            } else if (enchantment.matchesKey(Enchantments.LOYALTY)) {
                return makeID("loyalty");
            } else if (enchantment.matchesKey(Enchantments.PROJECTILE_PROTECTION)) {
                return makeID("projectile_protection");
            } else if (enchantment.matchesKey(Enchantments.QUICK_CHARGE)) {
                return makeID("quick_charge");
            } else if (enchantment.matchesKey(Enchantments.SMITE)) {
                return makeID("smite");
            } else if (enchantment.matchesKey(Enchantments.UNBREAKING)) {
                return makeID("unbreaking");
            }
        }

        // Priority 4: Weight 10
        for (RegistryEntry<Enchantment> enchantment : enchantments) {
            if (enchantment.matchesKey(Enchantments.EFFICIENCY)) {
                return makeID("efficiency");
            } else if (enchantment.matchesKey(Enchantments.PIERCING)) {
                return makeID("piercing");
            } else if (enchantment.matchesKey(Enchantments.POWER)) {
                return makeID("power");
            } else if (enchantment.matchesKey(Enchantments.PROTECTION)) {
                return makeID("protection");
            } else if (enchantment.matchesKey(Enchantments.SHARPNESS)) {
                return makeID("sharpness");
            }
        }

        return fallback;
    }

    private static Identifier makeID(String path) {
        return Identifier.of(EnbeonsCustomBooks.MOD_ID, path);
    }
}
