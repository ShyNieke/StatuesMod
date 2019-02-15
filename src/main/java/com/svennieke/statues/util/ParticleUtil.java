package com.svennieke.statues.util;

import net.minecraft.init.Particles;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleUtil {
	public static void emitExplosionParticles(World worldIn, BlockPos pos) {
		double d0 = (double)((float)pos.getX() + worldIn.rand.nextFloat());
        double d1 = (double)((float)pos.getY() + worldIn.rand.nextFloat());
        double d2 = (double)((float)pos.getZ() + worldIn.rand.nextFloat());
        double d3 = d0 - pos.getX();
        double d4 = d1 - pos.getY();
        double d5 = d2 - pos.getZ();
        double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        d3 = d3 / d6;
        d4 = d4 / d6;
        d5 = d5 / d6;
        double d7 = 0.5D / (d6 / (double)4.0F + 0.1D);
        d7 = d7 * (double)(worldIn.rand.nextFloat() * worldIn.rand.nextFloat() + 0.3F);
        d3 = d3 * d7;
        d4 = d4 * d7;
        d5 = d5 * d7;
        worldIn.spawnParticle(Particles.POOF, (d0 + pos.getX()) / 2.0D, (d1 + pos.getY()) / 2.0D, (d2 + pos.getZ()) / 2.0D, d3, d4, d5);
        worldIn.spawnParticle(Particles.SMOKE, d0, d1, d2, d3, d4, d5);
	}
}
