package com.silentclient;

import net.fabricmc.api.ModInitializer;

/**
 * Silent Client is entirely client-side, so there is nothing to do on the
 * common/server initializer. It exists to satisfy the standard Fabric mod
 * entrypoint structure and so that {@code fabric.mod.json} has a valid
 * "main" entrypoint.
 */
public class SilentClientMod implements ModInitializer {
	public static final String MOD_ID = "silentclient";

	@Override
	public void onInitialize() {
		// No common-side logic. See com.silentclient.client.SilentClient
		// for the actual mod initialization.
	}
}
