package com.thelinuxseal.sealymod.client.commands;


import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class GhostCommands {
    public static CommandNode<FabricClientCommandSource> init(){
        //setblock = ClientCommands.literal("give").executes()
        //return ClientCommands.literal("ghost").then();
    }
}
