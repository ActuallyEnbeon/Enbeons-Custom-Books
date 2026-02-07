# Enbeon's Custom Books

[![Modrinth](https://img.shields.io/modrinth/dt/hLTpBzGI?color=00AF5C&label=downloads&logo=modrinth)](https://modrinth.com/mod/enbeons-custom-books)

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/actuallyenbeon)

Yet another Minecraft mod that gives Enchanted Books unique textures based on the enchantments they have.

Compatible with Fabric and Quilt. Sorry if you run Forge or NeoForge!

This mod is compatible with 1.21.4+. I probably won't backport unless there's demand for it.

## Preamble

This mod is the product of wanting a solution to component-controlled textures for Enchanted Books,
when CIT is unavailable and Mojang's vanilla solution is infeasible.

Enbeon's Custom Books uses a precedence-based system to decide which texture is used for a given book.
This precedence is fully configurable in the mod config; and I've provided what I feel is a sensible default order,
with rarer enchantments taking priority over more common ones (Mending has the highest priority, Sharpness has the lowest).

I took inspiration from [Even Better Enchants](https://modrinth.com/resourcepack/even-better-enchants) for some of the textures in this mod.

## Dependencies

[Fabric API](https://github.com/FabricMC/fabric) is required to run this mod.

Additionally, to access the in-game config screen, you will need:
- [YetAnotherConfigLib (YACL)](https://github.com/isXander/YetAnotherConfigLib)
- [Mod Menu](https://github.com/TerraformersMC/ModMenu)

## Textures

![allbooks-1 21 11](https://github.com/user-attachments/assets/2ad2862b-4f11-4985-894b-d213b46a5b7f)

The animation for the Mending book texture can be disabled in the in-game config.

## Mod compatibility

If you find this mod useful and want me to add modded enchantments to it, feel free to create an issue or pull request.
