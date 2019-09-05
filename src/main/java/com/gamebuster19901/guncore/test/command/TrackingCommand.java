/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test.command;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.tracker.Tracker;
import com.gamebuster19901.guncore.capability.common.tracker.TrackerBaseImpl;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class TrackingCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		Main.LOGGER.info("Registering tracker command...");
		dispatcher.register(Commands.literal("tracking")
			.then(Commands.argument("target", EntityArgument.entity())
				.executes((command) -> {
					return getTrackingInfo(command.getSource(), EntityArgument.getEntity(command, "target"));
		})));
	}
	
	@SuppressWarnings("resource")
	public static int getTrackingInfo(CommandSource source, Entity target) {
		if(target.getCapability(TrackerBaseImpl.CAPABILITY).isPresent()) {
			Tracker tracker = target.getCapability(TrackerBaseImpl.CAPABILITY).orElseThrow(AssertionError::new);
			if(tracker.isTracking()) {
				World world = tracker.getWorld();
				if(tracker.getTrackee() != null) {
					Entity trackee = tracker.getTrackee();
					DimensionType dimension = world.dimension.getType();
					source.sendFeedback(new TranslationTextComponent("commands.guncore.tracking.success.entity", target.getDisplayName(), trackee.getDisplayName(), dimension.getRegistryName(), dimension.getId()), true);
				}
				else {
					Vec3d destination = tracker.getDestination();
					DimensionType dimension = world.getDimension().getType();
					source.sendFeedback(new TranslationTextComponent("commands.guncore.tracking.success.location", target.getDisplayName(), destination.x, destination.y, destination.z, dimension.getRegistryName(), dimension.getId()), true);
				}
			}
			else {
				source.sendFeedback(new TranslationTextComponent("commands.guncore.tracking.success.none", target.getDisplayName()), true);
			}
		}
		else {
			source.sendErrorMessage(new TranslationTextComponent("commands.guncore.tracking.failure.nonexistant", target.getDisplayName()));
		}
		return 1;
	}
	
}
