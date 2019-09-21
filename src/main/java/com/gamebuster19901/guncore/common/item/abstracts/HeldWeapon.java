/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.item.abstracts;

import com.gamebuster19901.guncore.capability.common.item.weapon.Weapon;
import com.gamebuster19901.guncore.capability.common.item.weapon.WeaponDefaultImpl;
import com.gamebuster19901.guncore.common.item.GunCoreItem;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public abstract class HeldWeapon extends GunCoreItem{
	
	public HeldWeapon() {
		super(1);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new ICapabilityProvider() {			
			@Override
			public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
				if(capability == WeaponDefaultImpl.CAPABILITY) {
					return (LazyOptional<T>) LazyOptional.of(() -> new WeaponDefaultImpl(1,1,false));
				}
				return LazyOptional.empty();
			}
		};
	}
	
	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		Capability<Weapon> weapon = WeaponDefaultImpl.CAPABILITY;
		
		if(isSelected) {
			if(stack.getCapability(weapon).isPresent()) {
				stack.getCapability(weapon).orElseThrow(AssertionError::new).onTick(stack, worldIn, entityIn, itemSlot, isSelected);
			}
		}
	}
	
	@Override
	public boolean shouldSyncTag() {
		return true;
	}

	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT nbt = stack.getCapability(WeaponDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new).serializeNBT();
		return nbt;
	}

	@Override
	public void readShareTag(ItemStack stack, CompoundNBT nbt) {
		stack.getCapability(WeaponDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new).deserializeNBT(nbt);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if(slot == EquipmentSlotType.MAINHAND) {
			Weapon weapon = stack.getCapability(WeaponDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
			if(weapon.canMelee()) {
				float damage = weapon.getMeleeDamage();
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, AttributeModifier.Operation.ADDITION));
			}
		}
		return multimap;
	}
}
