package mcpecommander.theOvercasted.init;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.maze.DungeonWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
	
	public static DimensionType dungeonDimensionType;

    public static void init() {
        registerDimensionTypes();
        registerDimensions();
    }

    private static void registerDimensionTypes() {
        dungeonDimensionType = DimensionType.register(Reference.MODID, "_test", 100, DungeonWorldProvider.class, false);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(100, dungeonDimensionType);
    }

}
