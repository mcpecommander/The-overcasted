package mcpecommander.theOvercasted.init;

import mcpecommander.theOvercasted.animationSystem.Animation;
import mcpecommander.theOvercasted.animationSystem.AnimationRegistryHelper;

public class ModAnimations {
	
	public static Animation fat_walk;
	public static Animation popping;
	public static Animation sizing;
	
	public static void init() {
		fat_walk = AnimationRegistryHelper.registerAnimation("fat_walk");
		popping = AnimationRegistryHelper.registerAnimation("popping");
		sizing = AnimationRegistryHelper.registerAnimation("sizing");
	}

}
