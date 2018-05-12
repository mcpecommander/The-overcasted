package mcpecommander.theOvercasted.events;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.init.ModPotions;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class ClientEvents {
	
	@SubscribeEvent
	public static void inputEvent(InputUpdateEvent e) {
		if(e.getEntityPlayer().isPotionActive(ModPotions.potionStasis)) {
			e.getMovementInput().moveForward = 0;
			e.getMovementInput().moveStrafe = 0;
		}
	}


}
