package com.enbeon.books;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Set;

import static com.enbeon.books.EnbeonsCustomBooks.MOD_ID;
import static com.enbeon.books.EnbeonsCustomBooksClient.CONFIG;

public class EnchantmentGetter {
    public static Identifier getEnchantment(Set<RegistryEntry<Enchantment>> enchantments, Identifier fallback) {
        for (String enchantmentName : CONFIG.getEnchantmentPrecedence()) {
            boolean match = enchantments.stream().anyMatch(
                    entry -> enchantmentIDMatches(entry, enchantmentName)
            );
            if (match) {
                String checkedName = specialBehaviour(enchantmentName);
                return Identifier.of(MOD_ID, checkedName);
            }
        }
        return fallback;
    }

    public static boolean enchantmentIDMatches(RegistryEntry<Enchantment> entry, String enchantmentName) {
        if (!(entry instanceof RegistryEntry.Reference<Enchantment> ref)) {
            return false;
        }
        return ref.matchesId(Identifier.of(enchantmentName));
    }

    private static String specialBehaviour(String enchantmentName) {
        if (enchantmentName.equals("mending") && !CONFIG.isMendingAnimated()) {
            return "mending_static";
        }
        // ':' not allowed in paths, use '/' in conjunction with directory structure instead
        return enchantmentName.replace(':', '/');
    }
}
