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
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;

public class GunCoreDamageSource extends IndirectEntityDamageSource{

	public GunCoreDamageSource(String damageTypeIn, Entity source, @Nullable Entity indirectEntity) {
		super(damageTypeIn, source, indirectEntity);
	}

	public ITextComponent getDeathMessage(LivingEntity LivingEntityIn) {
		ItemStack stack = this.getTrueSource() instanceof LivingEntity ? ((LivingEntity)this.getTrueSource()).getHeldItemMainhand() : ItemStack.EMPTY;
		String s = "death.attack." + this.damageType;
		ITextComponent icon = getItemComponent(stack);
		return new TranslationTextComponent(s, this.getTrueSource().getDisplayName(), icon, LivingEntityIn.getDisplayName());
	}
	
	private ITextComponent getItemComponent(ItemStack stack) {
		if(stack.isEmpty()) {
			ITextComponent unknown = new TranslationTextComponent("item.guncore.unknown.icon");
			unknown.applyTextStyle((hover) -> {
				hover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("item.guncore.unknown")));
			});
			return unknown;
		}
		if(stack.getItem() instanceof HeldWeapon) {
			ITextComponent icon = new TranslationTextComponent(stack.getTranslationKey() + ".icon");
			icon.applyTextStyle(stack.getRarity().color).applyTextStyle((hover) -> {
				hover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new StringTextComponent(stack.serializeNBT().toString())));
			});
			return icon;
		}
		else {
			return stack.getTextComponent();
		}
	}
	
}
