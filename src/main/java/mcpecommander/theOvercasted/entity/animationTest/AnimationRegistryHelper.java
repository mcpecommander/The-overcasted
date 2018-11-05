package mcpecommander.theOvercasted.entity.animationTest;

import java.util.HashMap;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class AnimationRegistryHelper {
	
	private static HashMap<ResourceLocation, Animation> animations;
	
	public static void init() {
		animations = new HashMap<>();
	}
	
	public static Animation registerAnimation(String name) {
		ResourceLocation location = new ResourceLocation(Reference.MODID, "animations/"+ name+ ".csjsmodelanim");
		Animation animation = Animation.readAnim(location);
		if(animation != null) {
			animations.put(location, animation);
			return animation;
		}else {
			TheOvercasted.logger.error(name + ": Does not exist as an animation or the header is wrong. Skipping...");
			return null;
		}
	}

	public static HashMap<ResourceLocation, Animation> getAnimations() {
		return animations;
	}


}
