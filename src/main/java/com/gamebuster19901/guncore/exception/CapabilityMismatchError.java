/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.exception;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;

/**
 * Thrown when the client receives a capability from the server for an
 * object that does not have that capability
 * <p>
 * This means that a mod is adding the capability to the object on the
 * server, but not on the client. As such, the client and server are
 * not compatible, and the connection should be terminated.
 * <p>
 * This probably means that the client and server are on different
 * versions and have incompatibly changed, but the network protocol
 * version hasn't changed.
 */
@SuppressWarnings("serial")
public class CapabilityMismatchError extends Error{
	
	public CapabilityMismatchError(Capability capability, CapabilityProvider object) {
		super(object + " has capability " + capability.getName() + " on the server, but not on the client!");
	}
	
	public CapabilityMismatchError(Capability capability, CapabilityProvider object, Throwable cause) {
		super(object + " has capability " + capability.getName() + " on the server, but not on the client!", cause);
	}
	
}
