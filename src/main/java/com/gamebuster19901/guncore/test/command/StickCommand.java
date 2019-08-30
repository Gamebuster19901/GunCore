/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test.command;

import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultImpl;
import com.gamebuster19901.guncore.capability.common.entity.sticky.Sticky;
import com.gamebuster19901.guncore.capability.common.entity.sticky.StickyDefaultImpl;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TranslationTextComponent;

public class StickCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("stick")
			.then(Commands.argument("stickable", EntityArgument.entity())
				.executes((command) -> {
					return getStickableInfo(command.getSource(), EntityArgument.getEntity(command, "stickable"));
				}).then(Commands.argument("sticky", EntityArgument.entity())
					.executes((command) -> {
						return stick(command.getSource(), EntityArgument.getEntity(command, "stickable"), EntityArgument.getEntity(command, "sticky"));
					})
				)
			)
		);
				
	}
	
	public static int getStickableInfo(CommandSource source, Entity stickableEntity) {
		if(stickableEntity.getCapability(StickableDefaultImpl.CAPABILITY).isPresent()) {
			Stickable stickable = stickableEntity.getCapability(StickableDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
			
			String entities = "\n\n";
			
			if(stickable.getAmountStuck() == 0) {
				source.sendFeedback(new TranslationTextComponent("commands.guncore.stick.info.success.none", getEntityString(stickableEntity)), true);
				return 1;
			}
			
			for(Sticky sticky : stickable.getAllStickies().values()) {
				entities = entities += getEntityString(sticky.getStickyEntity()) + "\n";
			}
			
			source.sendFeedback(new TranslationTextComponent("commands.guncore.stick.info.success", getEntityString(stickableEntity), stickable.getAmountStuck(), entities), true);
		}
		else {
			source.sendErrorMessage(new TranslationTextComponent("commands.guncore.stick.stickable.failure", getEntityString(stickableEntity)));
		}
		return 1;
	}
	
	public static int stick(CommandSource source, Entity stickableEntity, Entity stickyEntity) {
		if(stickableEntity.getCapability(StickableDefaultImpl.CAPABILITY).isPresent()) {
			if(stickyEntity.getCapability(StickyDefaultImpl.CAPABILITY).isPresent()) {
				Sticky sticky = stickyEntity.getCapability(StickyDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
				if(sticky.canStick(stickableEntity)) {
					if(sticky.stick(stickableEntity)) {
						source.sendFeedback(new TranslationTextComponent("commands.guncore.stick.sticky.success", getEntityString(stickyEntity), getEntityString(stickableEntity)), true);
					}
					else {
						source.sendErrorMessage(new TranslationTextComponent("commands.guncore.stick.sticky.failure.rejected.unexpected"));
					}
				}
				else {
					source.sendErrorMessage(new TranslationTextComponent("commands.guncore.stick.sticky.failure.rejected", getEntityString(stickyEntity), getEntityString(stickableEntity)));
				}
			}
			else {
				source.sendErrorMessage(new TranslationTextComponent("commands.guncore.stick.sticky.failure.missing", getEntityString(stickyEntity)));
			}
		}
		else {
			source.sendErrorMessage(new TranslationTextComponent("commands.guncore.stick.stickable.failure.missing", getEntityString(stickableEntity)));
		}
		return 1;
	}
	
	private static String getEntityString(Entity entity) {
		return entity.getDisplayName().getFormattedText() + " [" + entity.getEntityId() + "]";
	}
	
}
