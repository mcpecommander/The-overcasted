package mcpecommander.theOvercasted.events;

import mcpecommander.theOvercasted.Reference;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ItemEvents {
	
	@SubscribeEvent
	public static void itemFinishUse(LivingEntityUseItemEvent.Finish event) {
		
		
	}

}
