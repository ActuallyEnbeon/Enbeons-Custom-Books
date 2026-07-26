plugins {
    id("dev.kikugie.stonecutter")
    id("net.fabricmc.fabric-loom-remap") version "1.14-SNAPSHOT" apply false
}

stonecutter active "26.1"

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    swaps["mod_version"] = "\"${property("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version}\";"
    constants["release"] = property("mod.id") != "template"
    dependencies["fapi"] = node.project.property("deps.fabric_api") as String

    replacements {
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }
        string("gui_graphics_replacements", current.parsed >= "26.1") {
            // These replacements apply only to LabelAndItemController
            replace("GuiGraphics", "GuiGraphicsExtractor")
            replace("render", "extractRenderState")
            replace("graphics.drawString", "graphics.text")
            replace("renderFakeItem", "fakeItem")
        }
        string(current.parsed >= "26.3") {
            replace("createLookup", "createWorldLookup")
        }
    }
}
