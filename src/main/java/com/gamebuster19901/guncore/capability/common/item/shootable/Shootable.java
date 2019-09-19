/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.shootable;

import java.util.Random;

import com.gamebuster19901.guncore.common.item.abstracts.Projectile;
import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface Shootable extends Updateable, INBTSerializable<CompoundNBT>{
	
	public void setBloom(float bloom);
	
	public void addBloom(float bloom);
	
	/**
	 * @return Return the amount of bloom that this shootable has. Generally a shootable becomes less accurate if spammed
	 */
	public float getBloom();
	
	/**
	 * @return the maximum amount of inaccuracy that this shootable will have if it shoots this tick.
	 */
	public float getMaxBloom();
	
	public void setMaxBloom(float maxBloom);
	
	/**
	 * 
	 * @return the amount the bloom increases per shot
	 */
	public float getBloomIncreasePerShot();
	
	public void setBloomIncreasePerShot(float bloomIncrease);
	
	/**
	 * @return the amount bloom decreases per tick
	 */
	public float getBloomDecreasePerTick();
	
	public void setBloomDecreasePerTick(float bloomDecrease);
	
	/**
	 * @return the initial velocity of the projectile that spawns
	 */
	public float getMuzzleVelocity();
	
	public void setMuzzleVelocity(float velocity);
	
	/**
	 * 
	 * @return the amount of vertical recoil the shot will have, negative values are down, positive values are up
	 */
	public default float getVerticalRecoil() {
		return getFloatBetween(getMinVerticalRecoil(), getMaxVerticalRecoil());
	}
	
	/**
	 * @return the minimum amount of vertical recoil this shootable can have. Negative values make the shootable
	 * recoil down, positive values make the shootable recoil up.
	 */
	public float getMinVerticalRecoil();
	
	public void setMinVerticalRecoil(float minVertical);
	
	/**
	 * @return the maximum amount of vertical recoil this shootable can have. Negative values make the shootable
	 * recoil down, positive values make the shootable recoil up.
	 */
	public float getMaxVerticalRecoil();
	
	public void setMaxVerticalRecoil(float maxVertical);
	
	/**
	 * @return the amount of horizontal recoil the shot will have, negative values are left, positive values are right
	 */
	public default float getHorizontalRecoil() {
		return getFloatBetween(getMinHorizontalRecoil(), getMaxHorizontalRecoil());
	}
	
	/**
	 * @return the minimum amount of horizontal recoil this shootable can have. Negative values make the shootable
	 * recoil left, positive values make the shootable recoil right.
	 */
	public float getMinHorizontalRecoil();
	
	public void setMinHorizontalRecoil(float minHorizontal);
	
	/**
	 * @return the maximum amount of horizontal recoil this shootable can have. Negative values make the shootable
	 * recoil left, positive values make the shootable recoil right.
	 */
	public float getMaxHorizontalRecoil();
	
	public void setMaxHorizontalRecoil(float maxHorizontal);
	
	/**
	 * Changes the projectile of this shootable
	 * @param projectile
	 */
	public void setProjectile(CompoundNBT projectile);
	
	/**
	 * Get the nbt that represents the projectile.
	 * @param world the world to instantiate the projectile in
	 * 
	 * @return the nbt of the projectile
	 */
	public CompoundNBT getProjectile();
	
	public Random getRandom();
	
	public void setRandom(Random random);
	
	/**
	 * @param min the minimum value to generate
	 * @param max the maximum value to generate
	 * @return a random float between the minimum and the maximum values provided
	 */
	public default float getFloatBetween(float min, float max) {
		return getRandom().nextFloat() * (max - min) + min;
	}
	
	/**
	 * Spawns the projectile into the world and shoots it
	 */
	public default void shoot(Entity shooter) {
		new Projectile(getProjectile()).shoot(shooter, this);
	}
	
	/**
	 * Spawns the projectile into the world and shoots it
	 */
	public default void shoot(World world, Vec3d pos, Vec3d vector) {
		new Projectile(getProjectile()).shoot(world, pos, vector, this);
	}
	
	public float getDamage();
	
	public void setDamage(float damage);
	
}
