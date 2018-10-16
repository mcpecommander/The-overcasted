package mcpecommander.theOvercasted.block.multiBlocks;

import java.util.List;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorchExt extends Block{
	protected static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625d * 4d, -0.0625d * 16d, 0.0625d * 4d, 0.0625d * 12d,
			0.0625d * 8d, 0.0625d * 12d);

	public BlockTorchExt() {
		super(Material.CIRCUITS);
		this.setRegistryName(Reference.ModBlocks.TORCH_EXT.getRegistryName());
		this.setUnlocalizedName(Reference.ModBlocks.TORCH_EXT.getName());
	}
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    	if(!worldIn.isRemote) {
    		if(!canBlockStay(worldIn, pos)) {
    			worldIn.setBlockToAir(pos);
    		}
    	}
    }
	
	public boolean canBlockStay(World world, BlockPos pos){
		return world.getBlockState(pos.down()) == ModBlocks.torchBlock.getDefaultState();
	}
	
	@Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
	}

}
