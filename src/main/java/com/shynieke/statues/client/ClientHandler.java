package com.shynieke.statues.client;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.shynieke.statues.Reference;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.blocks.statues.fish.FishStatueBlock;
import com.shynieke.statues.client.model.PlayerStatueModel;
import com.shynieke.statues.client.render.PlayerStatueRenderer;
import com.shynieke.statues.client.render.PlayerTileRenderer;
import com.shynieke.statues.client.render.StatueBatRenderer;
import com.shynieke.statues.init.StatueBlockEntities;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.io.File;

public class ClientHandler {
	public static final ModelLayerLocation PLAYER_STATUE = new ModelLayerLocation(new ResourceLocation(Reference.MOD_ID, "player_statue"), "player_statue");
	public static final ModelLayerLocation PLAYER_STATUE_SLIM = new ModelLayerLocation(new ResourceLocation(Reference.MOD_ID, "player_statue_slim"), "player_statue_slim");

	public static void doClientStuff(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.CAMPFIRE_STATUE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.DROWNED_STATUE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.HUSK_STATUE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.ZOMBIE_STATUE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.ENDERMAN_STATUE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.INFO_STATUE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.WASTELAND_STATUE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.TRANS_BEE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.ENDERMITE_STATUE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(StatueRegistry.SLIME_STATUE.get(), RenderType.translucent());

		setPlayerCache(Minecraft.getInstance());

		ItemProperties.register(StatueRegistry.PLAYER_COMPASS.get(), new ResourceLocation("angle"), new ClampedItemPropertyFunction() {
			private final ClientHandler.Angle rotation = new ClientHandler.Angle();
			private final ClientHandler.Angle rota = new ClientHandler.Angle();

			public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entityIn, int p_174567_) {
				Entity entity = (Entity) (entityIn != null ? entityIn : stack.getEntityRepresentation());
				if (entity == null) {
					return 0.0F;
				} else {
					if (level == null && entity.level instanceof ClientLevel) {
						level = (ClientLevel) entity.level;
					}
					if (level != null) {
						BlockPos blockpos = this.getWorldPos(level);
						long gameTime = level.getGameTime();

						CompoundTag tag = stack.getTag();
						if (tag != null && tag.contains("lastPlayerLocation")) {
							long location = tag.getLong("lastPlayerLocation");
							if (location != 0L) {
								blockpos = BlockPos.of(location);
							}
						}

						if (blockpos != null && !(entity.distanceToSqr((double) blockpos.getX() + 0.5D, entity.position().y(), (double) blockpos.getZ() + 0.5D) < (double) 1.0E-5F)) {
							boolean flag = entity instanceof Player && ((Player) entity).isLocalPlayer();
							double d1 = 0.0D;
							if (flag) {
								d1 = (double) entityIn.getYRot();
							} else if (entity instanceof ItemFrame) {
								d1 = this.getFrameRotation((ItemFrame) entity);
							} else if (entity instanceof ItemEntity) {
								d1 = (double) (180.0F - ((ItemEntity) entity).getSpin(0.5F) / ((float) Math.PI * 2F) * 360.0F);
							} else if (entityIn != null) {
								d1 = (double) entityIn.yBodyRot;
							}

							d1 = Mth.positiveModulo(d1 / 360.0D, 1.0D);
							double d2 = this.getLocationToAngle(Vec3.atCenterOf(blockpos), entity) / (double) ((float) Math.PI * 2F);
							double d3;
							if (flag) {
								if (this.rotation.shouldUpdate(gameTime)) {
									this.rotation.update(gameTime, 0.5D - (d1 - 0.25D));
								}
								d3 = d2 + this.rotation.rotation;
							} else {
								d3 = 0.5D - (d1 - 0.25D - d2);
							}

							return Mth.positiveModulo((float) d3, 1.0F);
						} else {
							if (this.rota.shouldUpdate(gameTime)) {
								this.rota.update(gameTime, Math.random());
							}

							double d0 = this.rota.rotation + (double) ((float) stack.hashCode() / 2.14748365E9F);
							return Mth.positiveModulo((float) d0, 1.0F);
						}
					}
					double d0 = this.rota.rotation + (double) ((float) stack.hashCode() / 2.14748365E9F);
					return Mth.positiveModulo((float) d0, 1.0F);
				}
			}

			@Nullable
			private BlockPos getWorldPos(ClientLevel level) {
				return level.dimensionType().natural() ? level.getSharedSpawnPos() : null;
			}

			private double getFrameRotation(ItemFrame itemFrameIn) {
				Direction direction = itemFrameIn.getDirection();
				int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
				return (double) Mth.wrapDegrees(180 + direction.get2DDataValue() * 90 + itemFrameIn.getRotation() * 45 + i);
			}

			private double getLocationToAngle(Vec3 location, Entity entityIn) {
				return Math.atan2(location.z() - entityIn.getZ(), location.x() - entityIn.getX());
			}
		});

		if (ModList.get().isLoaded("curios")) {
			com.shynieke.statues.compat.curios.client.StatueCurioRenderer.setupRenderer(event);
		}
	}

	@OnlyIn(Dist.CLIENT)
	static class Angle {
		private double rotation;
		private double deltaRotation;
		private long lastUpdateTick;

		private Angle() {
		}

		private boolean shouldUpdate(long p_239448_1_) {
			return this.lastUpdateTick != p_239448_1_;
		}

		private void update(long updateTick, double p_239449_3_) {
			this.lastUpdateTick = updateTick;
			double d0 = p_239449_3_ - this.rotation;
			d0 = Mth.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
			this.deltaRotation += d0 * 0.1D;
			this.deltaRotation *= 0.8D;
			this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0D);
		}
	}

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(StatueBlockEntities.PLAYER.get(), PlayerTileRenderer::new);

		event.registerEntityRenderer(StatueRegistry.PLAYER_STATUE_ENTITY.get(), PlayerStatueRenderer::new);
		event.registerEntityRenderer(StatueRegistry.STATUE_BAT.get(), StatueBatRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(PLAYER_STATUE, () -> LayerDefinition.create(PlayerStatueModel.createStatueMesh(CubeDeformation.NONE, false), 64, 64));
		event.registerLayerDefinition(PLAYER_STATUE_SLIM, () -> LayerDefinition.create(PlayerStatueModel.createStatueMesh(CubeDeformation.NONE, true), 64, 64));
	}

	public static void registerBlockColors(final ColorHandlerEvent.Block event) {
		BlockColors colors = event.getBlockColors();

		colors.register(FishStatueBlock::getColor, StatueRegistry.TROPICAL_FISH_B.get(), StatueRegistry.TROPICAL_FISH_BB.get(), StatueRegistry.TROPICAL_FISH_BE.get(),
				StatueRegistry.TROPICAL_FISH_BM.get(), StatueRegistry.TROPICAL_FISH_BMB.get(), StatueRegistry.TROPICAL_FISH_BMS.get(),
				StatueRegistry.TROPICAL_FISH_E.get(), StatueRegistry.TROPICAL_FISH_ES.get(), StatueRegistry.TROPICAL_FISH_HB.get(),
				StatueRegistry.TROPICAL_FISH_SB.get(), StatueRegistry.TROPICAL_FISH_SD.get(), StatueRegistry.TROPICAL_FISH_SS.get());
	}

	public static void onLogin(ClientPlayerNetworkEvent.LoggedInEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (!mc.isLocalServer()) {
			setPlayerCache(mc);
		}
	}

	public static void onRespawn(ClientPlayerNetworkEvent.RespawnEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (!mc.isLocalServer()) {
			setPlayerCache(mc);
		}
	}

	public static void preStitchEvent(TextureStitchEvent.Pre event) {
		if (ModList.get().isLoaded("curios") && event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
			event.addSprite(new ResourceLocation(Reference.MOD_ID, "curios/core"));
		}
	}

	private static void setPlayerCache(Minecraft mc) {
		AuthenticationService authenticationService = new YggdrasilAuthenticationService(mc.getProxy());
		MinecraftSessionService sessionService = authenticationService.createMinecraftSessionService();
		GameProfileRepository profileRepository = authenticationService.createProfileRepository();
		GameProfileCache profileCache = new GameProfileCache(profileRepository, new File(mc.gameDirectory, MinecraftServer.USERID_CACHE_FILE.getName()));
		profileCache.setExecutor(mc);
		PlayerBlockEntity.setup(profileCache, sessionService, mc);
		GameProfileCache.setUsesAuthentication(false);
	}
}
