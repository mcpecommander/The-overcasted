package mcpecommander.theOvercasted.events;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityNarrowRoomOverseer;
import mcpecommander.theOvercasted.entity.entities.EntityOverseer;
import mcpecommander.theOvercasted.entity.entities.IRoomRequirement;
import mcpecommander.theOvercasted.maze.DungeonWorldProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
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
		if(!event.getWorld().isRemote) {
			if(event.getWorld().provider instanceof DungeonWorldProvider ) {
				DungeonWorldProvider provider = (DungeonWorldProvider) event.getWorld().provider;
				Chunk chunk = event.getChunk();
				if(chunk.x > provider.getDungeon().getMaxRows() - 1 || chunk.z > provider.getDungeon().getMaxColumns() - 1
						|| chunk.x < 0 || chunk.z < 0) return;
				if(provider.getDungeon().getLayout()[chunk.x][chunk.z] != 0) {
					
					EntityOverseer overseer = null;
					for (Entity entity : chunk.getEntityLists()[15]) {
						if (entity instanceof EntityOverseer) {
							provider.overseers.add((EntityOverseer) entity);
							overseer = (EntityOverseer) entity;
						}
					}
					if(overseer != null) {
						for(Entity entity : chunk.getEntityLists()[4]) {
							if(entity instanceof IRoomRequirement) {
								overseer.getEntities().add(entity);
							}
						}
					}
					if(provider.getDungeon().getLayout()[chunk.x][chunk.z] == 3) {
						Chunk chunk4 = event.getWorld().getChunkFromChunkCoords(chunk.x + 1, chunk.z);
						Chunk chunk5 = event.getWorld().getChunkFromChunkCoords(chunk.x, chunk.z + 1);
						Chunk chunk6 = event.getWorld().getChunkFromChunkCoords(chunk.x + 1, chunk.z + 1);
						for(Entity entity : chunk4.getEntityLists()[4]) {
							if(entity instanceof IRoomRequirement) {
								overseer.getEntities().add(entity);
							}
						}
						for(Entity entity : chunk5.getEntityLists()[4]) {
							if(entity instanceof IRoomRequirement) {
								overseer.getEntities().add(entity);
							}
						}
						for(Entity entity : chunk6.getEntityLists()[4]) {
							if(entity instanceof IRoomRequirement) {
								overseer.getEntities().add(entity);
							}
						}
					}else if(overseer != null && overseer instanceof EntityNarrowRoomOverseer) {
						EnumFacing facing = ((EntityNarrowRoomOverseer) overseer).getDirection();
						Chunk chunkNeighbour = event.getWorld().getChunkFromChunkCoords(
								chunk.x + facing.getDirectionVec().getX(), chunk.z + facing.getDirectionVec().getZ());
						for(Entity entity : chunkNeighbour.getEntityLists()[4]) {
							if(entity instanceof IRoomRequirement) {
								overseer.getEntities().add(entity);
							}
						}
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
