/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test;

import java.util.HashSet;

import com.gamebuster19901.guncore.GunCore;
import com.gamebuster19901.guncore.common.util.Resourced;

import net.minecraft.util.Identifier;

/**
 * Tests are chosen to be loaded at runtime using the environment variable "guncore:test"
 * 
 * Tests are to be loaded using the resource location format, as such, any subclass of Test
 * must be named to comply with the java language specification AND the resource location
 * format
 */
public abstract class Test implements Resourced{

	private final String name;
	protected boolean enabled = true;
	
	public Test() {
		this.name = getClass().getName();
	}
	
	public abstract boolean test(Object... parameters);
	
	public String getName() {
		return name;
	}
	
	@Override
	public final String getModId() {
		return GunCore.MODID;
	}
	
	public static Test[] getActiveTests() {
		Test[] tests = new Test[]{};
		try {
			String values = System.getenv(Resourced.getResourceLocation("guncore", Test.class).toString());
			if(values != null && !values.isEmpty()) {
				HashSet<Test> classes = new HashSet<Test>();
				String[] classNames = values.split("\\|");
				for(String s : classNames) {
					classes.add(toClass(s).newInstance());
				}
				tests = classes.toArray(tests);
			}
			return tests;
		}
		catch (Throwable t) {
			throw new TestingError(t);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Class<? extends Test> toClass(String locationString) throws ClassNotFoundException, NoClassDefFoundError, ClassCastException{
		Identifier location = new Identifier(locationString);
		return (Class<? extends Test>) Class.forName(Test.class.getPackage().getName() + "." + location.getPath());
	}
	
}
