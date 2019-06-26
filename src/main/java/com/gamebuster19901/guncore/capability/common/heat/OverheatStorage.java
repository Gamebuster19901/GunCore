package com.gamebuster19901.guncore.capability.common.heat;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class OverheatStorage implements IStorage<Overheat>{

	@Override
	public INBTBase writeNBT(Capability<Overheat> capability, Overheat instance, EnumFacing side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Overheat> capability, Overheat instance, EnumFacing side, INBTBase nbt) {
		instance.deserializeNBT((NBTTagCompound) nbt);
	}

}
