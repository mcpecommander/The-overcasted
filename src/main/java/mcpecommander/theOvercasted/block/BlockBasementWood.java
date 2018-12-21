package mcpecommander.theOvercasted.block;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityBasicFamiliar;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
//		if(worldIn.isRemote) {
////			PickupsGUI gui = new PickupsGUI();
////			Minecraft.getMinecraft().displayGuiScreen(gui);
//			return true;
//		}else {
////			IStats cap = playerIn.getCapability(StatsProvider.STATS_CAP, null);
////			if(cap == null) {
////				return false;
////			}
////			cap.setDefaults(EnumCharacter.ISAAC);
////			CommonProxy.CHANNEL.sendTo(new PacketSendStats(cap), (EntityPlayerMP) playerIn);
//			return true;
//		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}


}
