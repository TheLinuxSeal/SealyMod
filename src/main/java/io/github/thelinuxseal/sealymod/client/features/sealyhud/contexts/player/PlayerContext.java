package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.player;

import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects.ItemContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects.Position;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects.Rotation;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextClass;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;

public final class PlayerContext {
    //public final PlayerPosContext pos = new PlayerPosContext();
    private LocalPlayer player(){return Minecraft.getInstance().player;}

    private final Position positionInstance = new Position();
    private final Rotation rotationInstance = new Rotation();
    private final ItemContainer itemContainerInstance = new ItemContainer();

    @ContextFunc(name="Player Position", desc="Returns the position of the player.", path="player.position()", returns = "Position")
    public Position position(){
        positionInstance.set(player().position(),player().blockPosition());
        return positionInstance;
    }
    @ContextFunc(name="Player Rotation", desc="Returns the rotation of the player.", path="player.rotation()", returns = "Rotation")
    public Rotation rotation(){
        rotationInstance.set(player().getXRot(),player().getYRot());
        return rotationInstance;
    }
    @ContextClass(name="Player Looking")
    public final PlayerLookingContext looking = new PlayerLookingContext();
    //public ItemContainer inventory(){}
    @ContextFunc(path = "player.dimension()", name = "Dimension", desc = "The dimension that the player is in.", returns = "String")
    public String dimension(){
        Minecraft client = Minecraft.getInstance();
        return client.level != null ? client.level.dimension().identifier().toString() : "...";
    }
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

    @ContextFunc(path="player.inventory()",name="Player Inventory", desc="Returns the inventory of the player",returns="ItemContainter()")
    public ItemContainer inventory(){
        itemContainerInstance.setFromPlayerInventory(player().getInventory());
        return itemContainerInstance;
    }

}
