/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import java.util.concurrent.Callable;

import com.gamebuster19901.guncore.capability.common.tracker.impl.TrackerNoImpl;

public class TrackerFactory implements Callable<Tracker>{

	@Override
	public Tracker call() throws Exception {
		return new TrackerNoImpl();
	}

}
