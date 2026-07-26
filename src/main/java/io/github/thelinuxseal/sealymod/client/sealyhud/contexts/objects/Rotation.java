package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects;

import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class Rotation {
    private double xRot;
    private double yRot;
    private final String[] directions = {
            "South (+Z)",
            "Southwest (-X, +Z)",
            "West (-X)",
            "Northwest (-X, -Z)",
            "North (-Z)",
            "Northeast (+X, -Z)",
            "East (+X)",
            "Southeast (+X, +Z)"
    };

    public void set(double xRot, double yRot) {
        this.xRot=xRot;
        this.yRot=yRot;
    }
    @ContextFunc(path = "Rotation().yaw()", name = "Yaw", desc = "Returns the yaw of the rotation.", returns = "double")
    double yaw(){return yRot;}
    @ContextFunc(path = "Rotation().pitch()", name = "Pitch", desc = "Returns the pitch of the rotation.", returns = "double")
    double pitch(){return xRot;}

    @ContextFunc(path = "Rotation().facing()", name = "Facing", desc = "Returns the cardinal direction that the rotation is facing.", returns = "String")
    String facing() {
        double heading = (yRot % 360 + 360) % 360;
        return directions[(int) Math.floor((heading + 22.5) / 45.0) & 7];
    }

}
