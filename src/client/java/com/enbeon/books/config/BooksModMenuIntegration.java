package com.enbeon.books.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

import static com.enbeon.books.EnbeonsCustomBooksClient.CONFIG;

public class BooksModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("config.enbeons-custom-books-config.title"));

            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.enbeons-custom-books-config.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startBooleanToggle(
                            Text.translatable("config.enbeons-custom-books-config.option.modEnabled"), CONFIG.modEnabled)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.enbeons-custom-books-config.option.modEnabled.tooltip"))
                    .setSaveConsumer(newValue -> CONFIG.modEnabled = newValue)
                    .build()
            );

            general.addEntry(entryBuilder.startBooleanToggle(
                            Text.translatable("config.enbeons-custom-books-config.option.mendingAnimation"), CONFIG.mendingAnimated)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> CONFIG.mendingAnimated = newValue)
                    .build()
            );

            builder.setSavingRunnable(BooksConfig::saveConfig);
            return builder.build();
        };
    }
}
