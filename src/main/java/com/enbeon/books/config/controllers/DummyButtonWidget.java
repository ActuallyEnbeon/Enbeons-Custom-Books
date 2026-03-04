package com.enbeon.books.config.controllers;

import dev.isxander.yacl3.gui.TooltipButtonWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class DummyButtonWidget extends TooltipButtonWidget {
    public DummyButtonWidget(Screen screen) {
        super(screen, 0, 0, 0, 0, Component.empty(), Component.empty(), null);
    }

    @Override
    protected void renderContents(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float a) {}
}
