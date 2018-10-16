package mcpecommander.theOvercasted.block.multiBlocks;

import java.util.List;
import java.util.Random;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorch extends Block{

	protected static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625d * 4d, 0d, 0.0625d * 4d, 0.0625d * 12d,
			0.0625d * 24d, 0.0625d * 12d);
	
	public BlockTorch() {
		super(Material.CIRCUITS);
		this.setRegistryName(Reference.ModBlocks.TORCH.getRegistryName());
		this.setUnlocalizedName(Reference.ModBlocks.TORCH.getName());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)pos.getX() + 0.25D;
        double d1 = (double)pos.getY() + 1.4D;
        double d2 = (double)pos.getZ() + 0.25D;

        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D , d1 + 0.22D, d2 + 0.27D , 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.27D , d1 + 0.22D, d2 + 0.27D , 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D , d1 + 0.22D, d2 + 0.27D , 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.2D , d1 + 0.22D, d2 + 0.22D , 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D , d1 + 0.22D, d2 + 0.27D , 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.35D , d1 + 0.22D, d2 + 0.35D , 0.0D, 0.0D, 0.0D);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
    		ItemStack stack) {
    	if(!worldIn.isRemote)
    	worldIn.setBlockState(pos.up(), ModBlocks.torchBlockExt.getDefaultState());
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    	if(!worldIn.isRemote) {
    		if(!canBlockStay(worldIn, pos)) {
    			dropBlockAsItemWithChance(worldIn, pos, state, 0.1f, 0);
    			worldIn.setBlockToAir(pos);
    		}
    	}
    }
    
    
	
	public boolean canBlockStay(World world, BlockPos pos){
		return world.getBlockState(pos.up()) == ModBlocks.torchBlockExt.getDefaultState();
	}
    
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    	if (pos.getY() >= worldIn.getHeight() - 1)
        {
            return false;
        }
    	return super.canPlaceBlockAt(worldIn, pos) && ModBlocks.torchBlockExt.canPlaceBlockAt(worldIn, pos.up());
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
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

}
