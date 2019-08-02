/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.stickable;

import java.util.concurrent.Callable;

public class StickableFactory implements Callable<Stickable>{

	@Override
	public Stickable call() throws Exception {
		return new StickableDefaultImpl();
	}

}
