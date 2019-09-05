/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import java.util.UUID;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface Tracker extends Updateable, INBTSerializable<CompoundNBT>{

	public void track(Entity e);
	
	public void track(World world, Vec3d vec);
	
	public boolean isTracking();
	
	public Entity getTrackee();
	
	public Vec3d getDestination();
	
	public void setRange(double range);
	
	public double getRange();
	
	public void setActivationRange(double range);
	
	public double getActivationRange();
	
	public UUID getTrackeeUUID();
	
	public World getWorld();
	
}
