/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.context;

import javax.annotation.Nullable;

import com.gamebuster19901.guncore.Main;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class TrackingContext implements INBTSerializable<CompoundNBT>{

	protected Entity trackee;
	
	private World destinationWorld;
	private Vec3d destination;
	
	public TrackingContext() {
		
	}
	
	public TrackingContext(Entity trackee) {
		track(trackee);
	}
	
	public TrackingContext(World destinationWorld, Vec3d destination) {
		track(destinationWorld, destination);
	}
	
	public void track(World world, Vec3d position) {
		trackee = null;
		destinationWorld = world;
		destination = position;
	}
	
	public void track(Entity e) {
		trackee = e;
		destinationWorld = null;
		destination = null;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.put("trackerDim", new IntNBT(getTrackerWorld().getDimension().getType().getId()));
		
		if(trackee != null) {
			nbt.put("destDim", new IntNBT(trackee.getEntityWorld().getDimension().getType().getId()));
			nbt.put("trackee", new IntNBT(trackee.getEntityId()));
		}
		else {
			nbt.put("destDim", new IntNBT(destinationWorld.getDimension().getType().getId()));
			nbt.put("destX", new DoubleNBT(destination.x));
			nbt.put("destY", new DoubleNBT(destination.y));
			nbt.put("destZ", new DoubleNBT(destination.z));
		}
		
		return nbt;
	}

	@Override
	@SuppressWarnings("resource")
	public void deserializeNBT(CompoundNBT nbt) {
		World world = getTrackerWorld();
		int dim = nbt.getInt("destDim");
		
		if(nbt.contains("trackee")) {
			int trackee = nbt.getInt("trackee");
			if(world.isRemote) {
				if(world.getDimension().getType().getId() == dim) {
					this.trackee = world.getEntityByID(trackee);
					if(this.trackee == null) {
						Main.LOGGER.warn("Could not find client entity with id " + trackee);
					}
				}
			}
			else {
				World trackeeWorld = world.getServer().getWorld(DimensionManager.getRegistry().getByValue(dim));
				this.trackee = trackeeWorld.getEntityByID(trackee);
				if(this.trackee == null) {
					Main.LOGGER.warn("Could not find server entity with id " + trackee + " in " + trackeeWorld.getProviderName() + " [" + dim + "]");
				}
			}
		}
		else {
			this.destinationWorld = world.getServer().getWorld(DimensionManager.getRegistry().getByValue(dim));
			this.destination = new Vec3d(nbt.getDouble("destX"), nbt.getDouble("destY"), nbt.getDouble("destZ"));
		}
	}
	
	public abstract ICapabilityProvider getTracker();
	
	public abstract World getTrackerWorld();
	
	public boolean hasTrackee() {
		return trackee != null;
	}
	
	public Entity getTrackee() {
		return trackee;
	}
	
	public boolean hasDestination() {
		return getDestinationWorld() != null && getDestination() != null;
	}
	
	@Nullable
	public Vec3d getDestination() {
		if(trackee != null) {
			return trackee.getPositionVec();
		}
		return destination;
	}
	
	@Nullable
	public World getDestinationWorld() {
		if(trackee != null) {
			return trackee.getEntityWorld();
		}
		return destinationWorld;
	}
	
}
