package mcpecommander.theOvercasted.block;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBasementWood extends Block implements IHasModel{

	public BlockBasementWood() {
		super(Material.CLAY, MapColor.SAND);
		setRegistryName(Reference.ModBlocks.BASEMENT_WOOD.getRegistryName());
		setUnlocalizedName(Reference.ModBlocks.BASEMENT_WOOD.getName());
		setCreativeTab(TheOvercasted.overcastedTab);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		Registry.BLOCKS.add(this);
		Registry.ITEMS.add(new ItemBlock(this).setRegistryName(getRegistryName()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}


}
