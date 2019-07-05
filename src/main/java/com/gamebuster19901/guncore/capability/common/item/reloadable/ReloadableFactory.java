/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.reloadable;

import java.util.concurrent.Callable;

public class ReloadableFactory implements Callable<Reloadable>{

	public static final ReloadableFactory INSTANCE = new ReloadableFactory();
	
	@Override
	public Reloadable call() throws Exception {
		return new ReloadableDefaultImpl(5, 5);
	}

}
