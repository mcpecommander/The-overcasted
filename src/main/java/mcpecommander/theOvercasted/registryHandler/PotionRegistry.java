package mcpecommander.theOvercasted.registryHandler;

import mcpecommander.theOvercasted.potion.PotionStasis;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionRegistry {

	@SubscribeEvent
	public static void registerPotion(RegistryEvent.Register<Potion> e) {
		e.getRegistry().register(new PotionStasis());
	}
}
