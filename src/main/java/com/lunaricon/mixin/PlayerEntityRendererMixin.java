package com.lunaricon.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class PlayerEntityRendererMixin<T extends Entity, S extends EntityRenderState> {

    private static final Identifier ICON_FONT = Identifier.fromNamespaceAndPath("iconlunarclient", "default");

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void addLunarIconToSelf(T entity, S state, float tickDelta, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();

        if (client.player != null && entity.getUUID().equals(client.player.getUUID())) {
            if (state instanceof PlayerRenderState playerState && playerState.nameTag != null) {
                MutableComponent icon = Component.literal("\uE001 ")
                        .setStyle(Style.EMPTY.withFont(ICON_FONT).withColor(0xFFFFFF));

                playerState.nameTag = icon.append(playerState.nameTag);
            }
        }
    }
}
