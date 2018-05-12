package mcpecommander.theOvercasted;

import net.minecraft.util.ResourceLocation;

public class Reference {

	public static final String MODID = "overcasted";
	public static final String NAME = "mcpecommander's Overcasted mod";
	public static final String VERSION = "0.0.1";
	public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.12, 1.13)";
	
	public static final String CLIENT_PROXY_CLASS = "mcpecommander.theOvercasted.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "mcpecommander.theOvercasted.proxy.ServerProxy";
	
	public enum ModEntities{
		
		ARMOURSTAND(new ResourceLocation(MODID, "armour_stand")),
		LANGOLIER(new ResourceLocation(MODID, "langolier"));
		
		private ResourceLocation name;
		
		private ModEntities(ResourceLocation registryName) {
			this.name = registryName;
			
		}

		public ResourceLocation getRegistryName() {
			return name;
		}
		
		public String getName() {
			return name.toString();
		}
		
		
	}
	
	public enum ModPotions{
		
		STASIS(new ResourceLocation(MODID, "stasis_potion"));
		
		private ResourceLocation name;
		
		private ModPotions(ResourceLocation name) {
			this.name = name;
		}
		
		public ResourceLocation getRegistryName() {
			return name;
		}
		
		public String getName() {
			return name.toString();
		}
	}
	
}