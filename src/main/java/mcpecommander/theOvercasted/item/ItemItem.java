package mcpecommander.theOvercasted.item;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemItem extends Item implements IHasModel{
	
	public ItemItem() {
		setRegistryName(Reference.ModItems.ITEM.getRegistryName());
		setUnlocalizedName(Reference.ModItems.ITEM.getName());
		setCreativeTab(TheOvercasted.overcastedTab);
		setMaxStackSize(1);
//		setMaxDamage(16);
		setHasSubtypes(true);
		Registry.ITEMS.add(this);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == TheOvercasted.overcastedTab) {
			for (int i = 0; i < 16; i++)
				items.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public void registerModels() {
		for (int i = 0; i < 16; i++)
			ModelLoader.setCustomModelResourceLocation(this, i,
					new ModelResourceLocation(getRegistryName(), "meta=" + i));
	}
	
	

}
