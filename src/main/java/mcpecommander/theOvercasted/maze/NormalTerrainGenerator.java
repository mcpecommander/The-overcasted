package mcpecommander.theOvercasted.maze;

import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.BlockDoor.EnumHingePosition;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class NormalTerrainGenerator {

	public void generate(int chunkX, int chunkZ, ChunkPrimer chunkprimer, int[][] chunks) {
		if(chunkX < 13 && chunkX >= 0 && chunkZ < 13 && chunkZ >= 0) {
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					for(int y = 63; y < 80; y++) {
						if(y <64) {
							chunkprimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
						}else if (y == 64){
							chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
						}else if(y == 65 || y == 66){
							
							
							//Start inner wall at level 65 && 66
							if((x == 0 || x == 15 || z == 0 || z == 15)/* && (chunkX + chunkZ) % 2 == 0*/)
//								if((x != 7 && x != 8) && (z != 7 && z != 8)) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
									
									//Start adding doors
//								}
							if(y == 65){
									if(x == 0 ) {
										if(chunkX - 1 >= 0 && chunks[chunkX - 1][chunkZ] == 0) {
											if(z == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.EAST));
											if(z == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.EAST));
										}
									}else if (x == 15) {
										if(chunkX + 1 <= 12 && chunks[chunkX + 1][chunkZ] == 0) {
											if(z == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.EAST));
											if(z == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.EAST));
										}
									}
									if(z == 0) {
										if(chunkZ - 1 >= 0 && chunks[chunkX][chunkZ - 1] == 0) {
											if(x == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH));
											if(x == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH));
										}
									}else if (z == 15) {
										if(chunkZ + 1 <= 12 && chunks[chunkX][chunkZ + 1] == 0) {
											if(x == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH));
											if(x == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH));
										}
									}
									
								}else {
									if(x == 0 ) {
										if(chunkX - 1 >= 0 && chunks[chunkX - 1][chunkZ] == 0) {
											if(z == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
											if(z == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
										}
									}else if (x == 15) {
										if(chunkX + 1 <= 12 && chunks[chunkX + 1][chunkZ] == 0) {
											if(z == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
											if(z == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
										}
									}
									if(z == 0) {
										if(chunkZ - 1 >= 0 && chunks[chunkX][chunkZ - 1] == 0) {
											if(x == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
											if(x == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
										}
									}else if (z == 15) {
										if(chunkZ + 1 <= 12 && chunks[chunkX][chunkZ + 1] == 0) {
											if(x == 7)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
											if(x == 8)
												chunkprimer.setBlockState(x, y, z, Blocks.ACACIA_DOOR.getDefaultState().withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.FACING, EnumFacing.SOUTH).withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
										}
									}
								}
							//End adding doors
							//End inner wall at level 65 && 66
							
							//Start outer wall at level 65 && 66
							if(chunkX == 0) {
								if (x == 0) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							} 
							if (chunkZ == 0) {
								if (z == 0) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							} 
							if (chunkX == 12) {
								if (x == 15) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}
							if (chunkZ == 12){
								if (z == 15) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}
							//End outer wall at level 65 && 66
						}else {
							
							
							//Start inner wall
							if((x == 0 || x == 15 || z == 0 || z == 15) /*&& (chunkX + chunkZ) % 2 == 0*/)
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
							//End inner wall
							
							//Start outer wall
							if(chunkX == 0) {
								if (x == 0) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}
							if (chunkZ == 0) {
								if (z == 0) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}
							if (chunkX == 12) {
								if (x == 15) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}
							if (chunkZ == 12){
								if (z == 15) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}
							//End outer wall
						}
					}
				}
			}
		}
	}

}
