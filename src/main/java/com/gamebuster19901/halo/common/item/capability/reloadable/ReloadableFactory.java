package com.gamebuster19901.halo.common.item.capability.reloadable;

import java.util.concurrent.Callable;

public class ReloadableFactory implements Callable<Reloadable>{

	public static final ReloadableFactory INSTANCE = new ReloadableFactory();
	
	@Override
	public Reloadable call() throws Exception {
		return new ReloadableDefaultImpl(5, 5);
	}

}
