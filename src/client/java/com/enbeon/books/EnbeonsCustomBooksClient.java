package com.enbeon.books;

import com.enbeon.books.config.BooksConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class EnbeonsCustomBooksClient implements ClientModInitializer {
    public static BooksConfig CONFIG = new BooksConfig();
    public static final Path configLocation =
            FabricLoader.getInstance().getConfigDir().resolve("enbeons-custom-books-config.json");

    @Override
    public void onInitializeClient() {
        BooksConfig.loadConfig();
    }
}