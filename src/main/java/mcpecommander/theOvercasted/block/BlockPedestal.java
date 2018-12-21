package mcpecommander.theOvercasted.block;

import java.util.Arrays;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.block.tesr.TileEntityPedestalRenderer;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityPedestal;
import mcpecommander.theOvercasted.item.ItemItem;
import mcpecommander.theOvercasted.item.ItemTear;
import mcpecommander.theOvercasted.item.effects.Attribute;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPedestal extends Block implements IHasModel, ITileEntityProvider {
	
	private static final AxisAlignedBB PEDESTAL_BOX = Block.FULL_BLOCK_AABB.contract(0.03d, 6d/16d, 0.03d).contract(-0.03d, 0, -0.03d);

	public BlockPedestal() {
		super(Material.ROCK, MapColor.GRAY);
		setRegistryName(Reference.ModBlocks.PEDESTAL.getRegistryName());
		setUnlocalizedName(Reference.ModBlocks.PEDESTAL.getName());
		setCreativeTab(TheOvercasted.overcastedTab);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		Registry.BLOCKS.add(this);
		
	}

	@Override
	public void registerModels() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TileEntityPedestalRenderer());

	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return PEDESTAL_BOX;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return PEDESTAL_BOX;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null && tile instanceof TileEntityPedestal) {
				TileEntityPedestal pedestal = (TileEntityPedestal) tile; 
				if(pedestal.getItemStack().isEmpty()) {
					return;
				}else if(pedestal.getItemStack().getItem() instanceof ItemItem) {
					ItemStack upgrade = pedestal.getItemStack();
					EntityPlayer player = (EntityPlayer) entityIn;
					boolean hasTear = false;
					ItemStack tear = ItemStack.EMPTY;
					for(ItemStack stack : player.inventory.mainInventory) {
						if(stack.getItem() instanceof ItemTear) {
							hasTear = true;
							tear = stack;
							break;
						}
					}
					if(hasTear) {
						NBTTagCompound tag = tear.getTagCompound();
						if(tag == null || tag.getIntArray("items").length == 0) {
							tag = new NBTTagCompound();
							int[] array = new int[255];
							Arrays.fill(array, 0);
							tag.setIntArray("items", array);
						}
						int[] items = tag.getIntArray("items");
						for(int x = 0; x < items.length; x++) {
							if(items[x] == 0) {
								items[x] = upgrade.getMetadata() + 1;
								break;
							}
						}
						tag.setIntArray("items", items);
						tear.setTagCompound(tag);
						pedestal.setItemStack(ItemStack.EMPTY);
						((ItemTear) tear.getItem()).onTagChange(worldIn, (EntityPlayer) entityIn, tear, Attribute.getAttributeById(upgrade.getMetadata() + 1));
					}	
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//TODO: test code. delete.
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null && tile instanceof TileEntityPedestal) {
				ItemStack stack = playerIn.getHeldItem(hand);
				if(stack != ((TileEntityPedestal) tile).getItemStack())
				((TileEntityPedestal) tile).setItemStack(stack);
			}
		}
		return true;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPedestal();
	}

}
