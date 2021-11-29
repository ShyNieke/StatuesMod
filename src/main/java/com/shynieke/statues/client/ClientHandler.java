package com.shynieke.statues.client;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.shynieke.statues.blocks.statues.fish.FishStatueBlock;
import com.shynieke.statues.client.render.PlayerStatueRenderer;
import com.shynieke.statues.client.render.PlayerTileRenderer;
import com.shynieke.statues.client.render.StatueBatRenderer;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueTiles;
import com.shynieke.statues.items.CustomSpawnEggItem;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.io.File;
import java.util.UUID;

public class ClientHandler {

    public static void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(StatueTiles.PLAYER, PlayerTileRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(StatueRegistry.PLAYER_STATUE_ENTITY.get(), PlayerStatueRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(StatueRegistry.STATUE_BAT.get(), StatueBatRenderer::new);

        RenderTypeLookup.setRenderLayer(StatueRegistry.CAMPFIRE_STATUE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueRegistry.DROWNED_STATUE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueRegistry.HUSK_STATUE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueRegistry.ZOMBIE_STATUE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueRegistry.ENDERMAN_STATUE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueRegistry.INFO_STATUE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueRegistry.WASTELAND_STATUE.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(StatueRegistry.ENDERMITE_STATUE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(StatueRegistry.SLIME_STATUE.get(), RenderType.translucent());

        Minecraft mc = Minecraft.getInstance();
        YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(mc.getProxy(), UUID.randomUUID().toString());
        MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
        GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
        PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(mc.gameDirectory, MinecraftServer.USERID_CACHE_FILE.getName()));
        PlayerTile.setProfileCache(playerprofilecache);
        PlayerTile.setSessionService(minecraftsessionservice);
        PlayerProfileCache.setUsesAuthentication(false);

        ItemModelsProperties.register(StatueRegistry.PLAYER_COMPASS.get(), new ResourceLocation("angle"), new IItemPropertyGetter() {
            private final ClientHandler.Angle rotation = new ClientHandler.Angle();
            private final ClientHandler.Angle rota = new ClientHandler.Angle();

            public float call(ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
                Entity entity = (Entity)(entityIn != null ? entityIn : stack.getEntityRepresentation());
                if (entity == null) {
                    return 0.0F;
                } else {
                    if (entity != null && entity.level instanceof ClientWorld) {
                        worldIn = (ClientWorld)entity.level;
                    }
                    if(worldIn != null) {
                        BlockPos blockpos = this.getWorldPos(worldIn);
                        long gameTime = worldIn.getGameTime();

                        CompoundNBT tag = stack.getTag();
                        if (tag != null && tag.contains("lastPlayerLocation")) {
                            long location = tag.getLong("lastPlayerLocation");
                            if (location != 0L) { blockpos = BlockPos.of(location); }
                        }

                        if (blockpos != null && !(entity.position().distanceToSqr((double)blockpos.getX() + 0.5D, entity.position().y(), (double)blockpos.getZ() + 0.5D) < (double)1.0E-5F)) {
                            boolean flag = entity instanceof PlayerEntity && ((PlayerEntity)entityIn).isLocalPlayer();
                            double d1 = 0.0D;
                            if (flag) {
                                d1 = (double)entityIn.yRot;
                            } else if (entity instanceof ItemFrameEntity) {
                                d1 = this.getFrameRotation((ItemFrameEntity)entity);
                            } else if (entity instanceof ItemEntity) {
                                d1 = (double)(180.0F - ((ItemEntity)entity).getSpin(0.5F) / ((float)Math.PI * 2F) * 360.0F);
                            } else if (entityIn != null) {
                                d1 = (double)entityIn.yBodyRot;
                            }

                            d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                            double d2 = this.getLocationToAngle(Vector3d.atCenterOf(blockpos), entity) / (double)((float)Math.PI * 2F);
                            double d3;
                            if (flag) {
                                if (this.rotation.shouldUpdate(gameTime)) { this.rotation.update(gameTime, 0.5D - (d1 - 0.25D)); }
                                d3 = d2 + this.rotation.rotation;
                            } else {
                                d3 = 0.5D - (d1 - 0.25D - d2);
                            }

                            return MathHelper.positiveModulo((float)d3, 1.0F);
                        } else {
                            if (this.rota.shouldUpdate(gameTime)) {
                                this.rota.update(gameTime, Math.random());
                            }

                            double d0 = this.rota.rotation + (double)((float)stack.hashCode() / 2.14748365E9F);
                            return MathHelper.positiveModulo((float)d0, 1.0F);
                        }
                    }
                    double d0 = this.rota.rotation + (double)((float)stack.hashCode() / 2.14748365E9F);
                    return MathHelper.positiveModulo((float)d0, 1.0F);
                }
            }

            @Nullable
            private BlockPos getWorldPos(ClientWorld world) {
                return world.dimensionType().natural() ? world.getSharedSpawnPos() : null;
            }

            private double getFrameRotation(ItemFrameEntity itemFrameIn) {
                Direction direction = itemFrameIn.getDirection();
                int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
                return (double)MathHelper.wrapDegrees(180 + direction.get2DDataValue() * 90 + itemFrameIn.getRotation() * 45 + i);
            }

            private double getLocationToAngle(Vector3d location, Entity entityIn) {
                return Math.atan2(location.z() - entityIn.getZ(), location.x() - entityIn.getX());
            }
        });
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

        private void update(long p_239449_1_, double p_239449_3_) {
            this.lastUpdateTick = p_239449_1_;
            double d0 = p_239449_3_ - this.rotation;
            d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
            this.deltaRotation += d0 * 0.1D;
            this.deltaRotation *= 0.8D;
            this.rotation = MathHelper.positiveModulo(this.rotation + this.deltaRotation, 1.0D);
        }
    }

    public static void registerBlockColors(final ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        colors.register(FishStatueBlock::getColor, StatueRegistry.TROPICAL_FISH_B.get(), StatueRegistry.TROPICAL_FISH_BB.get(), StatueRegistry.TROPICAL_FISH_BE.get(),
                StatueRegistry.TROPICAL_FISH_BM.get(), StatueRegistry.TROPICAL_FISH_BMB.get(), StatueRegistry.TROPICAL_FISH_BMS.get(),
                StatueRegistry.TROPICAL_FISH_E.get(), StatueRegistry.TROPICAL_FISH_ES.get(), StatueRegistry.TROPICAL_FISH_HB.get(),
                StatueRegistry.TROPICAL_FISH_SB.get(), StatueRegistry.TROPICAL_FISH_SD.get(), StatueRegistry.TROPICAL_FISH_SS.get());
    }

    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();

        for(CustomSpawnEggItem item : CustomSpawnEggItem.getEggs()) {
            colors.register((p_198141_1_, p_198141_2_) -> item.getColor(p_198141_2_), item);
        }
    }
}
