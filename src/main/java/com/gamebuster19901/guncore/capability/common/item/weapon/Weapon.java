/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.weapon;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.INBTSerializable;

public interface Weapon extends Updateable, INBTSerializable<CompoundNBT>{
	double FPSToMPS = 0.3048d;
	double TPS = 20;
	
	/**
	 * @return if this weapon can be used.
	 */
	public boolean canFire(Entity shooter);
	
	/**
	 * Attempt to fire this weapon. Checks if the weapon can fire, and then fires if it can.
	 * 
	 * If automatic and can fire more than once per tick, fires as many times as possible per tick.
	 * 
	 * @return true if a shot was fired, false otherwise
	 */
	public default boolean attemptFire(Entity shooter) {
		boolean shot = false;
		byte shotsLeft = shotsPerTick();
		if(!isAutomatic()) {
			shotsLeft = 1;
		}
		while(canFire(shooter) && shotsLeft > 0) {
			fire(shooter);
			shot = true;
			shotsLeft--;
		}
		return shot;
	}
	
	/**
	 * Fires the weapon.
	 */
	public void fire(Entity shooter);
	
	/**
	 * How many shots this weapon can shoot per second
	 * 
	 * Any values returned over 2540 are effectively treated as 2540.
	 * 
	 * @return how many shots this weapon can fire per second
	 */
	public int getFireRate();
	
	public void setFireRate(int rate);
	
	/**
	 * @return the time in ticks until the weapon can fire if its rate of fire is >= 1 per tick, max 6.35 seconds
	 */
	public byte getTimeUntilNextFire();
	
	public void setTimeUntilNextFire(byte time);
	
	/**
	 * @return If this is an automatic weapon or not. I.E. will keep firing when the fire button is held.
	 */
	public boolean isAutomatic();
	
	public void setAutomatic(boolean isAutomatic);
	
	/**
	 * @return The maximum amount of bullets this gun can shoot per tick
	 * 
	 * Max of 127 to prevent lag
	 */
	public default byte shotsPerTick() {
		if(getFireRate() < 20) {
			return (byte) MathHelper.clamp(Math.floor(getFireRate() / TPS), 1, 127);
		}
		return 1;
	}
	
	public default boolean canMelee() {
		return true;
	}
	
	public void setMeleeDamage(float meleeDamage);
	
	public float getMeleeDamage();
	
	/**
	 * Convert Feet per second to meters per tick
	 * @param fps feet per second
	 * @return meters per tick
	 */
	public static double FPSToMPT(double fps) {
		return fps * FPSToMPS / TPS;
	}

}
