package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common.Position;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common.Rotation;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextClass;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class PlayerContext {
    //public final PlayerPosContext pos = new PlayerPosContext();
    private LocalPlayer player(){return Minecraft.getInstance().player;}
    @ContextFunc(name="Player Position", desc="Returns the position of the player.", path="player.position()", returns = "Position")
    public Position position(){return new Position(player().position(),player().blockPosition());}
    @ContextFunc(name="Player Rotation", desc="Returns the rotation of the player.", path="player.rotation()", returns = "Rotation")
    public Rotation rotation(){return new Rotation(player().getXRot(),player().getYRot());}
    @ContextClass(name="Player Looking")
    public final PlayerLookingContext looking = new PlayerLookingContext();
    @ContextFunc(path = "player.gameMode()", name = "Player Gamemode", desc = "Returns the player's gamemode.", returns = "String")
    public String gameMode(){
        Minecraft client = Minecraft.getInstance();
        return client.gameMode != null ? client.gameMode.getPlayerMode().getName() : "";
    }

    @ContextFunc(path = "player.uuid()", name = "Player UUID", desc = "Returns the player's uuid.", returns = "String")
    public String uuid(){
        Minecraft client = Minecraft.getInstance();
        return client.player != null ? client.player.getUUID().toString() : "";
    }

}
