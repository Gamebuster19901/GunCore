/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import com.gamebuster19901.guncore.capability.common.tracker.context.TrackingContext;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class TrackerDefaultImpl extends TrackerBaseImpl{
	
	@CapabilityInject(Tracker.class)
	public static Capability<Tracker> CAPABILITY;
	
	public TrackerBaseImpl impl;
	
	public TrackerDefaultImpl() {
		super(null, 0, 0);
	}
	
	public void setImpl(TrackerBaseImpl impl) {
		if(impl instanceof TrackerDefaultImpl) {
			throw new IllegalArgumentException();
		}
		this.impl = impl;
	}

	@Override
	public TrackingContext getTrackingContext() {
		return impl.getTrackingContext();
	}
	
	@Override
	public boolean isTracking() {
		return impl.isTracking();
	}
	
	@Override
	public boolean destinationReached() {
		return impl.destinationReached();
	}
	
	@Override
	public boolean withinTrackingRange(World world, Vec3d vec) {
		return impl.withinTrackingRange(world, vec);
	}
	
	@Override
	public void setTrackingRange(double range) {
		impl.setTrackingRange(range);
	}
	
	@Override
	public double getTrackingRange() {
		return impl.getTrackingRange();
	}
	
	@Override
	public void setDestinationRange(double range) {
		impl.setDestinationRange(range);
	}
	
	@Override
	public double getDestinationRange() {
		return impl.getDestinationRange();
	}
	
	@Override
	public World getWorld() {
		return impl.getWorld();
	}
	
	@Override
	public Vec3d getPosition() {
		return impl.getPosition();
	}
	
	@Override
	public void track(TrackingContext trackingContext) {
		impl.track(trackingContext);
	}
	
	@Override
	public boolean canTrack(TrackingContext trackingContext) {
		return impl.canTrack(trackingContext);
	}

	@Override
	public void update(Object... data) {
		impl.update(data);
	}

}
