package mcpecommander.theOvercasted.util;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.util.ResourceLocation;

public class AnimationHelper {
	
	/**
	 * Start the animation on the client.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param name The name of the animation to start.
	 * 
	 */
	public static void startAnimation(AnimationHandler handler, IAnimated element, String name ) {
		if(!handler.isAnimationActive(Reference.MODID, name, element)) {
			((ClientAnimationHandler)handler).clientStartAnimation(Reference.MODID+":"+ name, 0, element);
		}		
	}
	
	/**
	 * Start the animation on the client but checks if it is holding too beforehand.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param name The name of the animation to start (preferably hold animations).
	 * 
	 */
	public static void startHoldAnimation(AnimationHandler handler, IAnimated element, String name) {
		if(!handler.isHoldAnimationActive(new ResourceLocation(Reference.MODID, name).toString(), element)) {
			((ClientAnimationHandler)handler).clientStartAnimation(Reference.MODID+":"+ name, 0, element);
		}
	}
	
	/**
	 * Stop the animation on the client.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param name The name of the animation to start.
	 * 
	 * @return {@code true} if the animation stopped successfully or the animation was never on in the first place.
	 * 
	 */
	public static boolean stopAnimation(AnimationHandler handler, IAnimated element, String name) {
		if(handler.isHoldAnimationActive(Reference.MODID+ ":" +name, element)) {
			return ((ClientAnimationHandler)handler).clientStopAnimation(Reference.MODID +":"+ name, element);
		}
		return true;
	}
	
	/**
	 * Stop an animation on the client and starts a new one immediately.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param oldName The name of the animation to stop.
	 * @param newName The name of the animation to start.
	 * 
	 */
	public static void stopStartAnimation(AnimationHandler handler, IAnimated element, String oldName, String newName) {
		if(!handler.isAnimationActive(Reference.MODID, newName, element)) {
			((ClientAnimationHandler)handler).stopStartAnimation(Reference.MODID, oldName, Reference.MODID, newName, 0, element);
		}
		
	}
	
	/**
	 * Checks if the animation is in the holding phase.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param name The name of the animation to start.
	 * 
	 * @return {@code true} if the animation is running in the holding phase.
	 * 
	 */
	public static boolean isAnimationHolding(AnimationHandler handler, IAnimated element, String name) {
		return !handler.isAnimationActive(Reference.MODID, name, element) && handler.isHoldAnimationActive(Reference.MODID + ":" + name, element);
	}
	
	/**
	 * Start the animation on the specified side.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param name The name of the animation to start.
	 * @param client Which side to start the animation from.
	 * 
	 */
	public static void networkStartAnimation(AnimationHandler handler, IAnimated element, String name, boolean client) {
		if(!handler.isAnimationActive(Reference.MODID, name, element)) {
			handler.networkStartAnimation(Reference.MODID, name, 0, element, client);
		}
	}
	
	/**
	 * Stop the animation on the specified side.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param name The name of the animation to start.
	 * @param client Which side to stop the animation from.
	 * 
	 */
	public static void networkStopAnimation(AnimationHandler handler, IAnimated element, String name, boolean client) {
		if(handler.isAnimationActive(Reference.MODID, name, element)) {
			handler.networkStopAnimation(Reference.MODID , name, element, client);
		}
	}
	
	/**
	 * Stop an animation and starts a new one immediately on a specific side.
	 * 
	 * @param handler The animation handler.
	 * @param element The entity or the tile entity.
	 * @param oldName The name of the animation to stop.
	 * @param newName The name of the animation to start.
	 * @param client If the animation should run on the client or on the server.
	 * 
	 */
	public static void networkStopStartAnimation(AnimationHandler handler, IAnimated element, String oldName, String newName, boolean client) {
		handler.networkStopStartAnimation(Reference.MODID, oldName, Reference.MODID, newName, 0, element, client);
	}

}
