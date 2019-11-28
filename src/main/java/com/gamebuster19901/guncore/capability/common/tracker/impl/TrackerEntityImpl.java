/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.impl;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class TrackerEntityImpl extends TrackerBaseImpl{

	private Entity tracker;
	
	public TrackerEntityImpl(Entity tracker) {
		this.tracker = tracker;
	}

	@Override
	public World getWorld() {
		return tracker.getEntityWorld();
	}

	public Entity getTracker() {
		return tracker;
	}

}
