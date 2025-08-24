package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.otherpet.Rat;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PracticalPets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class PPCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("practicalpets")
                .requires(src -> src.hasPermission(2))
                .then(Commands.literal("ratcube")
                        .executes(context -> spawnRatCubeAtPlayer(context, 1, true))
                        .then(Commands.argument("no_ai", BoolArgumentType.bool())
                                .executes(context -> spawnRatCubeAtPlayer(
                                        context,
                                        IntegerArgumentType.getInteger(context, "spacing"),
                                        BoolArgumentType.getBool(context, "no_ai")
                                ))
                                .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 8))
                                        .executes(context -> spawnRatCubeAtPlayer(
                                                context,
                                                IntegerArgumentType.getInteger(context, "spacing"),
                                                BoolArgumentType.getBool(context, "no_ai")
                                        ))
                                )
                                .then(Commands.argument("position", BlockPosArgument.blockPos())
                                        .executes(context -> spawnRatCubeAtPos(
                                                context,
                                                BlockPosArgument.getLoadedBlockPos(context, "position"),
                                                1,
                                                BoolArgumentType.getBool(context, "no_ai")
                                        ))
                                        .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 8))
                                                .executes(context -> spawnRatCubeAtPos(
                                                        context,
                                                        BlockPosArgument.getLoadedBlockPos(context, "position"),
                                                        IntegerArgumentType.getInteger(context, "spacing"),
                                                        BoolArgumentType.getBool(context, "no_ai")
                                                ))
                                        )
                                )
                        )
                )
        );
    }

    private static int spawnRatCubeAtPlayer(CommandContext<CommandSourceStack> context, int spacing, boolean noAi) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        Vec3 p = player.position();
        BlockPos base = new BlockPos((int) Math.floor(p.x), (int) Math.floor(p.y), (int) Math.floor(p.z));
        return spawnRatCube(context.getSource().getLevel(), base, spacing, player, noAi);
    }

    private static int spawnRatCubeAtPos(CommandContext<CommandSourceStack> context, BlockPos pos, int spacing, boolean noAi) {
        return spawnRatCube(context.getSource().getLevel(), pos, spacing, context.getSource().getPlayer(), noAi);
    }

    private static int spawnRatCube(ServerLevel level, BlockPos basePos, int spacing, ServerPlayer feedbackTarget, boolean noAi) {
        double baseX = basePos.getX() + 0.5;
        double baseY = basePos.getY();
        double baseZ = basePos.getZ() + 0.5;

        for (int i = 0; i < Rat.COLOR_TYPE_COUNT; i++) {
            for (int j = 0; j < Rat.COLOR_TYPE_COUNT; j++) {
                for (int k = 0; k < Rat.PATTERN_TYPE_COUNT; k++) {
                    Rat rat = new Rat(PPEntities.RAT.get(), level);
                    rat.moveTo(
                            (baseX + i - Rat.COLOR_TYPE_COUNT / 2) * spacing,
                            baseY + j * spacing,
                            (baseZ + k - Rat.PATTERN_TYPE_COUNT / 2) * spacing,
                            0.0F, 0.0F
                    );
                    rat.setIsAlbino(false);
                    rat.setAge(0);
                    rat.setNoAi(noAi);
                    rat.setVariant(i);
                    rat.setPatternColor(j);
                    rat.setPatternType(k);

                    String a = String.valueOf((char) ('A' + i));
                    String b = String.valueOf((char) ('A' + j));
                    rat.setCustomName(Component.literal(a + b + k));

                    level.addFreshEntity(rat);
                }
            }
        }

        return 1;
    }
}