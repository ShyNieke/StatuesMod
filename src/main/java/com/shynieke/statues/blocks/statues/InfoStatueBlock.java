package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.compat.patchouli.PatchouliCompat;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.fml.ModList;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class InfoStatueBlock extends AbstractBaseBlock {

	private static final VoxelShape BOTTOM_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 4.5D, 13.0D);
	private static final VoxelShape TOP_SHAPE = Block.box(5.5D, 4.5D, 5.5D, 10.5D, 7.0D, 10.5D);
	private static final VoxelShape SHAPE = Shapes.or(BOTTOM_SHAPE, TOP_SHAPE);

	public InfoStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		if (handIn == InteractionHand.MAIN_HAND) {
			ItemStack heldItem = playerIn.getMainHandItem();
			if (heldItem.is(Items.BOOK) && ModList.get().isLoaded("patchouli")) {
				PatchouliCompat.convertBook(playerIn);
				return InteractionResult.SUCCESS;
			}
			//Debug option specifically for Shy to know what version is being used
			if (heldItem.is(Items.PAPER) && !(playerIn instanceof FakePlayer) &&
					playerIn.getGameProfile().getId().equals(UUID.fromString("7135da42-d327-47bb-bb04-5ba4e212fb32"))) {
				if (!level.isClientSide)
					playerIn.sendSystemMessage(Component.literal("Statues version: ")
							.append(Component.literal(ModList.get().getModFileById("statues").versionString())).withStyle(ChatFormatting.GOLD));
				return InteractionResult.SUCCESS;
			}

			if (heldItem.is(StatueTags.UPGRADEABLE_STATUES) && !(playerIn instanceof FakePlayer) && upgraded(heldItem)) {
				if (heldItem.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractStatueBase statueBase) {
					if (!level.isClientSide)
						playerIn.sendSystemMessage(Component.literal("To level this statue you need to kill ")
								.append(Component.translatable(statueBase.getEntity().getDescriptionId()).withStyle(ChatFormatting.GOLD)));
				}
				return InteractionResult.SUCCESS;
			}

			sendInfoMessage(playerIn, level, pos);
		}
		return InteractionResult.SUCCESS;
	}

	private boolean upgraded(ItemStack stack) {
		return stack.getTagElement("BlockEntityTag") != null && stack.getTagElement("BlockEntityTag").getBoolean(Reference.UPGRADED);
	}

	public void sendInfoMessage(Player player, Level level, BlockPos pos) {
		if (!level.isClientSide) {
			int random = level.random.nextInt(100);

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

				if (level.random.nextDouble() <= 0.3D && j == 11 && i <= 20) {
					randomMessage = Component.literal("Please check out our friends over at ")
							.withStyle(ChatFormatting.YELLOW).append(CommonHooks.newChatWithLinks("https://lovetropics.com/"));
				} else {
					randomMessage = Component.literal(messages.get(idx));
				}
			}

			player.sendSystemMessage(randomMessage);
			level.playSound(null, pos, SoundEvents.DISPENSER_FAIL, SoundSource.NEUTRAL, 0.5F, 1.0F);
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
