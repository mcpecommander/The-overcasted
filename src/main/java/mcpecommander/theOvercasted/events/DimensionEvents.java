package mcpecommander.theOvercasted.events;

import baubles.common.network.PacketHandler;
import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
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

}
