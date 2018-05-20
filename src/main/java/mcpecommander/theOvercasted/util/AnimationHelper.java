package mcpecommander.theOvercasted.util;

import com.leviathanstudio.craftstudio.client.animation.ClientAnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.util.ResourceLocation;

public class AnimationHelper {
	
	public static void startAnimation(AnimationHandler handler, IAnimated element, String name ) {
		if(!handler.isAnimationActive(Reference.MODID, name, element)) {
			((ClientAnimationHandler)handler).clientStartAnimation(Reference.MODID+":"+ name, 0, element);
		}		
	}
	
	public static void startHoldAnimation(AnimationHandler handler, IAnimated element, String name) {
		if(!handler.isHoldAnimationActive(new ResourceLocation(Reference.MODID, name).toString(), element)) {
			((ClientAnimationHandler)handler).clientStartAnimation(Reference.MODID+":"+ name, 0, element);
		}
	}
	
	public static boolean stopAnimation(AnimationHandler handler, IAnimated element, String name) {
		if(handler.isHoldAnimationActive(Reference.MODID+ ":" +name, element)) {
			return ((ClientAnimationHandler)handler).clientStopAnimation(Reference.MODID +":"+ name, element);
		}
		return true;
	}
	
	public static void stopStartAnimation(AnimationHandler handler, IAnimated element, String oldName, String newName) {
		if(!handler.isAnimationActive(Reference.MODID, newName, element)) {
			((ClientAnimationHandler)handler).stopStartAnimation(Reference.MODID, oldName, Reference.MODID, newName, 0, element);
		}
		
	}
	
	public static void networkStartAnimation(AnimationHandler handler, IAnimated element, String name, boolean client) {
		if(!handler.isAnimationActive(Reference.MODID, name, element)) {
			handler.networkStartAnimation(Reference.MODID, name, 0, element, client);
		}
	}
	
	public static void networkStopAnimation(AnimationHandler handler, IAnimated element, String name, boolean client) {
		if(handler.isAnimationActive(Reference.MODID, name, element)) {
			handler.networkStopAnimation(Reference.MODID , name, element, client);
		}
	}
	
	public static void networkStopStartAnimation(AnimationHandler handler, IAnimated element, String oldName, String newName, boolean client) {
		handler.networkStopStartAnimation(Reference.MODID, oldName, Reference.MODID, newName, 0, element, client);
	}

}
