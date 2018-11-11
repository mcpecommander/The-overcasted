package mcpecommander.theOvercasted.maze;

import mcpecommander.theOvercasted.init.ModDimensions;
import mcpecommander.theOvercasted.registryHandler.BiomeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.IChunkGenerator;

public class TestWorldProvider extends WorldProvider {
	
	@Override
	protected void init() {
		this.hasSkyLight = true;
		this.biomeProvider = new BiomeProviderSingle(BiomeRegistry.biomeMaze);
		
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
		border.setSize(13*16);
		return super.createWorldBorder();
	}

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.testDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "TEST";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new TerrianGen(world);
    }
    
//    @Override
//    public int getRespawnDimension(EntityPlayerMP player) {
//    	return 100;
//    }
}