package com.shynieke.statues.client;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.shynieke.statues.client.render.PlayerTileRenderer;
import com.shynieke.statues.init.StatueBlocks;
import com.shynieke.statues.init.StatueItems;
import com.shynieke.statues.init.StatueTiles;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.io.File;
import java.util.UUID;

public class ClientHandler {

    public static void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(StatueTiles.PLAYER, PlayerTileRenderer::new);

        RenderTypeLookup.setRenderLayer(StatueBlocks.campfire_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.drowned_statue, RenderType.getCutout());;
        RenderTypeLookup.setRenderLayer(StatueBlocks.husk_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.zombie_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.enderman_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.info_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.wasteland_statue, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(StatueBlocks.endermite_statue, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(StatueBlocks.slime_statue, RenderType.getTranslucent());

        Minecraft mc = Minecraft.getInstance();
        YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(mc.getProxy(), UUID.randomUUID().toString());
        MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
        GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
        PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(mc.gameDir, MinecraftServer.USER_CACHE_FILE.getName()));
        PlayerTile.setProfileCache(playerprofilecache);
        PlayerTile.setSessionService(minecraftsessionservice);
        PlayerProfileCache.setOnlineMode(false);

        ItemModelsProperties.func_239418_a_(StatueItems.player_compass, new ResourceLocation("angle"), new IItemPropertyGetter() {
            double rotation;
            double rota;
            long lastUpdateTick;

            public float call(ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
                if (entityIn == null && !stack.isOnItemFrame()) {
                    return 0.0F;
                } else {
                    boolean flag = entityIn != null;
                    Entity entity = (Entity) (flag ? entityIn : stack.getItemFrame());

                    if (worldIn == null) {
                        worldIn = (ClientWorld) entity.world;
                    }

                    double d1 = flag ? (double) entity.rotationYaw : this.getFrameRotation((ItemFrameEntity) entity);
                    d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                    double d2 = this.getLastLocationToAngle(worldIn, entity, stack) / (Math.PI * 2D);
                    double d0 = 0.5D - (d1 - 0.25D - d2);

                    if (flag) {
                        d0 = this.wobble(worldIn, d0);
                    }

                    return MathHelper.positiveModulo((float) d0, 1.0F);
                }
            }

            private double wobble(ClientWorld worldIn, double p_185093_2_) {
                if (worldIn.getGameTime() != this.lastUpdateTick) {
                    this.lastUpdateTick = worldIn.getGameTime();
                    double d0 = p_185093_2_ - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }

            private double getFrameRotation(ItemFrameEntity itemFrameIn) {
                return (double) MathHelper.wrapDegrees(180 + itemFrameIn.getHorizontalFacing().getHorizontalIndex() * 90);
            }

            private double getLastLocationToAngle(ClientWorld worldIn, Entity entityIn, ItemStack stack) {
                if (stack.hasTag()) {
                    BlockPos lastLocation = worldIn.func_239140_u_();
                    CompoundNBT tag = stack.getTag();
                    if (tag.contains("lastPlayerLocation")) {
                        long location = tag.getLong("lastPlayerLocation");
                        if (location != 0L) {
                            lastLocation = BlockPos.fromLong(location);
                        }

                    }
                    return Math.atan2((double) lastLocation.getZ() - entityIn.getPosZ(), (double) lastLocation.getX() - entityIn.getPosZ());
                } else {
                    return getSpawnToAngle(worldIn, entityIn);
                }
            }

            private double getSpawnToAngle(ClientWorld worldIn, Entity entityIn) {
                BlockPos spawnPos = worldIn.func_239140_u_();
                return Math.atan2((double) spawnPos.getZ() - entityIn.getPosZ(), (double) spawnPos.getX() - entityIn.getPosX());
            }
        });

        ItemModelsProperties.func_239418_a_(Item.getItemFromBlock(StatueBlocks.bee_statue), new ResourceLocation("broken"), (p_239423_0_, p_239423_1_, p_239423_2_) -> {
            return ElytraItem.isUsable(p_239423_0_) ? 0.0F : 1.0F;
        });
    }
}
