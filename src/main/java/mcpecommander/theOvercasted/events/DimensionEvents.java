package mcpecommander.theOvercasted.events;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class DimensionEvents {
	
	public static BlockPos spawnPos;
	
	@SubscribeEvent
	public static void onDimensionChange(PlayerChangedDimensionEvent event) {
		
		if(event.toDim == 100) {
			spawnPos = event.player.getPosition();
			event.player.world.setSpawnPoint(spawnPos);
			event.player.setSpawnPoint(spawnPos, true);
		}
	}
	
	@SubscribeEvent
	public static void onEnteringChunk(EnteringChunk event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
//			System.out.println(player);
		}
	}

}
