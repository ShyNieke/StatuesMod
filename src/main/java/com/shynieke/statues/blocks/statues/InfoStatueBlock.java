package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.config.StatuesConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.ModList;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfoStatueBlock extends AbstractBaseBlock {

	private static final VoxelShape BOTTOM_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 4.5D, 13.0D);
	private static final VoxelShape TOP_SHAPE = Block.box(5.5D, 4.5D, 5.5D, 10.5D, 7.0D, 10.5D);
	private static final VoxelShape SHAPE = Shapes.or(BOTTOM_SHAPE, TOP_SHAPE);

	public InfoStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		sendInfoMessage(playerIn, worldIn, pos);
		return InteractionResult.SUCCESS;
	}

	public void sendInfoMessage(Player player, Level worldIn, BlockPos pos) {
		if (!worldIn.isClientSide) {
			int random = worldIn.random.nextInt(100);

			List<String> messages = new ArrayList<>(StatuesConfig.COMMON.info_messages.get());
			List<? extends String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();

			if (ModList.get().isLoaded("veinminer")) {
				messages.add("Did you know we have veinminer");
			}
			if (ModList.get().isLoaded("curios")) {
				messages.add("Did you know we have curios support");
			}

			int idx = new Random().nextInt(messages.size());
			Component randomMessage = Component.literal(messages.get(idx));

			if (!luckyPlayers.isEmpty() && random < 20) {
				for (String luckyPlayer : luckyPlayers) {
					if (!luckyPlayer.isEmpty()) {
						String luckyUser = luckyPlayer.trim();
						if (player.getDisplayName().getString().equalsIgnoreCase(luckyUser)) {
							randomMessage = Component.literal("Luck is not on your side today");
						}
					}
				}
			} else {
				LocalDate localdate = LocalDate.now();
				int i = localdate.get(ChronoField.DAY_OF_MONTH);
				int j = localdate.get(ChronoField.MONTH_OF_YEAR);

				if (worldIn.random.nextDouble() <= 0.3D && j == 11 && i <= 20) {
					randomMessage = Component.literal("Please check out our friends over at ")
							.withStyle(ChatFormatting.YELLOW).append(ForgeHooks.newChatWithLinks("https://lovetropics.com/"));
				} else {
					randomMessage = Component.literal(messages.get(idx));
				}
			}

			player.sendSystemMessage(randomMessage);
			worldIn.playSound(null, pos, SoundEvents.DISPENSER_FAIL, SoundSource.NEUTRAL, 0.5F, 1.0F);
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
