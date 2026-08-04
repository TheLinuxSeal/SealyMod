package io.github.thelinuxseal.sealymod.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class SealyModCommand {
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(command());
        });
    }
    public static LiteralArgumentBuilder<FabricClientCommandSource> command(){
        return ClientCommands.literal("sealymod")
                .executes(SealyModCommand::func)
                .then(ConfigCommand.command());
    }
    public static int func(CommandContext<FabricClientCommandSource> context){return 1;}
}
