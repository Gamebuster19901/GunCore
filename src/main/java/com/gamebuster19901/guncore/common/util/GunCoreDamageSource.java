/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.util;

import org.jetbrains.annotations.Nullable;

import com.gamebuster19901.guncore.common.item.abstracts.HeldWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class GunCoreDamageSource extends ProjectileDamageSource{

	public GunCoreDamageSource(String damageTypeIn, Entity source, @Nullable Entity indirectEntity) {
		super(damageTypeIn, source, indirectEntity);
	}

	public Text getDeathMessage(LivingEntity LivingEntityIn) {
		ItemStack stack = this.getAttacker() instanceof LivingEntity ? ((LivingEntity)this.getSource()).getMainHandStack() : ItemStack.EMPTY;
		String s = "death.attack." + this.name;
		Text icon = getItemComponent(stack);
		return new TranslatableText(s, this.getAttacker().getDisplayName(), icon, LivingEntityIn.getDisplayName());
	}
	
	private Text getItemComponent(ItemStack stack) {
		if(stack.isEmpty()) {
			Text unknown = new TranslatableText("item.guncore.unknown.icon");
			unknown.shallowCopy().styled((hover) -> {
				return hover.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("item.guncore.unknown")));
			});
			return unknown;
		}
		if(stack.getItem() instanceof HeldWeapon) {
			Text icon = new TranslatableText(stack.getTranslationKey() + ".icon");
			icon.shallowCopy().styled((style) -> {
				return style.withFormatting(stack.getRarity().formatting).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(stack)));
			});
			return icon;
		}
		else {
			return stack.toHoverableText();
		}
	}
	
}
