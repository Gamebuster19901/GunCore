/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.emp;

import java.util.concurrent.Callable;

public class EMPableFactory implements Callable<EMPable>{

	@Override
	public EMPable call() throws Exception {
		return new EMPableDefaultImpl();
	}

}
