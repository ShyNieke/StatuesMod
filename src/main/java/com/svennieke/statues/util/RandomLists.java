package com.svennieke.statues.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.svennieke.statues.init.StatuesSounds;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;

import java.util.List;
import java.util.Random;

public class RandomLists {

public static final Random rand = new Random();
	
	public static List<PotionType> throwablePotions = ImmutableList.of(
			PotionTypes.HARMING,
			PotionTypes.SLOWNESS,
			PotionTypes.POISON,
			PotionTypes.WEAKNESS,
			PotionTypes.STRENGTH,
			PotionTypes.HEALING,
			PotionTypes.REGENERATION
			);
	
	public static List<SoundEvent> wasteland_sounds = ImmutableList.of(
			StatuesSounds.wasteland_hello,
			StatuesSounds.wasteland_onwards
			);
	
	public static PotionType getRandomPotionType()
	{
		PotionType type = getRandomFromList(throwablePotions);
		return type;
	}
	
	public static SoundEvent GetRandomWasteland()
	{
		SoundEvent type = getRandomFromList(wasteland_sounds);
		return type;
	}
	
	public static <T> T getRandomFromList(List<T> list) {
        return getRandomFromList(list, rand);
    }

    public static <T> T getRandomFromList(List<T> list, Random rand) {
        final int size = list.size();
        Preconditions.checkArgument(size > 0, "Can't select from empty list");
        if (size == 0) return null;
        if (size == 1) return list.get(0);
        int randomIndex = rand.nextInt(list.size());
        return list.get(randomIndex);
    }
}
