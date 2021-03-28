/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap<Class, Object> data = new HashMap();
	
	public ArbitraryData(Object...objects) {
		for(Object o : objects) {
			if (data.put(o.getClass(), o) != null) {
				Main.LOGGER.log(Level.WARN, "Overwrote data of type " + o.getClass().getSimpleName() + " in updateEvent");
			}
		}
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type) {
		return (T)data.get(type);
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public <T> T getInstanceOf(Class<T> type) {
		for(Object obj : data.values()) {
			if(type.isInstance(obj)) {
				return (T)obj;
			}
		}
		return null;
	}
	
}
