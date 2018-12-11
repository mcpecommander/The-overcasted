package mcpecommander.theOvercasted.item;

import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.item.teisr.ItemPedestalStackRenderer;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBlockPedestal extends ItemBlock implements IHasModel{

	public ItemBlockPedestal() {
		super(Registry.PEDESTAL);
		setRegistryName(Registry.PEDESTAL.getRegistryName());
		setTileEntityItemStackRenderer(new ItemPedestalStackRenderer());
		setCreativeTab(TheOvercasted.overcastedTab);
		Registry.ITEMS.add(this);
		
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

}
