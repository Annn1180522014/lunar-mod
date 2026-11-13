package com.lunaricon.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerEntityRendererMixin {

    private static final ResourceLocation ICON_FONT = ResourceLocation.fromNamespaceAndPath("iconlunarclient", "default");

    @Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("TAIL"))
    private void addLunarIconToSelf(AbstractClientPlayer player, PlayerRenderState state, float tickDelta, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();

        if (client.player != null && player.getUUID().equals(client.player.getUUID())) {
            if (state.nameTag != null) {
                MutableComponent icon = Component.literal("\uE001 ")
                        .setStyle(Style.EMPTY.withFont(ICON_FONT).withColor(0xFFFFFF));

                state.nameTag = icon.append(state.nameTag);
            }
        }
    }
}
