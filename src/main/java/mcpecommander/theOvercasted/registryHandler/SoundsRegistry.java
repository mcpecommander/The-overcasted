package mcpecommander.theOvercasted.registryHandler;

import mcpecommander.theOvercasted.init.ModSounds;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SoundsRegistry {
	
	@SubscribeEvent
	public static void soundsRegistry(RegistryEvent.Register<SoundEvent> e) {
		e.getRegistry().register(ModSounds.fall);
		
	}

}
