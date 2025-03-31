package com.enbeon.books;

import com.enbeon.books.config.BooksConfig;
import net.fabricmc.api.ClientModInitializer;

public class EnbeonsCustomBooksClient implements ClientModInitializer {
    public static BooksConfig CONFIG = new BooksConfig();

    @Override
    public void onInitializeClient() {
        BooksConfig.loadConfig();
    }
}