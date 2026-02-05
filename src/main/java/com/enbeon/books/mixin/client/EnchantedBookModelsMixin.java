package com.enbeon.books.mixin.client;

import com.enbeon.books.EnchantmentGetter;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.enbeon.books.EnbeonsCustomBooks.CONFIG;

@Mixin(ItemModelResolver.class)
public class EnchantedBookModelsMixin {
    @Redirect(
            method = "appendItemLayers",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;")
    )
    private Object bookModelReturner(ItemStack instance, DataComponentType<ResourceLocation> componentType) {
        ResourceLocation modelID = instance.get(componentType);

        if (modelID != null && modelID.equals(ResourceLocation.withDefaultNamespace("enchanted_book"))) {
            ItemEnchantments storedEnchantments = instance.getComponents().get(DataComponents.STORED_ENCHANTMENTS);

            if (storedEnchantments != null && !storedEnchantments.isEmpty() && CONFIG.isModEnabled()) {
                return EnchantmentGetter.getEnchantment(storedEnchantments.keySet(), modelID);
            }
        }

        return modelID;
    }
}
