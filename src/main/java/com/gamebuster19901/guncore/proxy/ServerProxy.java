/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.proxy;

import com.gamebuster19901.guncore.Main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ServerProxy extends Proxy{
	
	public ServerProxy() {
		super();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	@SuppressWarnings("unused")
	public void serverSetup(FMLServerStartingEvent e) {
		Main.LOGGER.info("SERVER SETUP");
	}
	
}
