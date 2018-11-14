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
	public DungeonGenerator chunksRequired;

	private List<Biome.SpawnListEntry> mobs = Lists
			.newArrayList(new Biome.SpawnListEntry(EntityFly.class, 100, 2, 2));

	private NormalTerrainGenerator terraingen = new NormalTerrainGenerator();

	public TerrianGen(World worldObj) {
		this.worldObj = worldObj;
		long seed = worldObj.getSeed();
		this.random = new Random();
		chunksRequired = DungeonGenerator.createDungeon(6, 13, 13, 10, 65);
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		
		
		ChunkPrimer chunkprimer = new ChunkPrimer();
		if(x < 13 && z < 13 &&  x > -1 && z > -1 && chunksRequired.getLayout()[x][z] != 1) {
			terraingen.generate(x, z, chunkprimer, chunksRequired);
		}

		Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

		chunk.generateSkylightMap();
		return chunk;
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
