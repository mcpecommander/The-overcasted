package mcpecommander.theOvercasted.maze;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import mcpecommander.theOvercasted.entity.entities.EntityFly;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class TerrianGen implements IChunkGenerator {

	private final World worldObj;
	private static Random random;
	private Biome[] biomesForGeneration;
	private int[][] chunksRequired;

	private List<Biome.SpawnListEntry> mobs = Lists
			.newArrayList(new Biome.SpawnListEntry(EntityFly.class, 100, 2, 2));

	private NormalTerrainGenerator terraingen = new NormalTerrainGenerator();

	public TerrianGen(World worldObj) {
		this.worldObj = worldObj;
		long seed = worldObj.getSeed();
		this.random = new Random();
		chunksRequired = createMap(12, 12, 6, 10);
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		
		
		ChunkPrimer chunkprimer = new ChunkPrimer();
		if(x < 12 && z < 12 &&  x > -1 && z > -1 && chunksRequired[x][z] == 0) {
			terraingen.generate(x, z, chunkprimer, chunksRequired);
		}

		Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

		chunk.generateSkylightMap();
		return chunk;
	}
	
	public static int[][] createMap(int row, int column, int length, int maxTunnels) {
		int[][] array = empty2DArray(1, 13, 13);
		int dimension = row * column;
		int currentRow = (int) Math.floor(Math.random() * row); // our current row - start at a random spot
		int currentColumn = (int) Math.floor(Math.random() * column); // our current column - start at a random spot
		Direction lastDirection = Direction.STOPPING, randomDirection;

		while (maxTunnels > 0 && dimension > 0 && length > 0) {

			// lets get a random direction - until it is a perpendicular to our
			// lastDirection
			// if the last direction = left or right,
			// then our new direction has to be up or down,
			// and vice versa
			do {
				randomDirection = Direction.getRandom(random.nextInt(4));
			} while ((randomDirection.row == -lastDirection.row && randomDirection.column == -lastDirection.column)
					|| (randomDirection.row == lastDirection.row && randomDirection.column == lastDirection.column));

			int randomLength = (int) MathHelper.clamp(Math.ceil(Math.random() * length), 3, length), // length the next tunnel will be (max of
																		// maxLength)
					tunnelLength = 0; // current length of tunnel being created

			// lets loop until our tunnel is long enough or until we hit an edge
			while (tunnelLength < randomLength) {

				// break the loop if it is going out of the map
				if (((currentRow == 0) && (randomDirection.row == -1))
						|| ((currentColumn == 0) && (randomDirection.column == -1))
						|| ((currentRow == row - 1) && (randomDirection.row == 1))
						|| ((currentColumn == column - 1) && (randomDirection.column == 1))) {
					break;
				} else {
					array[currentRow][currentColumn] = 0; // set the value of the index in map to 0 (a tunnel, making it
															// one longer)
					currentRow += randomDirection.row; // add the value from randomDirection to row and col (-1, 0, or
														// 1) to update our location
					currentColumn += randomDirection.column;
					tunnelLength++; // the tunnel is now one longer, so lets increment that variable
				}
			}

			if (tunnelLength > 0) { // update our variables unless our last loop broke before we made any part of a
									// tunnel
				lastDirection = randomDirection; // set lastDirection, so we can remember what way we went
				maxTunnels--; // we created a whole tunnel so lets decrement how many we have left to create
			}
		}
		return array; // all our tunnels have been created and our map is complete, so lets return it
						// to our render()
	}

	public static int[][] empty2DArray(int num, int row, int column) {
		int[][] array = new int[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				array[i][j] = num;
			}
		}
		return array;
	}
	
	public enum Direction{
		
		LEFT(-1,0),
		RIGHT(1,0),
		UP(0,1),
		DOWN(0,-1),
		STOPPING(0,0);
		
		private int row, column;
		
		private Direction(int row, int column) {
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}
		
		public static Direction getRandom(int random) {
			return values()[random];
		}	

	}

	@Override
	public void populate(int x, int z) {

	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int chunkX, int chunkZ) {
		

		return false;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		// If you want normal creatures appropriate for this biome then uncomment the
		// following two lines:
		if (pos.getY() < 70) {
			Biome biome = this.worldObj.getBiome(pos);
			return biome.getSpawnableList(creatureType);
		}
		return ImmutableList.of();

//		if (creatureType == EnumCreatureType.MONSTER) {
//			return mobs;
//		}
	

	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		return null;
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {

	}
}
