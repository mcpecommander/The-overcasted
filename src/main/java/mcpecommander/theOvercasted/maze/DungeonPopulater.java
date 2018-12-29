package mcpecommander.theOvercasted.maze;

import mcpecommander.theOvercasted.entity.entities.EntityLargeRoomOverseer;
import mcpecommander.theOvercasted.entity.entities.EntityNarrowRoomOverseer;
import mcpecommander.theOvercasted.entity.entities.EntityOverseer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class DungeonPopulater {
	
	public void popluate(int chunkX, int chunkZ, World world, DungeonGenerator chunksRequired, int layout) {
		int[][] chunks = chunksRequired.getLayout();
		if(chunks[chunkX][chunkZ] == 0) return;
		
		switch (chunks[chunkX][chunkZ]) {
		case 1:
			
			EntityOverseer overseer = new EntityOverseer(world);
			overseer.setLocationAndAngles(chunkX * 16 + 8, 255, chunkZ * 16 + 8, 0, 0);
			overseer.setRoomLayout(layout);
			world.spawnEntity(overseer);
			((DungeonWorldProvider)world.provider).overseers.add(overseer);
			break;
		case 2:
			EnumFacing direction = checkNarrow(chunksRequired, (DungeonWorldProvider)world.provider, chunkX, chunkZ, world);
			if(direction != EnumFacing.UP) {
				EntityNarrowRoomOverseer smallOverseer = new EntityNarrowRoomOverseer(world);
				smallOverseer.setLocationAndAngles(chunkX * 16 + 8, 255, chunkZ * 16 + 8, 0, 0);
				smallOverseer.setRoomLayout(layout);
				smallOverseer.setDirection(direction);
				world.spawnEntity(smallOverseer);
				((DungeonWorldProvider)world.provider).overseers.add(smallOverseer);
			}
			break;
		case 3:
			EntityLargeRoomOverseer largeOverseer = new EntityLargeRoomOverseer(world);
			largeOverseer.setLocationAndAngles(chunkX * 16 + 8, 255, chunkZ * 16 + 8, 0, 0);
			largeOverseer.setRoomLayout(layout);
			world.spawnEntity(largeOverseer);
			((DungeonWorldProvider)world.provider).overseers.add(largeOverseer);
			break;
		default:
			break;
		}

	}
	
	public static EntityOverseer getOverseerByChunks(DungeonWorldProvider provider, Chunk chunk) {
		for(EntityOverseer overseer : provider.overseers) {
			if(overseer.isChunkUnderThisControl(chunk)) return overseer;
		}
		return null;
	}

	public static EnumFacing checkNarrow(DungeonGenerator dungeon, DungeonWorldProvider provider, int chunkX, int chunkZ, World world) {
		int[][] layout = dungeon.getLayout();
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		if(dungeon.isNarrowSouthNorth(chunkX, chunkZ)) {
			if(chunkZ - 1 > 1 && layout[chunkX][chunkZ - 1] == 2) {
				return getOverseerByChunks(provider, chunk) == null ? EnumFacing.NORTH : EnumFacing.UP;
			}else if(chunkZ + 1 < dungeon.getMaxColumns() - 1 && layout[chunkX][chunkZ + 1] == 2) {
				return getOverseerByChunks(provider, chunk) == null ? EnumFacing.SOUTH : EnumFacing.UP;
			}else {
				return EnumFacing.DOWN;
			}
		}else {
			if(chunkX - 1 > 1 && layout[chunkX - 1][chunkZ] == 2) {
				return getOverseerByChunks(provider, chunk) == null ? EnumFacing.WEST : EnumFacing.UP;
			}else if(chunkX + 1 < dungeon.getMaxColumns() - 1 && layout[chunkX + 1][chunkZ] == 2) {
				return getOverseerByChunks(provider, chunk) == null ? EnumFacing.EAST : EnumFacing.UP;
			}else {
				return EnumFacing.DOWN;
			}
		}
	}
}
