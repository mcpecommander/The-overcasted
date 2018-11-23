package mcpecommander.theOvercasted.maze;

import java.util.List;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.registryHandler.Registry;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class DungeonDecorator {
	private byte[][] currentLayout = null;
	
	public void decorate(int chunkX, int chunkZ, World world, DungeonGenerator chunksRequired) {
		int[][] chunks = chunksRequired.getLayout();
		switch(chunks[chunkX][chunkZ]) {
		case 7:
			BlockPos pos = new BlockPos(chunkX * 16 + 8, 65, chunkZ * 16 + 8);
			world.setBlockState(pos, Blocks.CHEST.getDefaultState());
			TileEntity tile = world.getTileEntity(pos);
			if(tile != null) {
				TileEntityChest chest = (TileEntityChest) tile;
				chest.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, chunksRequired.random.nextLong());
			}
			break;
		case 1:
			if(currentLayout == null) {
				List<byte[][]> list = chunksRequired.getPossibleRooms();
				currentLayout = list.get(world.rand.nextInt(list.size()));
						
			}
			readLayout(currentLayout, world, chunkX, chunkZ);
			break;
		default:
			for(int holes = 0; holes < 5; holes++) {
				int x = chunkX * 16 + chunksRequired.random.nextInt(14) + 1;
				int z = chunkZ * 16 + chunksRequired.random.nextInt(14) + 1;
				world.setBlockToAir(new BlockPos(x, 64, z));
				world.setBlockToAir(new BlockPos(x, 63, z));
			}
			break;
			
		}
	}

	private void readLayout(byte[][] currentLayout, World world, int chunkX, int chunkZ) {
		BlockPos pos = BlockPos.ORIGIN;
		for(int x = 0; x < 14; x ++) {
			for(int z = 0; z < 14; z++) {
				pos = new BlockPos(chunkX * 16 + x + 1, 65, chunkZ * 16 + z + 1);
				switch(currentLayout[x][z]) {
				case 0:
					break;
				case 1:
					world.setBlockState(pos, Registry.ROCK.getDefaultState());
					break;
				case 2:
					world.setBlockState(pos, Registry.VASE.getDefaultState());
					break;
				case 3:
					world.setBlockState(pos, Registry.POOP.getAllowedStates());
					break;
				case 4:
					world.setBlockState(pos, Blocks.WEB.getDefaultState());
					break;
				}
			}
		}
		this.currentLayout = null;
		
	}

}
