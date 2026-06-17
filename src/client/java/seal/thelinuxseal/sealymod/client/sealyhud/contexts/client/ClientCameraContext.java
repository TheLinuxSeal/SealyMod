package seal.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;

public final class ClientCameraContext {
    public double exactX(){return Minecraft.getInstance().gameRenderer.mainCamera().position().x;}
    public double exactY(){return Minecraft.getInstance().gameRenderer.mainCamera().position().y;}
    public double exactZ(){return Minecraft.getInstance().gameRenderer.mainCamera().position().z;}
    public String x(){return String.format("%.2f", exactX());}
    public String y(){return String.format("%.2f", exactY());}
    public String z(){return String.format("%.2f", exactZ());}
    public int blockX(){return Minecraft.getInstance().gameRenderer.mainCamera().blockPosition().getX();}
    public int blockY(){return Minecraft.getInstance().gameRenderer.mainCamera().blockPosition().getY();}
    public int blockZ(){return Minecraft.getInstance().gameRenderer.mainCamera().blockPosition().getZ();}
    public double yaw(){return Minecraft.getInstance().gameRenderer.mainCamera().yRot();}
    public double pitch(){return Minecraft.getInstance().gameRenderer.mainCamera().xRot();}
    public String facing(){
        Camera camera = Minecraft.getInstance().gameRenderer.mainCamera();
        String direction = "...";
        if (camera != null) {
            float yaw = camera.yRot();
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
