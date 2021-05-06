package net.dodogang.marbles.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.util.SpotlightUtil;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class MarblesDebugCommand {
    public static final Identifier ID = new Identifier(Marbles.MOD_ID, "debug");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal(MarblesDebugCommand.ID.toString()).requires(src -> src.hasPermissionLevel(2)).then(
                CommandManager.literal("setspotlight").then(
                    CommandManager.argument("pos", BlockPosArgumentType.blockPos()).then(
                        CommandManager.argument("val", IntegerArgumentType.integer()).executes(
                            ctx -> {
                                BlockPos pos = BlockPosArgumentType.getBlockPos(ctx, "pos");
                                int val = IntegerArgumentType.getInteger(ctx, "val");
                                SpotlightUtil.setSpotlightData(ctx.getSource().getWorld(), pos, val, true);
                                return 1;
                            }
                        )
                    )
                )
            )
        );
    }
}
