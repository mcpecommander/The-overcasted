package mcpecommander.theOvercasted.maze;

import mcpecommander.theOvercasted.block.BlockPoop;
import mcpecommander.theOvercasted.block.BlockTNT;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityPedestal;
import mcpecommander.theOvercasted.init.ModRoomLayouts;
import mcpecommander.theOvercasted.item.effects.Attribute;
import mcpecommander.theOvercasted.maze.RoomLayout.RoomType;
import mcpecommander.theOvercasted.registryHandler.Registry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DungeonDecorator {
	private ResourceLocation[][] currentLayout = null;
	
	public void decorate(int chunkX, int chunkZ, World world, DungeonGenerator chunksRequired, DungeonPopulater populater) {
		int[][] chunks = chunksRequired.getLayout();
		switch(chunks[chunkX][chunkZ]) {
		case 7:
			BlockPos pos = new BlockPos(chunkX * 16 + 8, 65, chunkZ * 16 + 8);
			world.setBlockState(pos, Registry.PEDESTAL.getDefaultState());
			TileEntity tile = world.getTileEntity(pos);
			if(tile != null) {
				TileEntityPedestal pedestal = (TileEntityPedestal) tile;
				pedestal.setItemStack(new ItemStack(Registry.ITEM, 1, chunksRequired.random.nextInt(Attribute.REGISTRY.size()) + 1));
			}
			break;
		case 1:
			if(currentLayout == null) {
				int randomNormalLayout = chunksRequired.random.nextInt(ModRoomLayouts.layouts.getObject(RoomType.NORMAL).size());
				currentLayout = ModRoomLayouts.layouts.getObject(RoomType.NORMAL).get(randomNormalLayout).getDeco();
						
			}
			readNormalLayout(currentLayout, world, chunkX, chunkZ);
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

	private void readNormalLayout(ResourceLocation[][] currentLayout, World world, int chunkX, int chunkZ) {
		BlockPos pos = BlockPos.ORIGIN;
		for(int x = 0; x < 14; x ++) {
			for(int z = 0; z < 14; z++) {
				pos = new BlockPos(chunkX * 16 + x + 1, 65, chunkZ * 16 + z + 1);
				Block block = ForgeRegistries.BLOCKS.getValue(currentLayout[x][z]);
				if(block instanceof BlockPoop) {
					world.setBlockState(pos, Registry.POOP.getAllowedStates());
				}else if (block instanceof BlockTNT){
					world.setBlockState(pos, Registry.TNT.getRandomHorizontalDirection());
				}else {
					world.setBlockState(pos, block.getDefaultState());
				}
			}
		}
		this.currentLayout = null;
		
	}

}
