package mcpecommander.theOvercasted.init;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSounds {
	
	private static ResourceLocation loc = new ResourceLocation(Reference.MODID, "fall");
	public static SoundEvent fall = new SoundEvent(loc).setRegistryName(loc);
	private static ResourceLocation loc1 = new ResourceLocation(Reference.MODID, "man_eater_trap");
	public static SoundEvent man_eater_trap = new SoundEvent(loc1).setRegistryName(loc1);

}
