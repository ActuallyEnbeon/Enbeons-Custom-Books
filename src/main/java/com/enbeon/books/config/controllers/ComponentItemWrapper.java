package com.enbeon.books.config.controllers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record ComponentItemWrapper(Component component, @Nullable ItemStack item) {
    public static ComponentItemWrapper empty() {
        return new ComponentItemWrapper(Component.nullToEmpty(""), null);
    }
}
