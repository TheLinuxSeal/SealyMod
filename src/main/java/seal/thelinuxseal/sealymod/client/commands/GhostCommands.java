package seal.thelinuxseal.sealymod.client.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument; // Replaced BlockPosArgument
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;

import java.util.concurrent.atomic.AtomicInteger;

public class GhostCommands {
    private static final AtomicInteger GHOST_ENTITY_ID_COUNTER = new AtomicInteger(-1);

    public static LiteralArgumentBuilder<FabricClientCommandSource> build(CommandBuildContext registryAccess) {

        SealyModConfig config = SealyModConfigHandler.get();

        LiteralArgumentBuilder<FabricClientCommandSource> ghostCommands = ClientCommands.literal("ghost");
        
        if (config.commands.enableGhostSetBlock){
            ghostCommands = ghostCommands.then(
                    ClientCommands.literal("setblock")
                    .then(ClientCommands.argument("pos", BlockPosArgument.blockPos())
                            .then(ClientCommands.argument(
                                            "block",
                                            BlockStateArgument.block(registryAccess))
                                    .executes(GhostCommands::ghostSetBlock)
                            )
                    )
            );
        }

        if (config.commands.enableGhostFill) {
            ghostCommands = ghostCommands.then(
                    ClientCommands.literal("fill")
                            .then(ClientCommands.argument("from", Vec3Argument.vec3(false))
                                    .then(ClientCommands.argument("to", Vec3Argument.vec3(false))
                                            .then(ClientCommands.argument(
                                                            "block",
                                                            BlockStateArgument.block(registryAccess))
                                                    .executes(GhostCommands::ghostFill)
                                            )
                                    )
                            )
            );
        }

        if (config.commands.enableGhostGive) {
            ghostCommands = ghostCommands.then(
                    ClientCommands.literal("give")
                            .then(ClientCommands.argument("item", ItemArgument.item(registryAccess))
                                    .then(ClientCommands.argument("amount", IntegerArgumentType.integer())
                                            .executes(GhostCommands::ghostGive)))
            );
        }

        if (config.commands.enableGhostSummon) {
            ghostCommands = ghostCommands.then(
                    ClientCommands.literal("summon")
                            .then(ClientCommands.argument("entity", ResourceArgument.resource(registryAccess, Registries.ENTITY_TYPE))
                                    .suggests(SuggestionProviders.cast(SuggestionProviders.SUMMONABLE_ENTITIES))
                                    .executes(GhostCommands::ghostSummon))
            );
        }

        return ghostCommands;
    }

    private static CommandSourceStack makeFakeStack(Minecraft client){
        return new CommandSourceStack(
                CommandSource.NULL,                        // Base source
                client.player.position(),                   // Vec3 Position
                client.player.getRotationVector(),          // Vec2 Rotation (Pitch/Yaw)
                null,                                       // ServerLevel (Pass null safely on client)
                PermissionSet.NO_PERMISSIONS,                                          // Permission level
                client.player.getName().getString(),        // Text Name
                client.player.getDisplayName(),             // Component DisplayName
                null,                                       // MinecraftServer (Leave null)
                client.player                               // Entity context anchor
        );

    }

    private static int ghostSetBlock(CommandContext<FabricClientCommandSource> context) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return 0;
        if (client.player == null) return 0;
        // Extract the vector evaluated against client player position/camera orientation
        Coordinates coords =
                context.getArgument("pos", Coordinates.class);

        BlockPos pos =
                coords.getBlockPos(makeFakeStack(client));

        BlockInput input = context.getArgument("block", BlockInput.class);

        client.level.setBlock(
                pos,
                input.getState(),
                3
        );

        return 1;
    }

    private static int ghostFill(CommandContext<FabricClientCommandSource> context) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return 0;
        if (client.player == null) return 0;

        // Safely extract and floor the "from" coordinates
        Coordinates coords1 =
                context.getArgument("from", Coordinates.class);

        BlockPos from =
                coords1.getBlockPos(makeFakeStack(client));

        // Safely extract and floor the "to" coordinates
        Coordinates coords2 =
                context.getArgument("to", Coordinates.class);

        BlockPos to =
                coords2.getBlockPos(makeFakeStack(client));

        BlockInput input = context.getArgument("block", BlockInput.class);

        BlockPos.betweenClosedStream(from, to).forEach(pos -> {
            client.level.setBlock(pos, input.getState(), 3);
        });

        return 1;
    }

    private static int ghostGive(CommandContext<FabricClientCommandSource> context) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return 0;
        ItemInput input = context.getArgument("item", ItemInput.class);
        try {
            ItemStack itemStack = input.createItemStack(context.getArgument("amount", Integer.class));
            client.player.getInventory().add(itemStack);
            return 1;
        } catch (CommandSyntaxException e) {
            return 0;
        }
    }

    private static int ghostSummon(CommandContext<FabricClientCommandSource> context) {

        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return 0;
        if (client.player == null) return 0;
        EntityType<?> type =
                (EntityType<?>) context.getArgument("entity", Holder.Reference.class).value();

        Entity e = type.create(client.level, EntitySpawnReason.LOAD);
        if (e != null) {
            // Position the entity directly at the client's current location
            e.setPos(new Vec3(client.player.getX(),client.player.getY(),client.player.getZ()));
            e.setId(GHOST_ENTITY_ID_COUNTER.getAndDecrement());
            client.level.addEntity(e);
            return 1;
        }
        return 0;
    }
}
