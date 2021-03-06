/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.util;

public interface Updateable {
	
	public void onTick(Object... data);
	
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
