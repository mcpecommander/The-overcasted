package mcpecommander.theOvercasted.maze;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.BlockDoor.EnumHingePosition;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.ChunkPrimer;

public class NormalTerrainGenerator {

	public void generate(int chunkX, int chunkZ, ChunkPrimer chunkprimer, DungeonGenerator chunksRequired) {
		int[][] chunks = chunksRequired.getLayout();
		if (chunks[chunkX][chunkZ] != 1 && !chunksRequired.canBeNarrowTunnel(chunkX, chunkZ)) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					for (int y = 63; y < 80; y++) {
						if (y < 64) {
							chunkprimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
						} else if (y == 64) {
							chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
						} else if (y == 65 || y == 66) {

							// Start inner wall at level 65 && 66
							if(chunks[chunkX][chunkZ] == 3) {
								if(x == 0 || z == 0) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
									generateDoors(chunks, x, y, z, chunkX, chunkZ, chunkprimer, chunksRequired);
								}
								continue;
							}else if (chunks[chunkX][chunkZ] == 4) {
								if(x == 15 || z == 0) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
									generateDoors(chunks, x, y, z, chunkX, chunkZ, chunkprimer, chunksRequired);
								}
								continue;
							}else if (chunks[chunkX][chunkZ] == 5) {
								if(x == 0 || z == 15) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
									generateDoors(chunks, x, y, z, chunkX, chunkZ, chunkprimer, chunksRequired);
								}
								continue;
							}else if (chunks[chunkX][chunkZ] == 6) {
								if(x == 15 || z == 15) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
									generateDoors(chunks, x, y, z, chunkX, chunkZ, chunkprimer, chunksRequired);
								}
								continue;
							}else {
								if (x == 0 || x == 15 || z == 0 || z == 15)
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
							}
							

							// Start adding doors

							generateDoors(chunks, x, y, z, chunkX, chunkZ, chunkprimer, chunksRequired);

							// End adding doors
							// End inner wall at level 65 && 66

						} else {

							// Start inner wall
							if(chunks[chunkX][chunkZ] == 3) {
								if(x == 0 || z == 0) chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								continue;
							}else if (chunks[chunkX][chunkZ] == 4) {
								if(x == 15 || z == 0) chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								continue;
							}else if (chunks[chunkX][chunkZ] == 5) {
								if(x == 0 || z == 15) chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								continue;
							}else if (chunks[chunkX][chunkZ] == 6) {
								if(x == 15 || z == 15) chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								continue;
							}else {
								if (x == 0 || x == 15 || z == 0 || z == 15)
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
							}
							// End inner wall

						}
					}
				}
			}
		} else if (chunksRequired.canBeNarrowTunnel(chunkX, chunkZ)) {
			boolean south = chunksRequired.isNarrowSouthNorth(chunkX, chunkZ);
			for (int x = south ? 4 : 0; south ? x < 12 : x < 16; x++) {
				for (int z = south ? 0 : 4; south ? z < 16 : z < 12; z++) {
					for (int y = 63; y < 80; y++) {
						if (y < 64) {
							chunkprimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
						} else if (y == 64) {
							chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
						} else if (y == 65 || y == 66) {

							// Start inner wall at level 65 && 66

							if (south) {
								if (x == 4 || x == 11) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							} else {
								if (z == 4 || z == 11) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}

							// Start adding doors
							// generateDoors(chunks, x, y, z, chunkX, chunkZ, chunkprimer, chunksRequired);
							// End adding doors

							// End inner wall at level 65 && 66

						} else {

							// Start inner wall
							if (chunksRequired.isNarrowSouthNorth(chunkX, chunkZ)) {
								if (x == 4 || x == 11) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							} else {
								if (z == 4 || z == 11) {
									chunkprimer.setBlockState(x, y, z, Blocks.COBBLESTONE.getDefaultState());
								}
							}
							// End inner wall

						}
					}
				}
			}
		}
	}

	public void generateDoors(int[][] chunks, int x, int y, int z, int chunkX, int chunkZ, ChunkPrimer chunkprimer,
			DungeonGenerator chunksRequired) {
		if (y == 65) {
			if (x == 0) {
				if (chunkX - 1 >= 0) {
					if (chunks[chunkX - 1][chunkZ] != 1 && (!chunksRequired.canBeNarrowTunnel(chunkX, chunkZ))
							|| (chunksRequired.canBeNarrowTunnel(chunkX - 1, chunkZ)
									&& !chunksRequired.isNarrowSouthNorth(chunkX - 1, chunkZ))) {
						if (z == 7)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
											.withProperty(BlockDoor.FACING, EnumFacing.WEST));
						if (z == 8)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
											.withProperty(BlockDoor.FACING, EnumFacing.WEST));
					}
				}
			} else if (x == 15) {
				if (chunkX + 1 <= 12) {
					if (chunks[chunkX + 1][chunkZ] != 1 && (!chunksRequired.canBeNarrowTunnel(chunkX, chunkZ))
							|| (chunksRequired.canBeNarrowTunnel(chunkX + 1, chunkZ)
									&& !chunksRequired.isNarrowSouthNorth(chunkX + 1, chunkZ))) {
						if (z == 7)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
											.withProperty(BlockDoor.FACING, EnumFacing.EAST));
						if (z == 8)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
											.withProperty(BlockDoor.FACING, EnumFacing.EAST));
					}
				}
			}
			if (z == 0) {
				if (chunkZ - 1 >= 0) {
					if (chunks[chunkX][chunkZ - 1] != 1 && (!chunksRequired.canBeNarrowTunnel(chunkX, chunkZ))
							|| (chunksRequired.canBeNarrowTunnel(chunkX, chunkZ - 1)
									&& chunksRequired.isNarrowSouthNorth(chunkX, chunkZ - 1))) {
						if (x == 7)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
											.withProperty(BlockDoor.FACING, EnumFacing.NORTH));
						if (x == 8)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
											.withProperty(BlockDoor.FACING, EnumFacing.NORTH));
					}
				}
			} else if (z == 15) {
				if (chunkZ + 1 <= 12) {
					if (chunks[chunkX][chunkZ + 1] != 1 && (!chunksRequired.canBeNarrowTunnel(chunkX, chunkZ))
							|| (chunksRequired.canBeNarrowTunnel(chunkX, chunkZ + 1)
									&& chunksRequired.isNarrowSouthNorth(chunkX, chunkZ + 1))) {
						if (x == 7)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
											.withProperty(BlockDoor.FACING, EnumFacing.SOUTH));
						if (x == 8)
							chunkprimer.setBlockState(x, y, z,
									Blocks.ACACIA_DOOR.getDefaultState()
											.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
											.withProperty(BlockDoor.FACING, EnumFacing.SOUTH));
					}
				}
			}

		} else if (y == 66) {
			if (x == 0) {
				if (chunkprimer.getBlockState(x, y - 1, z).getBlock() instanceof BlockDoor) {
					if (z == 7)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
										.withProperty(BlockDoor.FACING, EnumFacing.EAST)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
					if (z == 8)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
										.withProperty(BlockDoor.FACING, EnumFacing.EAST)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
				}
			} else if (x == 15) {
				if (chunkprimer.getBlockState(x, y - 1, z).getBlock() instanceof BlockDoor) {
					if (z == 7)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
										.withProperty(BlockDoor.FACING, EnumFacing.EAST)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
					if (z == 8)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
										.withProperty(BlockDoor.FACING, EnumFacing.EAST)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
				}
			}
			if (z == 0) {
				if (chunkprimer.getBlockState(x, y - 1, z).getBlock() instanceof BlockDoor) {
					if (x == 7)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
										.withProperty(BlockDoor.FACING, EnumFacing.SOUTH)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
					if (x == 8)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
										.withProperty(BlockDoor.FACING, EnumFacing.SOUTH)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
				}
			} else if (z == 15) {
				if (chunkprimer.getBlockState(x, y - 1, z).getBlock() instanceof BlockDoor) {
					if (x == 7)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.RIGHT)
										.withProperty(BlockDoor.FACING, EnumFacing.SOUTH)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
					if (x == 8)
						chunkprimer.setBlockState(x, y, z,
								Blocks.ACACIA_DOOR.getDefaultState()
										.withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT)
										.withProperty(BlockDoor.FACING, EnumFacing.SOUTH)
										.withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER));
				}
			}
		}
	}

}
