package mcpecommander.theOvercasted.block;

import java.util.List;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityMushroomSprout;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMushroomSprout extends Block implements ITileEntityProvider{
	
	protected static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625d * 4d, 0d, 0.0625d * 4d, 0.0625d * 12d, 0.0625d * 8d, 0.0625d * 12d);

	public BlockMushroomSprout() {
		super(Material.ROCK);
		this.setRegistryName(Reference.ModBlocks.MUSHROOMSPROUT.getRegistryName())
				.setUnlocalizedName(Reference.ModBlocks.MUSHROOMSPROUT.getName())
				.setCreativeTab(TheOvercasted.overcastedTab);
		
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		boolean flag = super.canPlaceBlockAt(worldIn, pos);
		if(flag && (worldIn.getBlockState(pos.down()) == Blocks.MYCELIUM.getDefaultState() || worldIn.getBlockState(pos.down()) == Blocks.GRASS.getDefaultState())) {
			return true;
		}
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    public BlockRenderLayer getBlockLayer() {
    	return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMushroomSprout();
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
