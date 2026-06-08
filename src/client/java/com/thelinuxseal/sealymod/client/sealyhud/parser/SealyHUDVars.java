package com.thelinuxseal.sealymod.client.sealyhud.parser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

public final class SealyHUDVars {
    static Minecraft client = Minecraft.getInstance();
    static LocalPlayer player = client.player;
    static ClientLevel level = client.level;
    public static int getFPS() {
        return client.getFps();
    }
    public static String getX() {
        return player != null ? String.format("%.1f", player.getX()) : "...";
    }
    public static String getY() {
        return player != null ? String.format("%.1f", player.getY()) : "...";
    }
    public static String getZ() {
        return player != null ? String.format("%.1f", player.getZ()) : "...";
    }
    public static String getDim(){
        return level != null ? level.dimension().identifier().toString() : "...";
    }
    public static String getBiome(){
        return level != null ? level.getBiome(player.getOnPos()).getRegisteredName() : "...";
    }

    public static String getGameMode(){
        return client.gameMode != null ? client.gameMode.getPlayerMode().getName() : "...";
    }
    public static String getPing() {
        String ping = "...";
        if (player != null && client.getConnection() != null) {
            var playerInfo = client.getConnection().getPlayerInfo(player.getUUID());
            if (playerInfo != null) {
                ping = playerInfo.getLatency() + "ms";
            }
        }
        return ping;
    }
    public static String getDir(){
        String dir = "...";
        if (player != null) {
            float yaw = player.getYRot();
            // Normalize yaw to 0-360 range
            float heading = (yaw % 360 + 360) % 360;

            // An array of the 8 directions in clockwise order starting from South (0 degrees)
            String[] directions = {
                    "South (+Z)",          // 0° (337.5° - 22.5°)
                    "Southwest (-X, +Z)",   // 45°
                    "West (-X)",           // 90°
                    "Northwest (-X, -Z)",   // 135°
                    "North (-Z)",          // 180°
                    "Northeast (+X, -Z)",   // 225°
                    "East (+X)",           // 270°
                    "Southeast (+X, +Z)"    // 315°
            };

            // Offset by 22.5 degrees so that 0° sits dead-center in the South slice,
            // then divide by 45° slices and check the index.
            int index = (int) Math.floor((heading + 22.5) / 45.0) & 7;

            dir = directions[index];
        }
        return dir;
    }
}
