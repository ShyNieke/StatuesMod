package com.shynieke.statues.client;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.shynieke.statues.client.render.PlayerTileRenderer;
import com.shynieke.statues.init.StatueBlocks;
import com.shynieke.statues.init.StatueItems;
import com.shynieke.statues.init.StatueTiles;
import com.shynieke.statues.items.StatueTransBeeItem;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
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
            private final ClientHandler.Angle rotation = new ClientHandler.Angle();
            private final ClientHandler.Angle rota = new ClientHandler.Angle();

            public float call(ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
                Entity entity = (Entity)(entityIn != null ? entityIn : stack.getAttachedEntity());
                if (entity == null) {
                    return 0.0F;
                } else {
                    if (entity != null && entity.world instanceof ClientWorld) {
                        worldIn = (ClientWorld)entity.world;
                    }
                    if(worldIn != null) {
                        BlockPos blockpos = this.getWorldPos(worldIn);
                        long gameTime = worldIn.getGameTime();

                        CompoundNBT tag = stack.getTag();
                        if (tag != null && tag.contains("lastPlayerLocation")) {
                            long location = tag.getLong("lastPlayerLocation");
                            if (location != 0L) { blockpos = BlockPos.fromLong(location); }
                        }

                        if (blockpos != null && !(entity.getPositionVec().squareDistanceTo((double)blockpos.getX() + 0.5D, entity.getPositionVec().getY(), (double)blockpos.getZ() + 0.5D) < (double)1.0E-5F)) {
                            boolean flag = entity instanceof PlayerEntity && ((PlayerEntity)entityIn).isUser();
                            double d1 = 0.0D;
                            if (flag) {
                                d1 = (double)entityIn.rotationYaw;
                            } else if (entity instanceof ItemFrameEntity) {
                                d1 = this.getFrameRotation((ItemFrameEntity)entity);
                            } else if (entity instanceof ItemEntity) {
                                d1 = (double)(180.0F - ((ItemEntity)entity).getItemHover(0.5F) / ((float)Math.PI * 2F) * 360.0F);
                            } else if (entityIn != null) {
                                d1 = (double)entityIn.renderYawOffset;
                            }

                            d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                            double d2 = this.getLocationToAngle(Vector3d.copyCentered(blockpos), entity) / (double)((float)Math.PI * 2F);
                            double d3;
                            if (flag) {
                                if (this.rotation.func_239448_a_(gameTime)) { this.rotation.func_239449_a_(gameTime, 0.5D - (d1 - 0.25D)); }
                                d3 = d2 + this.rotation.field_239445_a_;
                            } else {
                                d3 = 0.5D - (d1 - 0.25D - d2);
                            }

                            return MathHelper.positiveModulo((float)d3, 1.0F);
                        } else {
                            if (this.rota.func_239448_a_(gameTime)) {
                                this.rota.func_239449_a_(gameTime, Math.random());
                            }

                            double d0 = this.rota.field_239445_a_ + (double)((float)stack.hashCode() / 2.14748365E9F);
                            return MathHelper.positiveModulo((float)d0, 1.0F);
                        }
                    }
                    double d0 = this.rota.field_239445_a_ + (double)((float)stack.hashCode() / 2.14748365E9F);
                    return MathHelper.positiveModulo((float)d0, 1.0F);
                }
            }

            @Nullable
            private BlockPos getWorldPos(ClientWorld world) {
                return world.func_230315_m_().func_236043_f_() ? world.func_239140_u_() : null;
            }

            private double getFrameRotation(ItemFrameEntity itemFrameIn) {
                Direction direction = itemFrameIn.getHorizontalFacing();
                int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getOffset() : 0;
                return (double)MathHelper.wrapDegrees(180 + direction.getHorizontalIndex() * 90 + itemFrameIn.getRotation() * 45 + i);
            }

            private double getLocationToAngle(Vector3d location, Entity entityIn) {
                return Math.atan2(location.getZ() - entityIn.getPosZ(), location.getX() - entityIn.getPosX());
            }
        });

        ItemModelsProperties.func_239418_a_(Item.getItemFromBlock(StatueBlocks.bee_statue), new ResourceLocation("trans"), (stack, worldIn, entityIn) -> StatueTransBeeItem.isTrans(stack) ? 1.0F : 0.0F);
    }

    @OnlyIn(Dist.CLIENT)
    static class Angle {
        private double field_239445_a_;
        private double field_239446_b_;
        private long field_239447_c_;

        private Angle() {
        }

        private boolean func_239448_a_(long p_239448_1_) {
            return this.field_239447_c_ != p_239448_1_;
        }

        private void func_239449_a_(long p_239449_1_, double p_239449_3_) {
            this.field_239447_c_ = p_239449_1_;
            double d0 = p_239449_3_ - this.field_239445_a_;
            d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
            this.field_239446_b_ += d0 * 0.1D;
            this.field_239446_b_ *= 0.8D;
            this.field_239445_a_ = MathHelper.positiveModulo(this.field_239445_a_ + this.field_239446_b_, 1.0D);
        }
    }
}
