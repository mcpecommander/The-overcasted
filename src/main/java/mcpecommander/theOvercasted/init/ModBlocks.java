package mcpecommander.theOvercasted.init;

import mcpecommander.theOvercasted.block.BlockMushroomSprout;
import mcpecommander.theOvercasted.block.multiBlocks.BlockTorch;
import mcpecommander.theOvercasted.block.multiBlocks.BlockTorchExt;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	@GameRegistry.ObjectHolder("overcasted:block_mushroom_sprout")
	public static BlockMushroomSprout mushroomBlock;
	@GameRegistry.ObjectHolder("overcasted:block_torch")
	public static BlockTorch torchBlock;
	@GameRegistry.ObjectHolder("overcasted:block_torch_ext")
	public static BlockTorchExt torchBlockExt;

}
