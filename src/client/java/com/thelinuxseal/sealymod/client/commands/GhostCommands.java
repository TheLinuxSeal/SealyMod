package com.thelinuxseal.sealymod.client.commands;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class GhostCommands {
    public static LiteralArgumentBuilder<FabricClientCommandSource> init(){
        //setblock = ClientCommands.literal("give").executes()
        return ClientCommands.literal("ghost");
    }
}
