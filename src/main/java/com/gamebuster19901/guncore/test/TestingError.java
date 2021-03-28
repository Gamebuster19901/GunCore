/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test;

/**
 * Signals that an unexpected error occured while
 * setting up the testing environment
 */

@SuppressWarnings("serial")
public class TestingError extends Error{

	public TestingError(Throwable t) {
		super(t);
	}
	
}
