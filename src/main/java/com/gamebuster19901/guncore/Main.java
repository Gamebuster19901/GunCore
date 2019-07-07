/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore;

import static com.gamebuster19901.guncore.Main.MODID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gamebuster19901.guncore.proxy.ClientProxy;
import com.gamebuster19901.guncore.proxy.Proxy;
import com.gamebuster19901.guncore.proxy.ServerProxy;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(value = MODID)
public class Main {
	public static final String MODID = "guncore";
	public static final String MODNAME = "GunCore";
	public static final String VERSION = "0.0.0.0 - 1.14.3";
	public static final Logger LOGGER = LogManager.getLogger(MODNAME);
	
	public static Proxy proxy;
	
	private static Main instance;
	
	{
		proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	}
	
	public static Main getInstance(){
		return instance;
	}
}
