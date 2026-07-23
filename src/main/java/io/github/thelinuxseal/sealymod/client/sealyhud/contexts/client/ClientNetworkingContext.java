package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Minecraft;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class ClientNetworkingContext {
    @ContextFunc(path = "client.networking.ping()", name = "Ping", desc = "Returns the ping if the player is connected to something, and -1 if not.", returns = "int")
    public static int ping() {
        Minecraft client = Minecraft.getInstance();
        int ping = -1;
        if (client.player != null && client.getConnection() != null) {
            var playerInfo = client.getConnection().getPlayerInfo(client.player.getUUID());
            if (playerInfo != null) {
                ping = playerInfo.getLatency();
            }
        }
        return ping;
    }
}
