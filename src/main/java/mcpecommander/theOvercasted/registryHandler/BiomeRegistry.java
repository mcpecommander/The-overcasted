package mcpecommander.theOvercasted.registryHandler;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.biome.BiomeMaze;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BiomeRegistry {
	
	public static Biome biomeMaze;
	
	@SubscribeEvent
	public static void RegisterBiomes(RegistryEvent.Register<Biome> e) {
		biomeMaze = new BiomeMaze();
		e.getRegistry().register(biomeMaze);
	}

}
