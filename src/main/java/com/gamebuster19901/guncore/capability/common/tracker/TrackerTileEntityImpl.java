/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import com.gamebuster19901.guncore.capability.common.tracker.context.TileEntityTrackingContext;
import com.gamebuster19901.guncore.capability.common.tracker.context.TrackingContext;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TrackerTileEntityImpl extends TrackerBaseImpl{

	public TrackerTileEntityImpl(TileEntity tracker, double trackingRange, double destinationRange) {
		super(new TileEntityTrackingContext(tracker), trackingRange, destinationRange);
	}

	@Override
	public TileEntityTrackingContext getTrackingContext() {
		return (TileEntityTrackingContext) super.getTrackingContext();
	}

	@Override
	public World getWorld() {
		return getTrackingContext().getTrackerWorld();
	}

	@Override
	public Vec3d getPosition() {
		return new Vec3d(getTrackingContext().getTracker().getPos());
	}
	
	@Override
	public boolean canTrack(TrackingContext trackingContext) {
		if(trackingContext instanceof TileEntityTrackingContext) {
			return super.canTrack(trackingContext);
		}
		return false;
	}

	@Override
	public void update(Object... data) {
		// TODO Auto-generated method stub
		
	}

}
