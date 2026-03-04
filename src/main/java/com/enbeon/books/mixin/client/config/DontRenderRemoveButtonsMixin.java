package com.enbeon.books.mixin.client.config;

import com.enbeon.books.config.controllers.DummyButtonWidget;
import dev.isxander.yacl3.api.ListOptionEntry;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.TooltipButtonWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.ListEntryWidget;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ListEntryWidget.class)
public class DontRenderRemoveButtonsMixin {
    @Mutable
    @Shadow
    @Final
    private TooltipButtonWidget removeButton;

    @Inject(
            method = "<init>",
            at = @At(value = "RETURN")
    )
    private void renderBlocker(YACLScreen screen, ListOptionEntry<?> listOptionEntry, AbstractWidget entryWidget, CallbackInfo ci) {
        // If the current object is the enchantment order, replace the removeButton with a dummy
        if (listOptionEntry.parentGroup().name().getContents() instanceof TranslatableContents contents
                && contents.getKey().equals("config.enbeons_custom_books.option.precedence"))
        {
            this.removeButton = new DummyButtonWidget(screen);
        }
    }
}
