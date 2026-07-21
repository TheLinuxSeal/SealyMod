package seal.thelinuxseal.sealymod.client.sealyhud.contexts.common;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public interface PosContext {

    Vec3 position();

    BlockPos blockPosition();

    double yaw();

    double pitch();

    default double exactX() { return position().x; }
    default double exactY() { return position().y; }
    default double exactZ() { return position().z; }

    default double x() { return Math.round(exactX() * 100.0) / 100.0; }
    default double y() { return Math.round(exactY() * 100.0) / 100.0; }
    default double z() { return Math.round(exactZ() * 100.0) / 100.0; }

    default int blockX() { return blockPosition().getX(); }
    default int blockY() { return blockPosition().getY(); }
    default int blockZ() { return blockPosition().getZ(); }

    //default double yawDegrees() { return yaw(); }

    //default double pitchDegrees() { return pitch(); }

    default String facing() {
        double heading = (yaw() % 360 + 360) % 360;

        String[] directions = {
                "South (+Z)",
                "Southwest (-X, +Z)",
                "West (-X)",
                "Northwest (-X, -Z)",
                "North (-Z)",
                "Northeast (+X, -Z)",
                "East (+X)",
                "Southeast (+X, +Z)"
        };

        return directions[(int) Math.floor((heading + 22.5) / 45.0) & 7];
    }


}