/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.weapon;

import java.util.concurrent.Callable;

public class WeaponFactory implements Callable<Weapon>{

	public static final WeaponFactory INSTANCE = new WeaponFactory();
	
	@Override
	public Weapon call() throws Exception {
		return new WeaponDefaultImpl(1, 1, true);
	}

}
