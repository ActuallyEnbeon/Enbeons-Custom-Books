package com.enbeon.books.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.gui.controllers.LabelController;
import net.minecraft.text.Text;

import static com.enbeon.books.EnbeonsCustomBooksClient.CONFIG;
import static com.enbeon.books.config.BooksConfig.decodePrecedence;
import static com.enbeon.books.config.BooksConfig.encodePrecedence;

public class BooksModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config.enbeons_custom_books.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.enbeons_custom_books.category.general"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("config.enbeons_custom_books.option.modEnabled"))
                                .description(OptionDescription.of(
                                        Text.translatable("config.enbeons_custom_books.option.modEnabled.desc")))
                                .binding(true, () -> CONFIG.modEnabled, newValue -> CONFIG.modEnabled = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("config.enbeons_custom_books.option.mendingAnimation"))
                                .description(OptionDescription.of(
                                        Text.translatable("config.enbeons_custom_books.option.mendingAnimation.desc")))
                                .binding(true, () -> CONFIG.mendingAnimated, newValue -> CONFIG.mendingAnimated = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.enbeons_custom_books.category.precedence"))
                        .option(ListOption.<Text>createBuilder()
                                .name(Text.translatable("config.enbeons_custom_books.option.precedence"))
                                .description(OptionDescription.of(
                                        Text.translatable("config.enbeons_custom_books.option.precedence.desc")))
                                .binding(
                                        decodePrecedence(BooksConfig.defaultPrecedence),
                                        () -> decodePrecedence(CONFIG.enchantmentPrecedence),
                                        newValue -> CONFIG.enchantmentPrecedence = encodePrecedence(newValue))
                                .controller(option -> () -> new LabelController(option))
                                .initial(Text.of(""))
                                .maximumNumberOfEntries(BooksConfig.defaultPrecedence.size())
                                .minimumNumberOfEntries(BooksConfig.defaultPrecedence.size())
                                .build())
                        .build())
                .save(BooksConfig::saveConfig)
                .build()
                .generateScreen(parent);
    }
}
