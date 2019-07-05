/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.util;

import javax.annotation.Nullable;

import com.gamebuster19901.guncore.common.item.abstracts.HeldWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.HoverEvent;

public class GunCoreDamageSource extends EntityDamageSourceIndirect{

	public GunCoreDamageSource(String damageTypeIn, Entity source, @Nullable Entity indirectEntity) {
		super(damageTypeIn, source, indirectEntity);
	}

	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
		ItemStack stack = this.getTrueSource() instanceof EntityLivingBase ? ((EntityLivingBase)this.getTrueSource()).getHeldItemMainhand() : ItemStack.EMPTY;
		String s = "death.attack." + this.damageType;
		ITextComponent icon = getItemComponent(stack);
		return new TextComponentTranslation(s, this.getTrueSource().getDisplayName(), icon, entityLivingBaseIn.getDisplayName());
	}
	
	private ITextComponent getItemComponent(ItemStack stack) {
		if(stack.isEmpty()) {
			ITextComponent unknown = new TextComponentTranslation("item.guncore.unknown.icon");
			unknown.applyTextStyle((hover) -> {
				hover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("item.guncore.unknown")));
			});
			return unknown;
		}
		if(stack.getItem() instanceof HeldWeapon) {
			ITextComponent icon = new TextComponentTranslation(stack.getTranslationKey() + ".icon");
			icon.applyTextStyle(stack.getRarity().color).applyTextStyle((hover) -> {
				hover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new TextComponentString(stack.serializeNBT().toString())));
			});
			return icon;
		}
		else {
			return stack.getTextComponent();
		}
	}
	
}
