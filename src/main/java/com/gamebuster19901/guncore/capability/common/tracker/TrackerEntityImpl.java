/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import com.gamebuster19901.guncore.capability.common.tracker.context.EntityTrackingContext;
import com.gamebuster19901.guncore.capability.common.tracker.context.TrackingContext;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TrackerEntityImpl extends TrackerBaseImpl{
	
	public TrackerEntityImpl(Entity tracker, double trackingRange, double destinationRange) {
		super(new EntityTrackingContext(tracker), trackingRange, destinationRange);
	}
	
	@Override
	public EntityTrackingContext getTrackingContext() {
		return (EntityTrackingContext) super.getTrackingContext();
	}
	
	@Override
	public World getWorld() {
		return trackingContext.getTrackerWorld();
	}
	
	@Override
	public Vec3d getPosition() {
		return getTrackingContext().getTracker().getPositionVec();
	}
	
	@Override
	public boolean canTrack(TrackingContext trackingContext) {
		if(trackingContext instanceof EntityTrackingContext) {
			return super.canTrack(trackingContext);
		}
		return false;
	}
	
	@Override
	public void update(Object... data) {
		
	}

}
