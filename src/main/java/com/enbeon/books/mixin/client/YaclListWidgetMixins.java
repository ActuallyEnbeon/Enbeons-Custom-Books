package com.enbeon.books.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.isxander.yacl3.gui.OptionListWidget;
import dev.isxander.yacl3.gui.TooltipButtonWidget;
import dev.isxander.yacl3.gui.controllers.ListEntryWidget;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


public class YaclListWidgetMixins {
    @Mixin(OptionListWidget.ListGroupSeparatorEntry.class)
    public static class DontRenderAddButton {
        @WrapOperation(
                method = "renderContent",
                at = @At(value = "INVOKE", target = "Ldev/isxander/yacl3/gui/TooltipButtonWidget;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")
        )
        private void renderBlocker(TooltipButtonWidget instance, GuiGraphics graphics, int mouseX, int mouseY, float deltaTicks, Operation<Void> original) {
            // Don't render any buttons that are the add button
            // TODO: check we're in the right screen
            if (!instance.getMessage().getString().equals("+")) {
                original.call(instance, graphics, mouseX, mouseY, deltaTicks);
            }
        }
    }

    @Mixin(ListEntryWidget.class)
    public static class DontRenderRemoveButtons {
        @WrapOperation(
                method = "render",
                at = @At(value = "INVOKE", target = "Ldev/isxander/yacl3/gui/TooltipButtonWidget;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")
        )
        private void renderBlocker(TooltipButtonWidget instance, GuiGraphics graphics, int mouseX, int mouseY, float deltaTicks, Operation<Void> original) {
            // Don't render any buttons that are the remove button
            // TODO: check we're in the right screen
            if (!instance.getMessage().getString().equals("\u274c")) {
                original.call(instance, graphics, mouseX, mouseY, deltaTicks);
            }
        }
    }
}
