package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.config.StatuesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.ModList;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfoStatueBlock extends AbstractBaseBlock {

	private static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 4.5D, 13.0D);
	private static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(5.5D, 4.5D, 5.5D, 10.5D, 7.0D, 10.5D);
	private static final VoxelShape SHAPE = VoxelShapes.or(BOTTOM_SHAPE, TOP_SHAPE);

	public InfoStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		sendInfoMessage(playerIn, worldIn, pos);
		return ActionResultType.SUCCESS;
	}

	public void sendInfoMessage(PlayerEntity player, World worldIn, BlockPos pos) {
		if (!worldIn.isRemote) {
			int random = worldIn.rand.nextInt(100);

			List<String> messages = new ArrayList<>(StatuesConfig.COMMON.info_messages.get());
			List<? extends String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();

			if(ModList.get().isLoaded("veinminer")) {
				messages.add("Did you know we have veinminer");
			}
			if(ModList.get().isLoaded("curios")) {
				messages.add("Did you know we have curios support");
			}

			int idx = new Random().nextInt(messages.size());
			ITextComponent randomMessage = new StringTextComponent(messages.get(idx));

			if(!luckyPlayers.isEmpty() && random < 20) {
				for (String luckyPlayer : luckyPlayers) {
					if (!luckyPlayer.isEmpty()) {
						String luckyUser = luckyPlayer.trim();
						if (player.getDisplayName().getUnformattedComponentText().equalsIgnoreCase(luckyUser)) {
							randomMessage = new StringTextComponent("Luck is not on your side today");
						}
					}
				}
			} else {
				LocalDate localdate = LocalDate.now();
				int i = localdate.get(ChronoField.DAY_OF_MONTH);
				int j = localdate.get(ChronoField.MONTH_OF_YEAR);

				if(worldIn.rand.nextDouble() <= 0.3D && j == 11 && i <= 20) {
					randomMessage = new StringTextComponent("Please check out our friends over at ")
							.mergeStyle(TextFormatting.YELLOW).appendSibling(ForgeHooks.newChatWithLinks("https://lovetropics.com/"));
				} else {
					randomMessage = new StringTextComponent(messages.get(idx));
				}
			}

			player.sendMessage(randomMessage, Util.DUMMY_UUID);
			worldIn.playSound(null, pos, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.NEUTRAL, 0.5F, 1.0F);
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
