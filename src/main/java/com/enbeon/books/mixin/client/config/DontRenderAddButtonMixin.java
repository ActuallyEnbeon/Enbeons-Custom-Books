package com.enbeon.books.mixin.client.config;

import com.enbeon.books.config.controllers.DummyButtonWidget;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.gui.OptionListWidget;
import dev.isxander.yacl3.gui.TooltipButtonWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionListWidget.ListGroupSeparatorEntry.class)
public class DontRenderAddButtonMixin {
    @Mutable
    @Shadow
    @Final
    private TooltipButtonWidget addListButton;

    @Inject(
            method = "<init>",
            at = @At(value = "RETURN")
    )
    private void renderBlocker(OptionListWidget this$0, ListOption<?> group, Screen screen, CallbackInfo ci) {
        // If the current object is the enchantment order, replace the addListButton with a dummy
        if (group.name().getContents() instanceof TranslatableContents contents
                && contents.getKey().equals("config.enbeons_custom_books.option.precedence"))
        {
            this.addListButton = new DummyButtonWidget(screen);
        }
    }
}