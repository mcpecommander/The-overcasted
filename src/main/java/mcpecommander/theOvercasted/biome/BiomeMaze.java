package mcpecommander.theOvercasted.biome;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class BiomeMaze extends Biome {

	public BiomeMaze() {
		super(new BiomeProperties(Reference.MODID + ":maze").setRainDisabled());
		this.setRegistryName(new ResourceLocation(Reference.MODID, "maze"));
		this.spawnableMonsterList.clear();
	}
	
	@Override
	public boolean ignorePlayerSpawnSuitability() {
		return true;
	}

}
