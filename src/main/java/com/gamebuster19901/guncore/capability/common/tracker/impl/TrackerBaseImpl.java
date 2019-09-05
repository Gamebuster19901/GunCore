/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.impl;

import java.util.UUID;

import javax.annotation.Nullable;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.tracker.Tracker;
import com.gamebuster19901.guncore.common.util.Clearable;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public abstract class TrackerBaseImpl implements Tracker, Clearable{
	
	@CapabilityInject(Tracker.class)
	public static Capability<Tracker> CAPABILITY;
	
	private Entity trackee;
	private World world;
	private Vec3d dest;
	private double destinationRange;
	private double activationRange;
	
	@Override
	public void track(Entity trackee) {
		world = null;
		this.trackee = trackee;
	}

	@Override
	public void track(World world, Vec3d dest) {
		clear();
		this.world = world;
		this.dest = dest;
	}

	@Override
	public boolean isTracking() {
		return trackee != null || (world != null && dest != null);
	}

	@Override
	@Nullable
	public Entity getTrackee() {
		return trackee;
	}

	@Override
	public Vec3d getDestination() {
		if(trackee != null) {
			return trackee.getPositionVec();
		}
		return dest;
	}

	@Override
	public double getRange() {
		return destinationRange;
	}
	
	@Override
	public void setRange(double range) {
		this.destinationRange = range;
	}
	
	@Override
	public double getActivationRange() {
		return activationRange;
	}

	@Override
	public void setActivationRange(double range) {
		this.activationRange = range;
	}

	@Override
	@Nullable
	public UUID getTrackeeUUID() {
		if(trackee != null) {
			return trackee.getUniqueID();
		}
		return null;
	}
	

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		if(trackee != null) {
			nbt.putUniqueId("uuid", trackee.getUniqueID());
			nbt.putInt("id", trackee.getEntityId());
		}
		else {
			if(world != null) {
				nbt.putInt("destW", world.getDimension().getType().getId());
			}
			if(dest != null) {
				nbt.putDouble("destX", dest.getX());
				nbt.putDouble("destY", dest.getY());
				nbt.putDouble("destZ", dest.getZ());
			}
		}
		
		nbt.putDouble("destRange", destinationRange);
		nbt.putDouble("actRange", activationRange);
		
		return nbt;
	}

	@SuppressWarnings("resource")
	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		World world = getWorld();
		if(nbt.contains("id")) {
			UUID uuid = nbt.getUniqueId("uuid");
			if(!world.isRemote) {
				Entity trackee = ((ServerWorld)getWorld()).getEntityByUuid(uuid);
				if(trackee != null) {
					track(trackee);
				}
			}
			else {
				Entity trackee = world.getEntityByID(nbt.getInt("id"));
				if(trackee != null) {
					if(trackee.getUniqueID().equals(uuid)) {
						track(trackee);
					}
					else {
						Main.LOGGER.warn("UUID mismatch: " + trackee.getUniqueID() + " != " + uuid);
					}
				}
			}
		}
		else {
			if(!world.isRemote) {
				ServerWorld destWorld = DimensionManager.getWorld(world.getServer(), DimensionManager.getRegistry().getByValue(nbt.getInt("destW")), false, false);
				track(destWorld, new Vec3d(nbt.getDouble("destX"), nbt.getDouble("destY"), nbt.getDouble("destZ")));
			}
			else {
				if(world.getDimension().getType().getId() == nbt.getInt("destW")) {
					track(world, new Vec3d(nbt.getDouble("destX"), nbt.getDouble("destY"), nbt.getDouble("destZ")));
				}
			}
		}
		
		setRange(nbt.getDouble("destRange"));
		setActivationRange(nbt.getDouble("actRange"));
		
	}

	@Override
	public void clear() {
		trackee = null;
		world = null;
		dest = null;
	}
	
	@Override
	public void onTick(Object... data) {
		if(this.isTracking() && this.getTrackee() != null) {
			if(!this.getTrackee().isAlive()) {
				track(null);
			}
		}
	}
	
	@Override
	public void update(Object... data) {

	}

}
