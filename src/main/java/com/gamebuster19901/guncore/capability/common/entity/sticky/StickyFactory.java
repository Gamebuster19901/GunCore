/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.sticky;

import java.util.concurrent.Callable;

public class StickyFactory implements Callable<Sticky>{

	@Override
	public Sticky call() throws Exception {
		return new StickyDefaultImpl();
	}

}
