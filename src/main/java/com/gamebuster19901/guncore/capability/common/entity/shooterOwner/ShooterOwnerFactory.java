/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.shooterOwner;

import java.util.concurrent.Callable;

import com.gamebuster19901.guncore.capability.common.item.shootable.ShootableFactory;

import net.minecraft.entity.Entity;

public class ShooterOwnerFactory implements Callable<ShooterOwner>{

	public static final ShooterOwnerFactory INSTANCE = new ShooterOwnerFactory();
	
	@Override
	public ShooterOwner call() throws Exception {
		return new ShooterOwnerDefaultImpl((Entity)null, ShootableFactory.INSTANCE.call());
	}

}
