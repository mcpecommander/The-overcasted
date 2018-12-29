package mcpecommander.theOvercasted.maze;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mcpecommander.theOvercasted.entity.entities.EntityOverseer;
import mcpecommander.theOvercasted.events.DimensionEvents;
import mcpecommander.theOvercasted.init.ModDimensions;
import mcpecommander.theOvercasted.maze.DungeonGenerator.EnumDungeonType;
import mcpecommander.theOvercasted.registryHandler.BiomeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

public class DungeonWorldProvider extends WorldProvider {
	
	private DungeonGenerator dungeon;
	public final Set<EntityOverseer> overseers = new HashSet<>();
	
	@Override
	protected void init() {
		this.hasSkyLight = true;
		this.biomeProvider = new BiomeProviderSingle(BiomeRegistry.biomeMaze);
		NBTTagCompound tag = this.world.getWorldInfo().getDimensionData(100);
		if (!this.world.isRemote) {
			if (tag.hasKey("tunnels", 3)) {
				dungeon = DungeonGenerator.fromNBT(tag);
			} else {
				dungeon = DungeonGenerator.createDungeon(6, 13, 13, 10, 65, EnumDungeonType.BASEMENT);
			}
			this.setSpawnPoint(DimensionEvents.spawnPos);
		}
		
		
	}
	
	@Override
	public void onWorldSave() {
		NBTTagCompound nbttagcompound = this.world.getWorldInfo().getDimensionData(100);
		if(nbttagcompound == null) {
			nbttagcompound = new NBTTagCompound();
		}
		toNBT(nbttagcompound, dungeon);
		this.world.getWorldInfo().setDimensionData(100, nbttagcompound);

	}
	
	private NBTTagCompound toNBT(NBTTagCompound nbttagcompound, DungeonGenerator dungeon) {
		for(int x = 0; x < dungeon.getLayout().length; x++) {
			nbttagcompound.setIntArray("dungeon" + x, dungeon.getLayout()[x]);
		}
		nbttagcompound.setInteger("length", dungeon.getMaxLength());
		nbttagcompound.setInteger("rows", dungeon.getMaxRows());
		nbttagcompound.setInteger("columns", dungeon.getMaxColumns());
		nbttagcompound.setInteger("tunnels", dungeon.getMaxTunnels());
		nbttagcompound.setInteger("x", dungeon.getSpawnPos().getX());
		nbttagcompound.setInteger("y", dungeon.getSpawnPos().getY());
		nbttagcompound.setInteger("z", dungeon.getSpawnPos().getZ());
		return nbttagcompound;
	}

	
	@Override
	public boolean canMineBlock(EntityPlayer player, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	@Override
	public WorldSleepResult canSleepAt(EntityPlayer player, BlockPos pos) {
		return WorldSleepResult.DENY;
	}
	
	@Override
	public WorldBorder createWorldBorder() {
		WorldBorder border = new WorldBorder();
		border.setCenter(12 * 8, 12 * 8);
		border.setSize(12*8);
		return border;
	}

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.dungeonDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "Dungeon_World";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new DungeonTerrianGen(world);
    }

	public DungeonGenerator getDungeon() {
		return dungeon;
	}
}