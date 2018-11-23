package mcpecommander.theOvercasted.maze;

import mcpecommander.theOvercasted.entity.entities.fatties.EntityFatSack;
import net.minecraft.world.World;

public class DungeonPopulater {
	
	public void popluate(int chunkX, int chunkZ, World world, DungeonGenerator chunksRequired) {
		int[][] chunks = chunksRequired.getLayout();
		switch (chunks[chunkX][chunkZ]) {
		case 1:
			int rand = world.rand.nextInt(4) + 2;
			for(int count = 0; count < rand+1; count ++) {			
				int x = chunkX * 16 + (world.rand.nextInt(13) + 2);
				int z = chunkZ * 16 + (world.rand.nextInt(13) + 2);
				EntityFatSack entity = new EntityFatSack(world, (byte) world.rand.nextInt(2));
				entity.setPosition(x, 65, z);
				world.spawnEntity(entity);
			}
			break;
		default:
			break;
		}

	}

}
