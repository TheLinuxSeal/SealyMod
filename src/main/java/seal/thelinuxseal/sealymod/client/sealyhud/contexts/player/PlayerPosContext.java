package seal.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class PlayerPosContext {
    public double exactX(){return Minecraft.getInstance().player.getX();}
    public double exactY(){return Minecraft.getInstance().player.getY();}
    public double exactZ(){return Minecraft.getInstance().player.getZ();}
    public double x() { return Math.round(exactX() * 100.0) / 100.0; }
    public double y() { return Math.round(exactY() * 100.0) / 100.0; }
    public double z() { return Math.round(exactZ() * 100.0) / 100.0; }
    public int blockX(){return Minecraft.getInstance().player.getBlockX();}
    public int blockY(){return Minecraft.getInstance().player.getBlockY();}
    public int blockZ(){return Minecraft.getInstance().player.getBlockZ();}
    public double yaw(){return Minecraft.getInstance().player.getYRot();}
    public double pitch(){return Minecraft.getInstance().player.getXRot();}
    public String facing(){
        LocalPlayer player = Minecraft.getInstance().player;
        String direction = "...";
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

            direction = directions[index];
        }
        return direction;
    }

}
