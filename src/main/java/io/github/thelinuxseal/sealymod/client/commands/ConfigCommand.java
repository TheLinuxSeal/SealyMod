package io.github.thelinuxseal.sealymod.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.thelinuxseal.sealymod.common.ConfigHandler;
import io.github.thelinuxseal.sealymod.client.config.RootConfigScreen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;


public class ConfigCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> command(){
        return ClientCommands.literal("config").executes(ConfigCommand::func);
    }
    public static int func(CommandContext<FabricClientCommandSource> context) {
        Minecraft client = Minecraft.getInstance();

        Minecraft.getInstance().schedule(() -> {
            Minecraft.getInstance().gui.setScreen(RootConfigScreen.create(null,ConfigHandler.get()));
        });


        return 1;
    }
}
