package mcpecommander.theOvercasted.registryHandler;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.block.BlockPoop;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModelRegistry {

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent e) {
		for (Block b : Registry.BLOCKS)
			if (b instanceof IHasModel)
				((IHasModel) b).registerModels();
		for (Item i : Registry.ITEMS)
			if (i instanceof IHasModel)
				((IHasModel) i).registerModels();
	}

	@SubscribeEvent
	public static void registerColor(ColorHandlerEvent.Item e) {
		e.getBlockColors().registerBlockColorHandler(new IBlockColor() {

			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				switch(state.getValue(BlockPoop.COLOR)) {
				case BLACK:
					return 3089697;
				case BROWN:
					return 6306094;
				case PINK:
					return 10569562;
				case GOLDEN:
					return 14855986;
				case WHITE:
					return 13890547;
				}
				return 3089697;
			}
		}, Registry.POOP);
		
		e.getItemColors().registerItemColorHandler(new IItemColor() {

			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				switch(stack.getItem().getMetadata(stack)) {
				case 0:
				case 5:
				case 10:
					return 10569562;//6306094;//10569562;
				case 1:
				case 6:
				case 11:
					return 3089697;//10569562;//3089697;
				case 2:
				case 7:
				case 12:
					return 14855986;//3089697;//14855986;
				case 3:
				case 8:
				case 13:
					return 6306094;//14855986;//6306094;
				case 4:
				case 9:
				case 14:
					return 13890547;
				}
				return 3089697;
			}
		}, Registry.POOP);
	}


}
