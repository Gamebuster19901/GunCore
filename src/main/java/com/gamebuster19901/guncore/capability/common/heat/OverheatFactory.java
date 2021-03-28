/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.heat;

import java.util.concurrent.Callable;

public class OverheatFactory implements Callable<Overheat>{

	@Override
	public Overheat call() throws Exception {
		return new OverheatDefaultImpl(0, 80, 1, 8);
	}

}
