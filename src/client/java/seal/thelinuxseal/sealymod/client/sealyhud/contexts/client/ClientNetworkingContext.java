package seal.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Minecraft;

public final class ClientNetworkingContext {
    public static String ping() {
        Minecraft client = Minecraft.getInstance();
        String ping = "...";
        if (client.player != null && client.getConnection() != null) {
            var playerInfo = client.getConnection().getPlayerInfo(client.player.getUUID());
            if (playerInfo != null) {
                ping = playerInfo.getLatency() + "ms";
            }
        }
        return ping;
    }
}
