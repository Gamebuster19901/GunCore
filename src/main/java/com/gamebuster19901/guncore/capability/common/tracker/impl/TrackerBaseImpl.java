/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
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
	
	public static final String UUID = "uuid";
	public static final String ID = "id";
	public static final String DEST_W = "destW";
	public static final String DEST_X = "destX";
	public static final String DEST_Y = "destY";
	public static final String DEST_Z = "destZ";
	public static final String DEST_RANGE = "destRange";
	public static final String ACT_RANGE = "actRange";
	
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
			nbt.putUniqueId(UUID, trackee.getUniqueID());
			nbt.putInt(ID, trackee.getEntityId());
		}
		else {
			if(world != null) {
				nbt.putInt(DEST_W, world.getDimension().getType().getId());
			}
			if(dest != null) {
				nbt.putDouble(DEST_X, dest.getX());
				nbt.putDouble(DEST_Y, dest.getY());
				nbt.putDouble(DEST_Z, dest.getZ());
			}
		}
		
		nbt.putDouble(DEST_RANGE, destinationRange);
		nbt.putDouble(ACT_RANGE, activationRange);
		
		return nbt;
	}

	@Override
	@SuppressWarnings({ "deprecation" })
	public void deserializeNBT(CompoundNBT nbt) {
		World world = getWorld();
		if(nbt.contains(ID)) {
			UUID uuid = nbt.getUniqueId(UUID);
			if(!world.isRemote) {
				Entity trackee = ((ServerWorld)getWorld()).getEntityByUuid(uuid);
				if(trackee != null) {
					track(trackee);
				}
			}
			else {
				Entity trackee = world.getEntityByID(nbt.getInt(ID));
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
				ServerWorld destWorld = DimensionManager.getWorld(world.getServer(), DimensionManager.getRegistry().getByValue(nbt.getInt(DEST_W)), false, false);
				track(destWorld, new Vec3d(nbt.getDouble(DEST_X), nbt.getDouble(DEST_Y), nbt.getDouble(DEST_Z)));
			}
			else {
				if(world.getDimension().getType().getId() == nbt.getInt(DEST_W)) {
					track(world, new Vec3d(nbt.getDouble(DEST_X), nbt.getDouble(DEST_Y), nbt.getDouble(DEST_Z)));
				}
			}
		}
		
		setRange(nbt.getDouble(DEST_RANGE));
		setActivationRange(nbt.getDouble(ACT_RANGE));
		
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
