/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import com.gamebuster19901.guncore.capability.common.tracker.context.TrackingContext;
import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface Tracker extends Updateable, INBTSerializable<CompoundNBT>{
	
	public TrackingContext getTrackingContext();
	
	public default boolean isTracking() {
		return getTrackingContext().hasDestination();
	}
	
	/**
	 * @return true if the tracker is within the range of its destination
	 */
	public default boolean destinationReached() {
		if(isTracking()) {
			return getPosition().distanceTo(getTrackingContext().getDestination()) <= getDestinationRange();
		}
		return false;
	}
	
	public default boolean withinTrackingRange(World world, Vec3d vec) {
		return getWorld() == world && getPosition().distanceTo(vec) <= getTrackingRange();
	}
	
	public void setTrackingRange(double range);
	
	public double getTrackingRange();
	
	public void setDestinationRange(double range);
	
	public double getDestinationRange();
	
	/**
	 * Returns the world that this tracker is in
	 */
	public World getWorld();
	
	/**
	 * @return the location of this tracker
	 */
	public Vec3d getPosition();
	
	public void track(TrackingContext trackingContext);
	
	public default boolean canTrack(TrackingContext trackingContext) {
		return trackingContext != null && 
		trackingContext.hasDestination() &&
		trackingContext.getDestinationWorld() == getWorld() &&
		withinTrackingRange(trackingContext.getDestinationWorld(), trackingContext.getDestination());
	}
	
}
