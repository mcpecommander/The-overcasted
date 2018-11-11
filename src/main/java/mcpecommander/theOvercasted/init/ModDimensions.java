package mcpecommander.theOvercasted.init;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.maze.TestWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
	
	public static DimensionType testDimensionType;

    public static void init() {
        registerDimensionTypes();
        registerDimensions();
    }

    private static void registerDimensionTypes() {
        testDimensionType = DimensionType.register(Reference.MODID, "_test", 100, TestWorldProvider.class, false);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(100, testDimensionType);
    }

}
