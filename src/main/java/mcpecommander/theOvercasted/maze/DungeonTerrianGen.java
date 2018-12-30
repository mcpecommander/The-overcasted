package mcpecommander.theOvercasted.maze;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import mcpecommander.theOvercasted.entity.entities.flies.EntityFly;
import mcpecommander.theOvercasted.init.ModRoomLayouts;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class DungeonTerrianGen implements IChunkGenerator {

	private final World worldObj;
	private static Random random;
	private Biome[] biomesForGeneration;
	public DungeonGenerator chunksRequired;

	private List<Biome.SpawnListEntry> mobs = Lists
			.newArrayList(new Biome.SpawnListEntry(EntityFly.class, 100, 2, 2));

	private NormalDungeonTerrainGenerator terraingen = new NormalDungeonTerrainGenerator();
	private DungeonPopulater populater = new DungeonPopulater();
	private DungeonDecorator decorator = new DungeonDecorator();

	public DungeonTerrianGen(World worldObj) {
		this.worldObj = worldObj;
		this.random = new Random();
		chunksRequired = ((DungeonWorldProvider) worldObj.provider).getDungeon();
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		
		ChunkPrimer chunkprimer = new ChunkPrimer();
		if(x < chunksRequired.getMaxRows() && z < chunksRequired.getMaxColumns() &&  x > -1 && z > -1 && chunksRequired.getLayout()[x][z] != 0) {
			terraingen.generate(x, z, chunkprimer, chunksRequired);
		}

		Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

		chunk.generateSkylightMap();
		return chunk;
	}
	
	

	@Override
	public void populate(int x, int z) {
		if (x < chunksRequired.getMaxRows() && z < chunksRequired.getMaxColumns() && x > -1 && z > -1
				&& chunksRequired.getLayout()[x][z] != 0) {
			if (chunksRequired.getxChunkSpawn() == x && chunksRequired.getzChunkSpawn() == z)
				return;

			int layout = chunksRequired.random.nextInt(ModRoomLayouts.basement_normal_layouts.getKeys().size());
			decorator.decorate(x, z, worldObj, chunksRequired, layout);
			populater.popluate(x, z, worldObj, chunksRequired, layout);
		}
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int chunkX, int chunkZ) {
		

		return false;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {

		return ImmutableList.of();
	

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
