package mcpecommander.theOvercasted;

import javax.vecmath.Vector3f;

import net.minecraft.util.ResourceLocation;

public class Reference {

	public static final String MODID = "overcasted";
	public static final String NAME = "mcpecommander's Overcasted mod";
	public static final String VERSION = "0.0.1";
	public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.12, 1.13)";
	
	public static final String CLIENT_PROXY_CLASS = "mcpecommander.theOvercasted.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "mcpecommander.theOvercasted.proxy.ServerProxy";
	
	public static final Vector3f EMPTY_VECTOR = new Vector3f();
	
	public static final ResourceLocation PICKUPS_CAP = new ResourceLocation(MODID, "pickups");
	
	public enum ModEntities{
		
		ARMOURSTAND(new ResourceLocation(MODID, "armour_stand")),
		LANGOLIER(new ResourceLocation(MODID, "langolier")),
		BASILISK(new ResourceLocation(MODID, "basilisk")),
		MANEATER(new ResourceLocation(MODID, "man_eater")),
		MUSHROOMPERSON(new ResourceLocation(MODID, "mushroom_person")),
		SNOWRAT(new ResourceLocation(MODID, "snow_rat")),
		CONJOINED_SACK(new ResourceLocation(MODID, "conjioned_sack")),
		FLY(new ResourceLocation(MODID, "fly")),
		FAT_SACK(new ResourceLocation(MODID, "fat_sack")),
		BOMB(new ResourceLocation(MODID, "bomb"));
		
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
	
	public enum ModBlocks{
		
		POOP(new ResourceLocation(MODID, "block_poop")),
		BASEMENT_WOOD(new ResourceLocation(MODID, "block_basement_wood")),
		ROCK(new ResourceLocation(MODID, "block_rock")),
		VASE(new ResourceLocation(MODID, "block_vase")),
		TNT(new ResourceLocation(MODID, "block_tnt"));
		
		private ResourceLocation name;
		
		private ModBlocks(ResourceLocation name) {
			this.name = name;
		}
		
		public ResourceLocation getRegistryName() {
			return name;
		}
		
		public String getName() {
			return name.toString();
		}
	}
	
	public enum ModItems{
		BOMB(new ResourceLocation(MODID, "item_bomb"));
		
		private ResourceLocation name;

		private ModItems(ResourceLocation name) {
			this.name = name;
		}

		public ResourceLocation getRegistryName() {
			return name;
		}

		public String getName() {
			return name.toString();
		}
	}
	
	public static class RoomLayouts{
		
		public static final byte[][] BASEMENT_EMPTY =  {
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0}
				};
		
		public static final byte[][] BASEMENT =  {
				{0,0,0,0,0,1, 0,0, 1,0,0,0,0,0},
				{0,0,0,0,1,2, 0,0, 2,1,0,0,0,0},
				{0,0,0,0,1,2, 0,0, 2,1,0,0,0,0},
				{0,0,0,0,1,2, 0,0, 2,1,0,0,0,0},
				{0,1,1,1,1,2, 0,0, 2,1,1,1,1,0},
				{1,2,2,2,2,2, 0,0, 2,2,2,2,2,1},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{1,2,2,2,2,2, 0,0, 2,2,2,2,2,1},
				{0,1,1,1,1,2, 0,0, 2,1,1,1,1,0},
				{0,0,0,0,1,2, 0,0, 2,1,0,0,0,0},
				{0,0,0,0,1,2, 0,0, 2,1,0,0,0,0},
				{0,0,0,0,1,2, 0,0, 2,1,0,0,0,0},
				{0,0,0,0,0,1, 0,0, 1,0,0,0,0,0}
				};
		
		public static final byte[][] BASEMENT1 =  {
				{1,1,0,0,0,0, 0,0, 0,0,0,0,1,1},
				{1,0,0,0,0,0, 0,0, 0,0,0,0,0,1},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 3,3, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 3,3, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,0,0,0,0,0, 0,0, 0,0,0,0,0,1},
				{1,1,0,0,0,0, 0,0, 0,0,0,0,1,1}
				};
		
		public static final byte[][] BASEMENT2 =  {
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,1,0,0,0, 0,0, 0,0,0,1,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,1, 0,0, 1,0,0,0,0,0},
				{0,0,0,0,1,0, 0,0, 0,1,0,0,0,0},
				
				{0,0,0,0,1,0, 0,0, 0,1,0,0,0,0},
				{0,0,0,0,1,0, 0,0, 0,1,0,0,0,0},
				
				{0,0,0,0,1,0, 0,0, 0,1,0,0,0,0},
				{0,0,0,0,0,1, 0,0, 1,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,1,0,0,0, 0,0, 0,0,0,1,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0}
				};
		
		public static final byte[][] BASEMENT3 =  {
				{0,0,0,0,0,0, 0,0, 0,0,1,1,1,1},
				{0,0,0,0,0,0, 0,0, 0,0,1,1,1,1},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,1,1},
				{0,0,0,0,0,0, 0,0, 0,0,1,1,1,1},
				{0,0,0,0,0,0, 0,0, 0,0,1,1,1,1},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,1,1,1,0,0, 0,0, 0,0,0,0,0,0},
				{1,1,1,1,0,0, 0,0, 0,0,0,0,0,0},
				{1,1,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,1,1,1,0,0, 0,0, 0,0,0,0,0,0},
				{1,1,1,1,0,0, 0,0, 0,0,0,0,0,0}
				};
		
		public static final byte[][] BASEMENT4 =  {
				{0,1,1,0,0,0, 0,0, 0,1,1,1,1,0},
				{1,1,1,0,0,0, 0,0, 0,1,1,1,1,1},
				{1,1,1,0,0,0, 0,0, 0,1,1,1,1,1},
				{1,1,1,0,0,0, 0,0, 0,1,1,1,1,1},
				{1,0,0,0,0,0, 0,0, 0,0,1,1,1,1},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,1,1,1,0,0, 0,0, 0,0,0,0,0,1},
				{1,1,1,1,1,0, 0,0, 0,0,0,1,1,1},
				{1,1,1,1,1,0, 0,0, 0,0,0,1,1,1},
				{1,1,1,1,1,0, 0,0, 0,0,0,1,1,1},
				{0,1,1,1,1,0, 0,0, 0,0,0,1,1,0}
				};
		
		public static final byte[][] BASEMENT5 =  {
				{0,2,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{2,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,2,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{2,0,0,0,0,1, 1,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{2,0,0,0,0,1, 1,0, 0,0,0,0,0,0},
				{1,2,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{2,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,2,0,0,0,0, 0,0, 0,0,0,0,0,0}
				};
		
		public static final byte[][] BASEMENT6 =  {
				{0,0,4,0,0,2, 0,0, 2,0,0,4,0,0},
				{0,0,0,0,0,0, 2,2, 0,0,0,0,0,0},
				{0,0,2,0,0,0, 0,0, 0,0,0,2,0,0},
				{0,0,0,0,0,4, 0,0, 4,0,0,0,0,0},
				{0,0,4,0,0,0, 0,0, 0,0,0,4,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0}
				};
		
		public static final byte[][] BASEMENT7 =  {
				{1,1,0,0,0,0, 0,0, 0,0,0,0,1,1},
				{1,0,0,0,0,0, 0,0, 0,0,0,0,0,1},
				{0,0,0,0,0,0, 0,1, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 1,1, 0,0,0,0,0,0},
				{0,0,0,0,0,1, 1,1, 1,0,0,0,0,0},
				{0,0,0,0,1,1, 1,1, 1,1,0,0,0,0},
				
				{0,0,1,1,1,1, 0,0, 1,1,1,0,0,0},
				{0,0,0,1,1,1, 0,0, 1,1,1,1,0,0},
				
				{0,0,0,0,1,1, 1,1, 1,1,0,0,0,0},
				{0,0,0,0,0,1, 1,1, 1,0,0,0,0,0},
				{0,0,0,0,0,0, 1,1, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 1,0, 0,0,0,0,0,0},
				{1,0,0,0,0,0, 0,0, 0,0,0,0,0,1},
				{1,1,0,0,0,0, 0,0, 0,0,0,0,1,1}
				};
		
		public static final byte[][] BASEMENT8 =  {
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,1,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 1,0,0,0,0,0},
				
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,0,0,0,1, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,1,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0}
				};
		
		public static final byte[][] BASEMENT9 =  {
				{0,5,0,0,5,0, 0,0, 0,5,0,0,5,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,5,5,0,0, 0,0, 0,0,5,5,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,5,0,0,5,0, 0,0, 0,5,0,0,5,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,0,5,5,0,0, 0,0, 0,0,5,5,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				
				{0,5,0,0,5,0, 0,0, 0,5,0,0,5,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{1,0,0,1,0,0, 0,0, 0,0,1,0,0,1}
				};
		
	}
	
}
