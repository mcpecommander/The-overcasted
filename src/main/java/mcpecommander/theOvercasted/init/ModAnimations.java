package mcpecommander.theOvercasted.init;

import mcpecommander.theOvercasted.entity.animationTest.Animation;
import mcpecommander.theOvercasted.entity.animationTest.AnimationRegistryHelper;

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
