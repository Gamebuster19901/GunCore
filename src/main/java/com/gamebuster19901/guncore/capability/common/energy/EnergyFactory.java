/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.energy;

import java.util.concurrent.Callable;

public class EnergyFactory implements Callable<Energy>{
	
	@Override
	public Energy call() throws Exception {
		return new EnergyDefaultImpl(100, 100, 100, 100);
	}

}
