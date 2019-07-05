/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.overlay;

import java.util.concurrent.Callable;

public class OverlayFactory implements Callable<Overlay>{
	public static final OverlayFactory INSTANCE = new OverlayFactory();

	@Override
	public Overlay call() throws Exception {
		return new OverlayDefaultImpl();
	}
}
