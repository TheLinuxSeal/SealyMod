package seal.thelinuxseal.sealymod.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.config.data.CommandsConfig;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;

public class CommandInitializer {
    public static LiteralArgumentBuilder<FabricClientCommandSource>  create(CommandBuildContext registryAccess){
        LiteralArgumentBuilder<FabricClientCommandSource> commands = ClientCommands.literal("sealymod");
        LiteralArgumentBuilder<FabricClientCommandSource> ghost =
                GhostCommands.build(registryAccess);

        if (!ghost.getArguments().isEmpty()) {
            commands.then(ghost);
        }

        commands.executes(CommandInitializer::run);

        return commands;
    }


    public static void init(){

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    create(registryAccess)
            );
        });
    }

    private static int run(CommandContext<FabricClientCommandSource> context) {
        MutableComponent message = ((MutableComponent) SealyModLang.getAsComponent("sealymod.name")).append("\n");

        message.append(SealyModLang.getAsComponent("sealymod.commands.sealymod.feedback.availableCommands")).append("\n");

        SealyModConfig config = SealyModConfigHandler.get();

        appendCommand(
                message,
                "/sealymod ghost setblock",
                config.commands.enableGhostSetBlock
        );

        appendCommand(
                message,
                "/sealymod ghost fill",
                config.commands.enableGhostFill
        );

        appendCommand(
                message,
                "/sealymod ghost give",
                config.commands.enableGhostGive
        );

        appendCommand(
                message,
                "/sealymod ghost summon",
                config.commands.enableGhostSummon
        );

        context.getSource().sendFeedback(message);
        return 1;
    }
    private static void appendCommand(
            MutableComponent message,
            String command,
            boolean enabled
    ) {
        if (enabled) {
            message.append(
                    Component.literal(command)
            );
        } else {
            message.append(
                    Component.literal(command + " " + SealyModLang.get("sealymod.commands.sealymod.feedback.disabledSuffix"))
                            .withStyle(ChatFormatting.DARK_GRAY)
            );
        }

        message.append(Component.literal("\n"));
    }
}
