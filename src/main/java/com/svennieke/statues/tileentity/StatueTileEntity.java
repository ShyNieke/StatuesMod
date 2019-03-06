package com.svennieke.statues.tileentity;

import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.config.StatuesConfig;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.init.StatuesSounds;
import com.svennieke.statues.init.StatuesTileTypes;
import com.svennieke.statues.items.ItemMarshmallow;
import com.svennieke.statues.items.ItemTea;
import com.svennieke.statues.util.RandomLists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class StatueTileEntity extends TileEntity implements ITickable, iStatueBehaviors{
	public int cooldown;
	public int cooldownMax;
	public boolean statueAble;
	private int tier;
	
	public StatueTileEntity() {
		super(StatuesTileTypes.STATUE);
		this.tier = 2;
		this.cooldown = 0;
		this.cooldownMax = (StatuesConfig.COMMON.interactionTimer.get() * 20);
		this.statueAble = false;
	}

	public StatueTileEntity(int tier) {
		super(StatuesTileTypes.STATUE);
		this.tier = tier;
		this.cooldown = 0;
		this.cooldownMax = (StatuesConfig.COMMON.interactionTimer.get() * 20);
		this.statueAble = false;
	}

	public int setTier(int theTier) {
		return this.tier = theTier;
	}
	
	public int getTier() {
		return this.tier;
	}

	public void CampfireBehavior(World worldIn, BlockPos pos, EntityPlayer playerIn, @Nullable ItemStack stack1, @Nullable ItemStack stack2, @Nullable ItemStack stack3)
	{	
		if(tier > 1)
		{
			if(isStatueAble()) 
			{
				int random = world.rand.nextInt(100);
				if(tier >= 3)
				{
					if (random < 100 && stack1 != null && stack1 != ItemStack.EMPTY)
					{
						if(stack1.getItem() instanceof ItemMarshmallow && (tier == 3 || tier == 4))
						{
							worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.NEUTRAL, 1F, 1F); //TODO: REMOVE PLACEHOLDER AND ACTUALLY IMPLEMENT PROPER STUFF
						}
						playerIn.dropItem(stack1, true);
					}
					
					if(stack2 != null && stack2 != ItemStack.EMPTY){
						if(random < 50)
						{
							playerIn.dropItem(stack2, true);
						}
					}
					
					if(stack3 != null && stack3 != ItemStack.EMPTY){
						if(random < 10)
						{
							playerIn.dropItem(stack3, true);
						}
					}
					setStatueAble(false);
				}
			}
			else
			{
				if((tier >= 2 && tier != 5))
				{
					worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1F, 1F); //TODO: REMOVE PLACEHOLDER AND ACTUALLY IMPLEMENT PROPER STUFF
				}
			}
		}
	}
	
	public void FakeMobs(Entity entity, World worldIn, BlockPos pos, boolean isChild) {
		if(tier == 3)
		{
			int random = world.rand.nextInt(100);

			if (random < 1)
			{
				if(isChild == true && entity instanceof EntityMob)
				{
					EntityMob mob = (EntityMob)entity;
					mob.isChild();
					mob.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
					worldIn.spawnEntity(mob);
				}
				else
				{
					entity.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
					worldIn.spawnEntity(entity);
				}
			}
		}
	}
	
	public void WastelandBehavior(World worldIn, BlockPos pos, EntityPlayer playerIn, @Nullable ItemStack stack1, @Nullable ItemStack stack2, @Nullable ItemStack stack3)
	{		
		if(isStatueAble()) 
		{
			int random = world.rand.nextInt(100);
			if(tier >= 3)
			{
				if (random < 100 && stack1 != null && stack1 != ItemStack.EMPTY)
				{
					if(stack1.getItem() instanceof ItemTea && (tier == 3 || tier == 4))
					{
						worldIn.playSound(null, pos, StatuesSounds.wasteland_tea, SoundCategory.NEUTRAL, 1F, 1F);
					}
					playerIn.dropItem(stack1, true);
				}
				
				if(stack2 != null && stack2 != ItemStack.EMPTY){
					if(random < 50)
					{
						playerIn.dropItem(stack2, true);
					}
				}
				
				if(stack3 != null && stack3 != ItemStack.EMPTY){
					if(random < 10)
					{
						playerIn.dropItem(stack3, true);
					}
				}
				setStatueAble(false);
			}
		}
		else
		{
			if((tier >= 2 && tier != 5))
			{
				worldIn.playSound(null, pos, RandomLists.GetRandomWasteland(), SoundCategory.NEUTRAL, 1F, 1F);
			}
		}
	}
	
	public void PlaySound(SoundEvent Mobsound, BlockPos pos, World worldIn) {
		if((tier >= 2 && tier != 5))
		{
			worldIn.playSound(null, pos, Mobsound, SoundCategory.NEUTRAL, 1F, 1F);
		}
	}
	
	public void SendInfoMessage(EntityPlayer entity, World worldIn, BlockPos pos) {
			EntityPlayer player = (EntityPlayer)entity;
			
			if (!world.isRemote) {
				int random = world.rand.nextInt(100);
				
				List<String> messages = StatuesConfig.COMMON.info_messages.get();
				List<String> LuckyPlayers = StatuesConfig.COMMON.lucky_players.get();
				
				int idx = new Random().nextInt(messages.size());
				String randommessage = (messages.get(idx));
				
				/*if(Statues.isVeinminerInstalled == true && random < 20)
				{
					randommessage = ("Did you know we have veinminer");
				}
				else */if(LuckyPlayers.size() != 0 && random < 20)
				{
					for (int i = 0; (i < LuckyPlayers.size()) && (LuckyPlayers.get(i) != null); i++) {
						randommessage = ("Luck is not on your side today");
					}
				}
				else
				{
					randommessage = (messages.get(idx));
				}
				randommessage = (messages.get(idx));

				player.sendMessage(new TextComponentTranslation(randommessage));
			}
	}
	
	public void GiveEffect(BlockPos pos, World worldIn, EntityPlayer entity, Potion effect) {
		if(isStatueAble())
		{
			EntityPlayer player = (EntityPlayer)entity;
			int random = world.rand.nextInt(100);
			if(tier >= 3)
			{
				if(random < 10)
				{
					if (!world.isRemote) {
						if (player.getActivePotionEffect(effect) == null) {
							player.addPotionEffect(new PotionEffect(effect, 20 * 20, 1, true, true));
						}
					}
				}
			}
		}
	}
	
	public void ThrowPotion(BlockPos pos, World worldIn, EntityPlayer entity) {
		if(isStatueAble())
		{			
			int random = world.rand.nextInt(100);
			if(tier >= 3)
			{
				if(random < 10)
				{
					if (!world.isRemote) {
						double d0 = entity.posY + (double)entity.getEyeHeight() - 1.100000023841858D;
			            double d1 = entity.posX + entity.motionX - pos.getX();
			            double d2 = d0 - pos.getY();
			            double d3 = entity.posZ + entity.motionZ - pos.getZ();
			            float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
			            
			            EntityPotion entitypotion = new EntityPotion(world);
			            entitypotion.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 0, 0);
			            entitypotion.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), RandomLists.getRandomPotionType()));
			            entitypotion.shoot(d1, d2 + (double)(f * 0.2F), d3, 0.25F, 6.0F);
			            this.world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
			            this.world.spawnEntity(entitypotion);
					}
				}
			}
		}
	}
	
	public void SpecialInteraction(boolean isCow, boolean isMooshroom, boolean isFlood, Block statue, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		EntityFireworkRocket firework = new EntityFireworkRocket(worldIn, (double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ), getFirework(world.rand));
		int random = world.rand.nextInt(100);
		
		if(tier >= 3)
		{
			if(isCow)
			{
				if(!worldIn.isRemote)
				{
					if (stack.getItem() == Items.BUCKET && !playerIn.abilities.isCreativeMode)
			        {
						worldIn.playSound(null, pos, SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1F, 1F);
			            stack.shrink(1);

			            if (stack.isEmpty())
			            {
			            	playerIn.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
			            }
			            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET)))
			            {
			            	playerIn.dropItem(new ItemStack(Items.MILK_BUCKET), false);
			            }
			        }
				}
			}
			
			if(isMooshroom)
			{
				if(!worldIn.isRemote)
				{
					if (stack.getItem() == Items.BOWL && !playerIn.abilities.isCreativeMode)
			        {
						//System.out.println("NO");
						worldIn.playSound(null, pos, SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1F, 1F);
			            stack.shrink(1);

			            if (stack.isEmpty())
			            {
			            	playerIn.setHeldItem(hand, new ItemStack(StatuesItems.soup));
			            }
			            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(StatuesItems.soup)))
			            {
			            	playerIn.dropItem(new ItemStack(StatuesItems.soup), false);
			            }
			        }
				}
			}
		
			if(isFlood)
			{
				if(!worldIn.isRemote)
					if (stack.getItem() == Items.BUCKET && !playerIn.abilities.isCreativeMode)
					{
						worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1F, 1F);
						stack.shrink(1);
						
						ItemStack floodbucket = StatueLootList.getFloodBucket();
								
						if (stack.isEmpty())
			            {
			                playerIn.setHeldItem(hand, floodbucket);
			            }
			            else if (!playerIn.inventory.addItemStackToInventory(floodbucket))
			            {
			            	playerIn.dropItem(floodbucket, false);
			            }
					}
				
					if (random < 50){
						 worldIn.spawnEntity(firework);
					}	
			}
		}
	}
    public static final int[] DYE_COLORS = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

	public ItemStack getFirework(Random rand) {
		ItemStack firework = new ItemStack(Items.FIREWORK_STAR);
		firework.setTag(new NBTTagCompound());
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("Flicker", true);
		nbt.setBoolean("Trail", true);

		int[] colors = new int[rand.nextInt(8) + 1];
		for (int i = 0; i < colors.length; i++) 
		{
			colors[i] = DYE_COLORS[rand.nextInt(16)];
		}
		nbt.setIntArray("Colors", colors);
		byte type = (byte) (rand.nextInt(3) + 1);
		type = type == 3 ? 4 : type;
		nbt.setByte("Type", type);

		NBTTagList explosions = new NBTTagList();
		explosions.add(nbt);

		NBTTagCompound fireworkTag = new NBTTagCompound();
		fireworkTag.setTag("Explosions", explosions);
		fireworkTag.setByte("Flight", (byte) 1);
		firework.getTag().setTag("Fireworks", fireworkTag); 

        return firework;
	}
	
	public void SpawnMob(Entity entity, World worldIn)
	{
		if(tier == 3)
		{
			int random = world.rand.nextInt(100);

			if (random < 1)
			{
				if(entity instanceof EntityRabbit)
				{
					EntityRabbit rabbit = new EntityRabbit(worldIn);
					rabbit.setRabbitType(99);
					rabbit.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
					
					worldIn.spawnEntity(rabbit);
				}
				else if(entity instanceof EntityCreeper)
				{
					EntityCreeper creeper = new EntityCreeper(worldIn);
					creeper.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
					NBTTagCompound tag = new NBTTagCompound();
					creeper.writeAdditional(tag);
			        
					tag.setShort("ExplosionRadius", (short)0);
			        tag.setShort("Fuse", (short)0);
			        
			        creeper.readAdditional(tag);
			        worldIn.spawnEntity(creeper);
			        creeper.spawnExplosionParticle();
				}
				else
				{
					entity.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
			        worldIn.spawnEntity(entity);
				}
			}
			setStatueAble(false);
		}
	}
	
	public void GiveItem(@Nullable ItemStack stack1, @Nullable ItemStack stack2, @Nullable ItemStack stack3, EntityPlayer playerIn) {
		
		if(isStatueAble()) 
		{
			int random = world.rand.nextInt(100);
			if(tier >= 3)
			{
				if (random < 100 && stack1 != null && stack1 != ItemStack.EMPTY)
				{
					playerIn.dropItem(stack1, true);
				}
				
				if(stack2 != null && stack2 != ItemStack.EMPTY){
					if(random < 50)
					{
						playerIn.dropItem(stack2, true);
					}
				}
				
				if(stack3 != null && stack3 != ItemStack.EMPTY){
					if(random < 10)
					{
						playerIn.dropItem(stack3, true);
					}
				}
				setStatueAble(false);
			}
		}
	}
	
    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        tier = compound.getInt("StatueTier");
        cooldown = compound.getInt("StatueCooldown");
        cooldownMax = compound.getInt("StatueMaxcooldown");
        statueAble = compound.getBoolean("StatueAble");
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setInt("StatueTier", tier);
        compound.setInt("StatueCooldown", cooldown);
        compound.setInt("StatueMaxcooldown", cooldownMax);
        compound.setBoolean("StatueAble", statueAble);
        return compound;
    }

    @Override
    public void tick(){
    	/*
    	if (!this.world.isRemote && Statues.instance.isWailaInstalled)
    	{
    		sendUpdatePacket();
    	}
    	*/
    	
    	if (!this.statueAble)
    	{
        	//System.out.println(this.cooldown);

    		++this.cooldown;
    		markDirty();
    		
    		if(this.cooldownMax == 0)
        		this.cooldownMax = (StatuesConfig.COMMON.interactionTimer.get() * 20);
    		
            if(this.cooldown >= this.cooldownMax){
            	
                this.cooldown = 0;
                setStatueAble(true);
            }
    	}
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	read(pkt.getNbtCompound());
    	
    	IBlockState state = world.getBlockState(getPos());
    	world.notifyBlockUpdate(getPos(), state, state, 3);
    }
        
    @Override
	public NBTTagCompound getUpdateTag()
    {
        return this.write(new NBTTagCompound());
    }
    
    @Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, this.getUpdateTag());
	}
    
    public int getCooldown() {
		return this.cooldown;
	}
    
    public int getCooldownMax() {
		return this.cooldownMax;
	}
    
    public boolean isStatueAble() {
		return this.statueAble;
	}
    
    public void setStatueAble(boolean statueAble) {
		this.statueAble = statueAble;
		this.markDirty();
	}
    
    /*
    @Method(modid = "waila")
    public void sendUpdatePacket()
    {
    	EntityPlayerMP player = (EntityPlayerMP) world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false);
		if(player != null && this.tier >= 3)
		{
			if(StatueTimerProvider.info == null)
		    	StatuesPacketHandler.INSTANCE.sendTo(new StatuesProgressMessage(getCooldown(), getCooldownMax(), isStatueAble(), this.pos), player);
			else
			{
				RayTraceResult result = player.rayTrace(5, 1F);
				int x = result.getBlockPos().getX();
				int y = result.getBlockPos().getY();
				int z = result.getBlockPos().getZ();
				BlockPos lookPos = new BlockPos(x,y,z);
				
				if(lookPos.equals(this.pos))
				{
					if(StatueTimerProvider.info.getPosition() != this.pos || StatueTimerProvider.info.isAble() != this.isStatueAble())
					{
						if(StatueTimerProvider.info.isAble() != isStatueAble())
					    	StatuesPacketHandler.INSTANCE.sendTo(new StatuesProgressMessage(getCooldown(), getCooldownMax(), isStatueAble(), this.pos), player);

						else if(StatueTimerProvider.info.getCooldown() != getCooldown())
					    	StatuesPacketHandler.INSTANCE.sendTo(new StatuesProgressMessage(getCooldown(), getCooldownMax(), isStatueAble(), this.pos), player);
					}
				}
			}
		}
    }
    */
}