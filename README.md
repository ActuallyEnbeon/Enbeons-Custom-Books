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

I took inspiration from [Even Better Enchants](https://modrinth.com/resourcepack/even-better-enchants) for the textures in this mod.

## Textures

![allbooks](https://github.com/user-attachments/assets/45d09a3c-197b-4336-9ffa-aaabd64be4bd)

The animation for the Mending book texture can be disabled in the in-game config.

## Mod compatibility

If, for some reason, people find this mod useful and want to add modded enchantmnets to it, then feel free to fork it.
I'm also happy to receive pull requests, although bear in mind that it might take me a little while to get around to them.
