package com.gamebuster19901.guncore.capability.common.emp;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EMPableStorage implements IStorage<EMPable>{

	@Override
	public INBT writeNBT(Capability<EMPable> capability, EMPable instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<EMPable> capability, EMPable instance, Direction side, INBT nbt) {
		instance.deserializeNBT((IntNBT) nbt);
	}

}
