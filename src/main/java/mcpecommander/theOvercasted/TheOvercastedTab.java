package mcpecommander.theOvercasted;

import mcpecommander.theOvercasted.registryHandler.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TheOvercastedTab extends CreativeTabs{

	public TheOvercastedTab() {
		super("tabTheOvercasted");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Registry.BOMB);
	}

}
