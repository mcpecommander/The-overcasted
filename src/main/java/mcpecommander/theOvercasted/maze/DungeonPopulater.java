package mcpecommander.theOvercasted.maze;

import mcpecommander.theOvercasted.entity.entities.EntityLargeRoomOverseer;
import mcpecommander.theOvercasted.entity.entities.EntityNarrowRoomOverseer;
import mcpecommander.theOvercasted.entity.entities.EntityOverseer;
import mcpecommander.theOvercasted.entity.entities.fatties.EntityFatSack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DungeonPopulater {
	
	public void popluate(int chunkX, int chunkZ, World world, DungeonGenerator chunksRequired) {
		int[][] chunks = chunksRequired.getLayout();
		if(chunks[chunkX][chunkZ] == 0) return;
		
		switch (chunks[chunkX][chunkZ]) {
		case 1:
			
			EntityOverseer overseer = new EntityOverseer(world);
			overseer.setLocationAndAngles(chunkX * 16 + 8, 150, chunkZ * 16 + 8, 0, 0);
			world.spawnEntity(overseer);
			((DungeonWorldProvider)world.provider).overseers.add(overseer);
			int rand = world.rand.nextInt(4) + 2;
			for(int count = 0; count < rand+1; count ++) {			
				int x = chunkX * 16 + (world.rand.nextInt(13) + 2);
				int z = chunkZ * 16 + (world.rand.nextInt(13) + 2);
				EntityFatSack entity = new EntityFatSack(world, (byte) world.rand.nextInt(2));
				entity.setPosition(x, 65, z);
				world.spawnEntity(entity);
				
			}
			break;
		case 2:
			EnumFacing direction = checkNarrow(chunksRequired, (DungeonWorldProvider)world.provider, chunkX, chunkZ);
			if(direction != EnumFacing.UP) {
				EntityNarrowRoomOverseer smallOverseer = new EntityNarrowRoomOverseer(world);
				smallOverseer.setLocationAndAngles(chunkX * 16 + 8, 150, chunkZ * 16 + 8, 0, 0);
				smallOverseer.setDirection(direction);
				world.spawnEntity(smallOverseer);
				((DungeonWorldProvider)world.provider).overseers.add(smallOverseer);
			}
			break;
		case 3:
			EntityLargeRoomOverseer largeOverseer = new EntityLargeRoomOverseer(world);
			largeOverseer.setLocationAndAngles(chunkX * 16 + 8, 150, chunkZ * 16 + 8, 0, 0);
			world.spawnEntity(largeOverseer);
			((DungeonWorldProvider)world.provider).overseers.add(largeOverseer);
			break;
		default:
			break;
		}

	}
	
	public static EntityOverseer getOverseer(DungeonWorldProvider provider, int chunkX, int chunkZ) {
		for(EntityOverseer overseer : provider.overseers) {
			if(overseer.chunkCoordX == chunkX && overseer.chunkCoordZ == chunkZ) return overseer;
		}
		return null;
	}

	public static EnumFacing checkNarrow(DungeonGenerator dungeon, DungeonWorldProvider provider, int chunkX, int chunkZ) {
		int[][] layout = dungeon.getLayout();
		if(dungeon.isNarrowSouthNorth(chunkX, chunkZ)) {
			if(chunkZ - 1 > 1 && layout[chunkX][chunkZ - 1] == 2) {
				return getOverseer(provider, chunkX, chunkZ - 1) == null ? EnumFacing.NORTH : EnumFacing.UP;
			}else if(chunkZ + 1 < dungeon.getMaxColumns() - 1 && layout[chunkX][chunkZ + 1] == 2) {
				return getOverseer(provider, chunkX, chunkZ + 1) == null ? EnumFacing.SOUTH : EnumFacing.UP;
			}else {
				return EnumFacing.DOWN;
			}
		}else {
			if(chunkX - 1 > 1 && layout[chunkX - 1][chunkZ] == 2) {
				return getOverseer(provider, chunkX - 1, chunkZ) == null ? EnumFacing.WEST : EnumFacing.UP;
			}else if(chunkX + 1 < dungeon.getMaxColumns() - 1 && layout[chunkX + 1][chunkZ] == 2) {
				return getOverseer(provider, chunkX + 1, chunkZ) == null ? EnumFacing.EAST : EnumFacing.UP;
			}else {
				return EnumFacing.DOWN;
			}
		}
	}
}
