/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.impl;

import static com.gamebuster19901.guncore.network.Network.CHANNEL;

import com.gamebuster19901.guncore.network.packet.server.UpdateTracker;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class TrackerEntityImpl extends TrackerBaseImpl{

	private Entity tracker;
	
	public TrackerEntityImpl(Entity tracker) {
		this.tracker = tracker;
	}

	@Override
	public World getWorld() {
		return tracker.getEntityWorld();
	}

	private Entity getTracker() {
		return tracker;
	}
	
	@Override
	public void update(Object... data) {
		if(tracker != null && !tracker.getEntityWorld().isRemote) {
			CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(this::getTracker), new UpdateTracker(this));
		}
	}

}
