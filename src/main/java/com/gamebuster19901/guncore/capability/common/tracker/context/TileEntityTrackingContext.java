/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.context;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TileEntityTrackingContext extends TrackingContext{

	private TileEntity tracker;
	
	public TileEntityTrackingContext(TileEntity tracker) {
		super();
		this.tracker = tracker;
	}
	
	public TileEntityTrackingContext(TileEntity tracker, Entity trackee) {
		super(trackee);
		this.tracker = tracker;
	}
	
	public TileEntityTrackingContext(TileEntity tracker, Vec3d destination) {
		this(tracker, tracker.getWorld(), destination);
	}
	
	public TileEntityTrackingContext(TileEntity tracker, World destinationWorld, Vec3d destination) {
		super(destinationWorld, destination);
		this.tracker = tracker;
	}
	
	@Override
	public TileEntity getTracker() {
		return tracker;
	}
	
	@Override
	public World getTrackerWorld() {
		return tracker.getWorld();
	}

}
