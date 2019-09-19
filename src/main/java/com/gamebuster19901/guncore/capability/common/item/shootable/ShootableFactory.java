/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.shootable;

import java.util.concurrent.Callable;

public class ShootableFactory implements Callable<Shootable>{

	public static final ShootableFactory INSTANCE = new ShootableFactory();
	
	@Override
	public Shootable call() throws Exception {
		return new ShootableDefaultImpl(1, 1, 1, 0.025f, 5, 0, 0, 0, 5, null);
	}

}
