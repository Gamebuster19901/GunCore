package com.gamebuster19901.guncore.capability.common.emp;

import net.minecraft.nbt.IntNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EMPableDefaultImpl implements EMPable{

	@CapabilityInject(EMPable.class)
	public Capability<EMPable> CAPABILITY;
	
	private int duration;
	
	@Override
	public void emp(int duration) {
		this.duration = duration;
	}

	@Override
	public boolean isEMPed() {
		return duration > 0;
	}
	
	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public IntNBT serializeNBT() {
		return new IntNBT(duration);
	}

	@Override
	public void deserializeNBT(IntNBT duration) {
		this.duration = duration.getInt();
	}
	
	@Override
	public void onTick(Object...data) {
		if(isEMPed()) {
			duration--;
		}
	}

	@Override
	public void update(Object... data) {
		// TODO Auto-generated method stub
		
	}

}
