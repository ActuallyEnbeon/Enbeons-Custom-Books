package com.enbeon.books;

import com.enbeon.books.config.BooksConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnbeonsCustomBooks implements ClientModInitializer {
    public static final String MOD_ID = "enbeons_custom_books";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static BooksConfig CONFIG = new BooksConfig();

    @Override
    public void onInitializeClient() {
        BooksConfig.loadConfig();
    }
}