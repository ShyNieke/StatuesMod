package com.svennieke.statues.util;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Random;

public class ListHelper {
    public static final Random rand = new Random();

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
