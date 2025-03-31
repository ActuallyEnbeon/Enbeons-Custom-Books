package com.enbeon.books.mixin.client;

import com.enbeon.books.EnchantmentGetter;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

public class EnchantedBookModelsMixins {
    @Mixin(ItemModelManager.class)
    public static class TwentyOnePointFour {
        @SuppressWarnings("ALL")
        @Redirect(
                method = "update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V",
                at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;get(Lnet/minecraft/component/ComponentType;)Ljava/lang/Object;")
        )
        private Object getModelForTwentyOnePointFour(ItemStack instance, ComponentType<Identifier> componentType) {
            return EnchantmentGetter.bookModelReturner(instance, componentType);
        }
    }

    @Mixin(ItemModelManager.class)
    public static class TwentyOnePointFive {
        @SuppressWarnings("ALL")
        @Redirect(
                method = "update",
                at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;get(Lnet/minecraft/component/ComponentType;)Ljava/lang/Object;")
        )
        private Object getModelForTwentyOnePointFive(ItemStack instance, ComponentType<Identifier> componentType) {
            return EnchantmentGetter.bookModelReturner(instance, componentType);
        }
    }
}
