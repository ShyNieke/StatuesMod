package com.shynieke.statues.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CustomSpawnEggItem extends SpawnEggItem {
    public final Supplier<EntityType<? extends Mob>> entityType;

    public CustomSpawnEggItem(final Supplier<EntityType<? extends Mob>> type, final int primary, final int secondary, final Properties properties) {
        super(null, primary, secondary, properties);
        entityType = type;
    }

    @Override
    public EntityType<?> getType(@Nullable final CompoundTag nbt) {
        if (nbt != null && nbt.contains("EntityTag", Constants.NBT.TAG_COMPOUND)) {
            final CompoundTag entityTag = nbt.getCompound("EntityTag");
            if (entityTag.contains("id", Constants.NBT.TAG_STRING)) {
                return EntityType.byString(entityTag.getString("id")).orElse(entityType.get());
            }
        }

        return entityType.get();
    }
}