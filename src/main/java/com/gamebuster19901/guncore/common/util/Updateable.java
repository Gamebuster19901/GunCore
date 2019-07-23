/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.util;

public interface Updateable {
	
	public default void onTick(Object... data) {
		if(canUpdate(data)) {
			update(data);
		}
	}
	
	@SuppressWarnings("unused")
	public default boolean canUpdate(Object... data) {
		return true;
	}
	
	public void update(Object... data);
	
	public default ArbitraryData getUpdateData(Object... data) {
		return getUpdateDataStatically(data);
	}
	
	public static ArbitraryData getUpdateDataStatically(Object... data){
		return new ArbitraryData(data);
	}
}
