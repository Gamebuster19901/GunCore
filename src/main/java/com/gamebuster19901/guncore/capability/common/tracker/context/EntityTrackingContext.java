/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.context;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityTrackingContext extends TrackingContext{

	private Entity tracker;
	
	public EntityTrackingContext(Entity tracker) {
		super();
		this.tracker = tracker;
	}
	
	public EntityTrackingContext(Entity tracker, Entity trackee) {
		super(trackee);
		this.tracker = tracker;
	}
	
	public EntityTrackingContext(Entity tracker, Vec3d destination) {
		this(tracker, tracker.world, destination);
	}
	
	public EntityTrackingContext(Entity tracker, World destinationWorld, Vec3d destination) {
		super(destinationWorld, destination);
		this.tracker = tracker;
	}
	
	@Override
	public Entity getTracker() {
		return tracker;
	}
	
	public World getTrackerWorld() {
		return tracker.getEntityWorld();
	}
	
	public boolean hasDestination() {
		return getDestinationWorld() != null && getDestination() != null;
	}
	
}
