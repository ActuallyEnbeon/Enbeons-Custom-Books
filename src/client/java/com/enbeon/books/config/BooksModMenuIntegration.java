package com.enbeon.books.config;

import com.enbeon.books.config.controllers.ComponentItemWrapper;
import com.enbeon.books.config.controllers.LabelAndItemController;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;

import static com.enbeon.books.EnbeonsCustomBooksClient.CONFIG;
import static com.enbeon.books.config.BooksConfig.decodePrecedence;
import static com.enbeon.books.config.BooksConfig.encodePrecedence;

public class BooksModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.enbeons_custom_books.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.enbeons_custom_books.category.general"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.enbeons_custom_books.option.modEnabled"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.enbeons_custom_books.option.modEnabled.desc")))
                                .binding(true, () -> CONFIG.modEnabled, newValue -> CONFIG.modEnabled = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.enbeons_custom_books.option.mendingAnimation"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.enbeons_custom_books.option.mendingAnimation.desc")))
                                .binding(true, () -> CONFIG.mendingAnimated, newValue -> CONFIG.mendingAnimated = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.enbeons_custom_books.option.oldUnbreaking"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.enbeons_custom_books.option.oldUnbreaking.desc")))
                                .binding(false, () -> CONFIG.oldUnbreaking, newValue -> CONFIG.oldUnbreaking = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.enbeons_custom_books.category.precedence"))
                        .group(ListOption.<ComponentItemWrapper>createBuilder()
                                .name(Component.translatable("config.enbeons_custom_books.option.precedence"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.enbeons_custom_books.option.precedence.desc")))
                                .binding(
                                        decodePrecedence(BooksConfig.prunedDefaultPrecedence()),
                                        () -> decodePrecedence(CONFIG.enchantmentPrecedence),
                                        newValue -> CONFIG.enchantmentPrecedence = encodePrecedence(newValue))
                                .controller(option -> () -> new LabelAndItemController(option))
                                .initial(ComponentItemWrapper.empty())
                                .maximumNumberOfEntries(CONFIG.enchantmentPrecedence.size())
                                .minimumNumberOfEntries(CONFIG.enchantmentPrecedence.size())
                                .build())
                        .build())
                .save(BooksConfig::saveConfig)
                .build()
                .generateScreen(parent);
    }
}
