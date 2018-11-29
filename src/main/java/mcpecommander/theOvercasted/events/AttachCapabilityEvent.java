package mcpecommander.theOvercasted.events;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.pickupsSystem.PickupsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class AttachCapabilityEvent {
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof EntityPlayer) {
			event.addCapability(Reference.PICKUPS_CAP, new PickupsProvider());
		}
	}

}
