package com.silentclient.client.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Runs once, right as the client starts up. Reserved as a safe place to hook
 * into early client initialization if a future module needs it (e.g. reading
 * launch args). Intentionally does nothing destructive by default.
 */
@Mixin(Minecraft.class)
public class MinecraftClientMixin {
	@Inject(at = @At("HEAD"), method = "run")
	private void silentclient$onClientStart(CallbackInfo info) {
		// Reserved hook. All actual Silent Client logic lives in
		// com.silentclient.client.SilentClient and its modules, registered
		// through Fabric API's client lifecycle/event hooks rather than mixins,
		// so that the mod stays easy to audit and safe to run on any server.
	}
}
