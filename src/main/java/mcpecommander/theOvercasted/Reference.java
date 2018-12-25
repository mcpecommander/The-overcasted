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
	public static final ResourceLocation STATS_CAP = new ResourceLocation(MODID, "stats");
	public static final ResourceLocation FOLLOWER_CAP = new ResourceLocation(MODID, "follower");
	
	public static final int PICKUPS_GUI_ID = 1;
	
	
	public enum ModEntities{
		
		OVERSEER(new ResourceLocation(MODID, "overseer")),
		LARGE_OVERSEER(new ResourceLocation(MODID, "large_overseer")),
		SMALL_OVERSEER(new ResourceLocation(MODID, "small_overseer")),
		LANGOLIER(new ResourceLocation(MODID, "langolier")),
		BASILISK(new ResourceLocation(MODID, "basilisk")),
		MANEATER(new ResourceLocation(MODID, "man_eater")),
		MUSHROOMPERSON(new ResourceLocation(MODID, "mushroom_person")),
		SNOWRAT(new ResourceLocation(MODID, "snow_rat")),
		CONJOINED_SACK(new ResourceLocation(MODID, "conjioned_sack")),
		BLACK_FLY(new ResourceLocation(MODID, "black_fly")),
		ATTACK_FLY(new ResourceLocation(MODID, "attack_fly")),
		FAT_SACK(new ResourceLocation(MODID, "fat_sack")),
		BOMB(new ResourceLocation(MODID, "bomb")),
		TEAR(new ResourceLocation(MODID, "tear")),
		SLAVE_TEAR(new ResourceLocation(MODID, "slave_tear")),
		PRIMAL_FAMILIAR(new ResourceLocation(MODID, "familiar"));
		
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
		TNT(new ResourceLocation(MODID, "block_tnt")),
		PEDESTAL(new ResourceLocation(MODID, "block_pedestal"));
		
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
		BOMB(new ResourceLocation(MODID, "item_bomb")),
		LASER_BEAM(new ResourceLocation(MODID, "item_laser_beam")),
		TEAR(new ResourceLocation(MODID, "item_tear")),
		ITEM(new ResourceLocation(MODID, "item_item"));
		
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
	
	public static class GuiTextures{
		public static final ResourceLocation BOMB = new ResourceLocation(Reference.MODID, "textures/gui/bomb.png");
		public static final ResourceLocation COIN = new ResourceLocation(Reference.MODID, "textures/gui/coin.png");
		public static final ResourceLocation KEY = new ResourceLocation(Reference.MODID, "textures/gui/key.png");
		public static final ResourceLocation SPEED = new ResourceLocation(Reference.MODID, "textures/gui/speed.png");
		public static final ResourceLocation RANGE = new ResourceLocation(Reference.MODID, "textures/gui/range.png");
		public static final ResourceLocation FIRE_RATE = new ResourceLocation(Reference.MODID, "textures/gui/fire_rate.png");
		public static final ResourceLocation PROJECTILE_SPEED = new ResourceLocation(Reference.MODID, "textures/gui/projectile_speed.png");
		public static final ResourceLocation DAMAGE = new ResourceLocation(Reference.MODID, "textures/gui/damage.png");
		public static final ResourceLocation CROSS = new ResourceLocation(Reference.MODID, "textures/gui/cross.png");
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
				{0,0,0,0,0,0, 0,0, 0,0,0,0,0,0},
				{0,0,2,0,0,0, 2,2, 0,0,0,2,0,0},
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
