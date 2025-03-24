# Enbeon's Custom Books

Yet another Minecraft mod that gives Enchanted Books unique textures based on the enchantments they have.

Compatible with Fabric and Quilt. If you run Forge or NeoForge, sorry!

This mod is currently compatible with 1.21.4 only. I aim to update it to future versions, but I probably won't backport unless there's demand for it.

## Preamble

This mod is the product of wanting a solution to component-controlled textures for Enchanted Books, when CIT is unavailable and Mojang's vanilla solution is infeasible.

Enbeon's Custom Books uses a precedence-based system to decide which texture is used for a given book.
This precedence is fully configurable (in the config `.json` file - I'm working on getting a config screen up and running for it!)
and I've provided what I feel is a sensible default order, with rarer enchantments taking priority over more common ones
(Mending has the highest priority, Sharpness has the lowest).

I took inspiration from [Even Better Enchants](https://modrinth.com/resourcepack/even-better-enchants) for some of the textures in this mod.

## Textures

![allbooks](https://github.com/user-attachments/assets/45d09a3c-197b-4336-9ffa-aaabd64be4bd)

The animation for the Mending book texture can be disabled in the in-game config.

## Mod compatibility

If, for some reason, people find this mod useful and want to add modded enchantments to it, then feel free to fork it.
I'm also happy to receive pull requests, although bear in mind that it might take me a little while to get around to them.

### How to add other mods' enchantments

1. Fork this repository and clone it to your local system.
2. Create your book textures. Each file should have the same name as the ID of the enchantment (_without_ the namespace) and should be a `.png` file.
3. Navigate to `src/main/resources/assets/enbeons_custom_books`.
4. Create a new folder under `textures/item` named after the *namespace* of the target mod (it needs to be exactly right) and place the textures inside.

5. Create a new folder under `models/item`, again named after the mod's namespace, and create new model `.json` files for each enchantment.
   They should follow this format:
    ```json
   {
      "parent": "minecraft:item/generated",
      "textures": {
        "layer0": "enbeons_custom_books:item/mod_namespace/enchantment_id"
      }
    }
   ```
   with `mod_namespace` and `enchantment_id` changed to the correct values.

6. Create a new folder under `items`, again named after the mod's namespace, and create new `.json` files for each enchantment. (Blame Mojang if this is seeming tedious.)
    They should follow this format:
    ```json
    {
      "model": {
      "type": "minecraft:model",
        "model": "enbeons_custom_books:item/mod_namespace/enchantment_id"
      }
    }
    ```
   with `mod_namespace` and `enchantment_id` again changed to the correct values.

7. Add all the new enchantments to `enchantmentPrecedence` in the `BooksConfig` class.
    You can put them wherever you like in the order, although you may want to consider putting rarer or more valuable enchantments higher up than more common ones.
    Strings should be in the format `mod_namespace:enchantment_id` (note the colon `:` instead of slash `/`).
8. Try it out! If everything has gone to plan, you should see your new textures in-game.
    
