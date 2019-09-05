/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.network.packet.server;

import java.util.function.Supplier;

import com.gamebuster19901.guncore.capability.common.tracker.Tracker;
import com.gamebuster19901.guncore.capability.common.tracker.impl.TrackerBaseImpl;
import com.gamebuster19901.guncore.exception.CapabilityMismatchError;
import com.gamebuster19901.guncore.network.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateTracker {
	private final Tracker tracker;
	private final PacketBuffer buf;
	
	public UpdateTracker(Tracker tracker) {
		this.tracker = tracker;
		this.buf = null;
	}
	
	public UpdateTracker(PacketBuffer buf) {
		tracker = null;
		this.buf = buf;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeCompoundTag(tracker.serializeNBT());
	}
	
	public void handle(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			CompoundNBT nbt = buf.readCompoundTag();
			Entity e = Minecraft.getInstance().world.getEntityByID(nbt.getInt("id"));
			Tracker tracker = e.getCapability(TrackerBaseImpl.CAPABILITY).orElseThrow(() -> new CapabilityMismatchError(TrackerBaseImpl.CAPABILITY, e));
			tracker.deserializeNBT(nbt);
		});
	}
	
	public static void register(int id) {
		Network.CHANNEL.registerMessage(id++, UpdateTracker.class, UpdateTracker::encode, UpdateTracker::new, UpdateTracker::handle);
	}
	
}
