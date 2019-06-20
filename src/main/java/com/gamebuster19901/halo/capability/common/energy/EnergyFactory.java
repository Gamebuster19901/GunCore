package com.gamebuster19901.halo.capability.common.energy;

import java.util.concurrent.Callable;

public class EnergyFactory implements Callable<Energy>{
	
	@Override
	public Energy call() throws Exception {
		return new EnergyDefaultImpl(100, 100, 100, 100);
	}

}
