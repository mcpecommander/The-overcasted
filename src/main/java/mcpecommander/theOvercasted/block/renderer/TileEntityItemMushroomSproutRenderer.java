package mcpecommander.theOvercasted.block.renderer;

import mcpecommander.theOvercasted.block.tileEntity.TileEntityMushroomSprout;
import mcpecommander.theOvercasted.init.ModBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TileEntityItemMushroomSproutRenderer extends TileEntityItemStackRenderer {
	
	private final TileEntityMushroomSprout mushroom = new TileEntityMushroomSprout();
	
	@Override
	public void renderByItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if(item == ItemBlock.getItemFromBlock(ModBlocks.mushroomBlock)) {
			TileEntityRendererDispatcher.instance.render(this.mushroom, 0.0D, 0.4D, 0.0D, 0.0F, 1);
			if(itemStack.getCount() > 1) {
				TileEntityRendererDispatcher.instance.render(this.mushroom, 0.1D, 0.45D, -0.1D, 0.0F, 1);
				if(itemStack.getCount() > 2) {
					TileEntityRendererDispatcher.instance.render(this.mushroom, -0.1D, 0.35D, -0.1D, 0.0F, 1);
				}
			}
		}
	}

}
