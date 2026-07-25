//~ gui_graphics_replacements

package com.enbeon.books.config.controllers;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.utils.GuiUtils;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// This class modified from YetAnotherConfigLib's LabelController class
// Original author: isXander
// Modified by: ActuallyEnbeon
// Originally modified date: 3rd February 2026
/**
 * Renders some text as a label, and optionally renders a fake item.
 */
public record LabelAndItemController(Option<ComponentItemWrapper> option) implements Controller<ComponentItemWrapper> {
    /**
     * Constructs a label controller
     *
     * @param option bound option
     */
    public LabelAndItemController {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<ComponentItemWrapper> option() {
        return option;
    }

    @Override
    public Component formatValue() {
        return option().pendingValue().component();
    }

    private ItemStack item() {
        return option().pendingValue().item();
    }

    @Override
    public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
        return new LabelAndTextureControllerElement(screen, widgetDimension);
    }

    public class LabelAndTextureControllerElement extends AbstractWidget {
        private List<FormattedCharSequence> wrappedText;
        protected boolean focused;

        protected final YACLScreen screen;

        public LabelAndTextureControllerElement(YACLScreen screen, Dimension<Integer> dim) {
            super(dim);
            this.screen = screen;
            updateText();
        }

        @Override
        public void extractRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
            updateText();

            int y = getDimension().y();
            for (FormattedCharSequence text : wrappedText) {
                graphics.text(textRenderer, text, getDimension().x() + getXPadding(), y + getYPadding(), option().available() ? -1 : 0xFFA0A0A0, true);
                if (item() != null) {
                    graphics.fakeItem(item(), getDimension().xLimit(), y);
                }
                y += textRenderer.lineHeight;
            }

            if (isFocused()) {
                graphics.fill(getDimension().x() - 1, getDimension().y() - 1, getDimension().xLimit() + 1, getDimension().y(), -1);
                graphics.fill(getDimension().x() - 1, getDimension().y() - 1, getDimension().x(), getDimension().yLimit() + 1, -1);
                graphics.fill(getDimension().x() - 1, getDimension().yLimit(), getDimension().xLimit() + 1, getDimension().yLimit() + 1, -1);
                graphics.fill(getDimension().xLimit(), getDimension().y() - 1, getDimension().xLimit() + 1, getDimension().yLimit() + 1, -1);
            }

            //? if >=26.1 {
            graphics.pose().pushMatrix();
            graphics.pose().popMatrix();
            //?} else {
            /*GuiUtils.pushPose(graphics);
            GuiUtils.translateZ(graphics, 100);
            GuiUtils.popPose(graphics);
            *///?}
        }

        private int getXPadding() {
            return 4;
        }

        private int getYPadding() {
            return 3;
        }

        private void updateText() {
            wrappedText = textRenderer.split(formatValue(), getDimension().width() - getXPadding() * 2);
            setDimension(getDimension().withHeight(wrappedText.size() * textRenderer.lineHeight + getYPadding() * 2));
        }

        @Override
        public boolean matchesSearch(String query) {
            return formatValue().getString().toLowerCase().contains(query.toLowerCase());
        }

        @Nullable
        @Override
        public ComponentPath nextFocusPath(@NotNull FocusNavigationEvent focusNavigationEvent) {
            if (!option().available())
                return null;
            return !this.isFocused() ? ComponentPath.leaf(this) : null;
        }

        @Override
        public boolean isFocused() {
            return focused;
        }

        @Override
        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        @Override
        public void updateNarration(NarrationElementOutput builder) {
            builder.add(NarratedElementType.TITLE, formatValue());
        }

        @Override
        public @NotNull NarrationPriority narrationPriority() {
            return NarrationPriority.FOCUSED;
        }
    }
}
