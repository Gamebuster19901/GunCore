/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.reticle;

import java.util.concurrent.Callable;

public class ReticleFactory implements Callable<Reticle>{
	public static final ReticleFactory INSTANCE = new ReticleFactory();
	
	@Override
	public Reticle call() throws Exception {
		return new ReticleDefaultImpl();
	}
}
