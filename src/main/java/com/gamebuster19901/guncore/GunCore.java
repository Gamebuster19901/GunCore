package com.gamebuster19901.guncore;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gamebuster19901.guncore.common.util.Resourced;
import com.gamebuster19901.guncore.test.Test;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class GunCore implements ModInitializer {
	
	public static final String MODID = "guncore";
	public static final String MODNAME = "GunCore";
	public static final String VERSION = "2.0.0.0 - 1.16.5";
	public static final Logger LOGGER = LogManager.getLogger(MODNAME);
	public static final Identifier FULL_ID = Resourced.getResourceLocation(MODID, Main.class);
	private static final Test[] TESTS = Test.getActiveTests();
	
	@Override
	public void onInitialize() {
		
	}
	
	public static Test[] getTests() {
		return Arrays.copyOf(TESTS, TESTS.length);
	}
	
}
