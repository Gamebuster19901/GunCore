/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import com.gamebuster19901.guncore.capability.common.tracker.context.TrackingContext;

import net.minecraft.nbt.CompoundNBT;

public abstract class TrackerBaseImpl implements Tracker{
	protected TrackingContext trackingContext;
	protected double trackingRange;
	protected double destinationRange;
	
	public TrackerBaseImpl(TrackingContext context, double trackingRange, double destinationRange) {
		if(destinationRange < trackingRange) {
			throw new IndexOutOfBoundsException(destinationRange + " < " + trackingRange);
		}
		this.trackingContext = context;
		this.trackingRange = trackingRange;
		this.destinationRange = destinationRange;
	}
	
	@Override
	public void setTrackingRange(double range) {
		trackingRange = range;
	}

	@Override
	public double getTrackingRange() {
		return trackingRange;
	}

	@Override
	public void setDestinationRange(double range) {
		destinationRange = range;
	}

	@Override
	public double getDestinationRange() {
		return destinationRange;
	}
	
	public TrackingContext getTrackingContext() {
		return trackingContext;
	}
	
	@Override
	public void track(TrackingContext trackingContext) {
		if(canTrack(trackingContext)) {
			this.trackingContext = trackingContext;
		}
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.putDouble("trackingRange", getTrackingRange());
		nbt.putDouble("destinationRange", getDestinationRange());
		nbt.put("trackingContext", getTrackingContext().serializeNBT());
		
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setTrackingRange(nbt.getDouble("trackingRange"));
		setDestinationRange(nbt.getDouble("getDestinationRange"));
	}
}
