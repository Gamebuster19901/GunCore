/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.util;

import java.util.HashMap;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import com.gamebuster19901.guncore.Main;

public class ArbitraryData {

	private HashMap<Class, Object> data = new HashMap();
	
	public ArbitraryData(Object...objects) {
		for(Object o : objects) {
			if (data.put(o.getClass(), o) != null) {
				Main.LOGGER.log(Level.WARN, "Overwrote data of type " + o.getClass().getSimpleName() + " in updateEvent");
			}
		}
	}
	
	@Nullable
	public <T> T get(Class<T> type) {
		return (T)data.get(type);
	}
	
	@Nullable
	public <T> T getInstanceOf(Class<T> type) {
		for(Object obj : data.values()) {
			if(obj.getClass().isAssignableFrom(type)) {
				return (T)obj;
			}
		}
		return null;
	}
	
}
