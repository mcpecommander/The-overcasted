package mcpecommander.theOvercasted.events;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityOverseer;
import mcpecommander.theOvercasted.maze.DungeonWorldProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.world.ChunkEvent;
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
	public static void onChunkLoad(ChunkEvent.Load event) {
		if(event.getWorld().provider instanceof DungeonWorldProvider && !event.getWorld().isRemote) {
			DungeonWorldProvider provider = (DungeonWorldProvider) event.getWorld().provider;
			Chunk chunk = event.getChunk();
			if(chunk.x > provider.getDungeon().getMaxRows() - 1 || chunk.z > provider.getDungeon().getMaxColumns() - 1
					|| chunk.x < 0 || chunk.z < 0) return;
			if(provider.getDungeon().getLayout()[chunk.x][chunk.z] != 0) {
				for (Entity entity : chunk.getEntityLists()[15]) {
					if (entity instanceof EntityOverseer) {
//						provider.overseers.add((EntityOverseer) entity);
					}
				}
			}
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
