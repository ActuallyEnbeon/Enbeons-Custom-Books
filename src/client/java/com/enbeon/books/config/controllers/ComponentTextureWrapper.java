package com.enbeon.books.config.controllers;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public record ComponentTextureWrapper(Component component, @Nullable Identifier texture) {
    public static ComponentTextureWrapper empty() {
        return new ComponentTextureWrapper(Component.nullToEmpty(""), null);
    }
}