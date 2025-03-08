package com.enbeon.books.mixin.client;

import com.enbeon.books.EnchantmentGetter;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemModelManager.class)
public class EnchantedBookModelsMixin {
    @Redirect(
        method="update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;get(Lnet/minecraft/component/ComponentType;)Ljava/lang/Object;")
    )
    private Object bookModelReturner(ItemStack instance, ComponentType<Identifier> componentType) {
        Identifier modelID = instance.get(componentType);

        if (modelID != null && modelID.equals(Identifier.of("minecraft", "enchanted_book"))) {
            ItemEnchantmentsComponent storedEnchantments = instance.getComponents().getOrDefault(
                    DataComponentTypes.STORED_ENCHANTMENTS, null
            );

            if (storedEnchantments != null && !storedEnchantments.isEmpty()) {
                return EnchantmentGetter.getEnchantment(
                        storedEnchantments.getEnchantments(), modelID
                );
            }
        }

        return modelID;
    }
}
