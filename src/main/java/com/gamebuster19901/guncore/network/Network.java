/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.network;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.network.packet.server.UpdateStickable;
import com.gamebuster19901.guncore.network.packet.server.UpdateTracker;

import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class Network {
	private static final String PROTOCOL_VERSION = "0";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(Main.FULL_ID, () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	private Network() {
		throw new AssertionError();
	}
	
	public static void register() {
		int id = 0;
		UpdateStickable.register(id++);
		UpdateTracker.register(id++);
	}

}
