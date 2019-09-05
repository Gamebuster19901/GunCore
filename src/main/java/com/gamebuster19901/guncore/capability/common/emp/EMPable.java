package com.gamebuster19901.guncore.capability.common.emp;

import net.minecraft.nbt.IntNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface EMPable extends INBTSerializable<IntNBT>{

	public void emp(int duration);
	
	public int getDuration();
	
	public boolean isEMPed();
	
}
