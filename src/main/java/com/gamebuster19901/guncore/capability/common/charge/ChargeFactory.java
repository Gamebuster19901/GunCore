/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.charge;

import java.util.concurrent.Callable;

public class ChargeFactory implements Callable<Charge>{

	@Override
	public Charge call() throws Exception {
		return new ChargeDefaultImpl();
	}

}
