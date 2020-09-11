/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore;

import static com.gamebuster19901.guncore.Main.MODID;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gamebuster19901.guncore.common.util.Resourced;
import com.gamebuster19901.guncore.proxy.ClientProxy;
import com.gamebuster19901.guncore.proxy.Proxy;
import com.gamebuster19901.guncore.proxy.ServerProxy;
import com.gamebuster19901.guncore.test.Test;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(value = MODID)
public class Main {
	public static final String MODID = "guncore";
	public static final String MODNAME = "GunCore";
	public static final String VERSION = "1.6.0.0 - 1.14.4";
	public static final Logger LOGGER = LogManager.getLogger(MODNAME);
	public static final ResourceLocation FULL_ID = Resourced.getResourceLocation(MODID, Main.class);
	private static final Test[] TESTS = Test.getActiveTests();
	
	public static Proxy proxy;
	
	private static Main instance;
	
	{
		proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	}
	
	public static Main getInstance(){
		return instance;
	}
	
	public static Test[] getTests() {
		return Arrays.copyOf(TESTS, TESTS.length);
	}
}
