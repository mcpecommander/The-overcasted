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
		
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "armour_stand");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "langolier");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "basilisk");
		
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "stand_set");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "stand_slash");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "walk");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "walk_hands");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "sword_slash");
		
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "langolier_idle");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "eat");
		e.register(EnumResourceType.ANIM, EnumRenderType.ENTITY, "basilisk_idle");
	}

}
