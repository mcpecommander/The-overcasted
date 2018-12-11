package mcpecommander.theOvercasted.config;

import mcpecommander.theOvercasted.Reference;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MODID, category = "gui", name = "Overcasted/config")
public class OvercastedConfig {
	
	@Config.Comment("hud' Configs")
	public static final Hud hud = new Hud();

	public static class Hud {
		
		@Config.Comment("X position of the hud GUI")
		@Config.RangeInt(min=0)
		public int xPos = 2;
		
		@Config.Comment("Y position of the hud GUI")
		@Config.RangeInt(min=0)
		public int yPos = 60;
		
		@Config.Comment("Scale of the gui")
		@Config.RangeInt(min=1,max=20)
		public int scale = 12;
		
		@Config.Comment("The extra info hud and whether it should be enabled or not")
		public boolean extraHud = true;
		
	}

}
