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
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "conjoined_sack");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "fly");
		e.register(EnumResourceType.MODEL, EnumRenderType.ENTITY, "fat_sack");
		
	}

}
