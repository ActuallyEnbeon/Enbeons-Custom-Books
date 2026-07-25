package com.enbeon.books.config.controllers;

import dev.isxander.yacl3.gui.TooltipButtonWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DummyButtonWidget extends TooltipButtonWidget {
    public DummyButtonWidget(Screen screen) {
        super(screen, 0, 0, 0, 0, Component.empty(), Component.empty(), null);
    }
}
