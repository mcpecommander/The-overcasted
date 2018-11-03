package mcpecommander.theOvercasted.registryHandler;

import com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper;
import com.leviathanstudio.craftstudio.client.registry.CraftStudioLoader;
import com.leviathanstudio.craftstudio.client.util.EnumRenderType;
import com.leviathanstudio.craftstudio.client.util.EnumResourceType;

import mcpecommander.theOvercasted.Reference;

public class CraftStudioRegistry {
	
	@CraftStudioLoader
	public static void registerCraftStudioAssests() {
		CSRegistryHelper e = new CSRegistryHelper(Reference.MODID);
		
		//Models
		//Entities
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "armour_stand");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "langolier");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "basilisk");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "man_eater");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "mushroom_person");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "snow_rat");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "test");
		
		//Blocks
		e.register(EnumResourceType.MODEL, EnumRenderType.BLOCK, "mushroom_sprout");
		
		//Armour stand animations
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "stand_set");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "stand_slash");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "walk");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "walk_hands");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "sword_slash");
		
		//Langolier animations
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "langolier_idle");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "eat");
		
		//Basilisk animations
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "basilisk_idle");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "basilisk_jump");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "basilisk_walk");
		
		//Maneater animations
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "man_eat");
		
		//Mushroom Person animations
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "mushroom_walk");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "mushroom_hide");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "mushroom_punch");
		
		//Mushroom sprout animations
		e.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "mushroom_grow");
		
		//Snow Rat animations
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "rat_idle");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "rat_walk");
	}

}
