/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.impl;

import java.util.UUID;

import com.gamebuster19901.guncore.capability.common.tracker.Tracker;
import com.gamebuster19901.guncore.exception.NoImplementationProvidedException;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TrackerNoImpl implements Tracker{

	@Override
	public CompoundNBT serializeNBT() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		throw new NoImplementationProvidedException();
	}

	@Override
	public void track(Entity e) {
		throw new NoImplementationProvidedException();
	}

	@Override
	public void track(World world, Vec3d vec) {
		throw new NoImplementationProvidedException();
	}

	@Override
	public boolean isTracking() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public Entity getTrackee() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public Vec3d getDestination() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public void setRange(double range) {
		throw new NoImplementationProvidedException();
	}

	@Override
	public double getRange() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public void setActivationRange(double range) {
		throw new NoImplementationProvidedException();
	}

	@Override
	public double getActivationRange() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public UUID getTrackeeUUID() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public World getWorld() {
		throw new NoImplementationProvidedException();
	}

	@Override
	public void onTick(Object... data) {
		throw new NoImplementationProvidedException();
	}
	
	@Override
	public void update(Object... data) {
		throw new NoImplementationProvidedException();
	}

}
