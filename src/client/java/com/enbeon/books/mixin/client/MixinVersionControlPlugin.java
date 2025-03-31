package com.enbeon.books.mixin.client;

import net.minecraft.GameVersion;
import net.minecraft.MinecraftVersion;
import net.minecraft.resource.ResourceType;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinVersionControlPlugin implements IMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        GameVersion gameVersion = MinecraftVersion.CURRENT;
        int resourcePackVersion = gameVersion.getResourceVersion(ResourceType.CLIENT_RESOURCES);
        String innerClassName;
        // If on 25w02 or below, use the 1.21.4 version
        if (resourcePackVersion < 48) {
            innerClassName = "TwentyOnePointFour";
        // Otherwise use the 1.21.5 version
        } else {
            innerClassName = "TwentyOnePointFive";
        }
        return mixinClassName.equals(makeMixinClassName(innerClassName));
    }

    private String makeMixinClassName(String innerClassName) {
        return "com.enbeon.books.mixin.client.EnchantedBookModelsMixins$" + innerClassName;
    }

    // Boilerplate
    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
