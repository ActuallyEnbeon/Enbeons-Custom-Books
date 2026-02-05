package com.enbeon.books;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Set;

import static com.enbeon.books.EnbeonsCustomBooks.CONFIG;
import static com.enbeon.books.EnbeonsCustomBooks.MOD_ID;

public class EnchantmentGetter {
    public static Identifier getEnchantment(Set<Holder<Enchantment>> enchantments, Identifier fallback) {
        for (String enchantmentName : CONFIG.getEnchantmentPrecedence()) {
            boolean match = enchantments.stream().anyMatch(
                    entry -> enchantmentIDMatches(entry, enchantmentName)
            );
            if (match) {
                String checkedName = specialBehaviour(enchantmentName);
                return Identifier.fromNamespaceAndPath(MOD_ID, checkedName);
            }
        }
        return fallback;
    }

    public static boolean enchantmentIDMatches(Holder<Enchantment> entry, String enchantmentName) {
        if (!(entry instanceof Holder.Reference<Enchantment> ref)) {
            return false;
        }
        return ref.is(Identifier.parse(enchantmentName));
    }

    private static String specialBehaviour(String enchantmentName) {
        if (enchantmentName.equals("mending") && !CONFIG.isMendingAnimated()) {
            return "mending_static";
        }
        if (enchantmentName.equals("unbreaking") && CONFIG.isOldUnbreaking()) {
            return "unbreaking_old";
        }
        // ':' not allowed in paths, use '/' in conjunction with directory structure instead
        return enchantmentName.replace(':', '/');
    }
}
