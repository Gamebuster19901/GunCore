package com.gamebuster19901.guncore.capability.common.item.reloadable;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ReloadableStorage implements IStorage<Reloadable>{

	public static final ReloadableStorage INSTANCE = new ReloadableStorage();
	
	@Override
	public INBTBase writeNBT(Capability<Reloadable> capability, Reloadable instance, EnumFacing side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Reloadable> capability, Reloadable reloadable, EnumFacing side, INBTBase tag) {
		reloadable.deserializeNBT((NBTTagCompound) tag);
	}

}
