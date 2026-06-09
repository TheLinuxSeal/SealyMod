package com.thelinuxseal.sealymod.client.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.network.chat.Component;

public class CommandInitializer {
    public static void init(){
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommands.literal("sealymod").executes(context -> {
                context.getSource().sendFeedback(Component.literal("Called /sealymod."));
                return 1;
            }));
        });
    }
}
