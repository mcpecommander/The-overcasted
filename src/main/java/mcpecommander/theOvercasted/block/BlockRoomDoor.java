package mcpecommander.theOvercasted.block;

import java.util.Random;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockRoomDoor extends BlockDoor implements IHasModel{

	public BlockRoomDoor() {
		super(Material.WOOD);
		this.setRegistryName(Reference.ModBlocks.ROOM_DOOR.getRegistryName());
		this.setUnlocalizedName(Reference.ModBlocks.ROOM_DOOR.getName());
		this.setCreativeTab(TheOvercasted.overcastedTab);
		this.setBlockUnbreakable();
		setResistance(6000000.0F);
		Registry.BLOCKS.add(this);
		//This absolutely the worst way to implement an item but the code is literally copied from vanilla so fuck it.
		Registry.ITEMS.add(new ItemBlock(this) {
			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
		    {
		        if (facing != EnumFacing.UP)
		        {
		            return EnumActionResult.FAIL;
		        }
		        else
		        {
		            IBlockState iblockstate = worldIn.getBlockState(pos);
		            Block block = iblockstate.getBlock();

		            if (!block.isReplaceable(worldIn, pos))
		            {
		                pos = pos.offset(facing);
		            }

		            ItemStack itemstack = player.getHeldItem(hand);

		            if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos))
		            {
		                EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
		                int i = enumfacing.getFrontOffsetX();
		                int j = enumfacing.getFrontOffsetZ();
		                boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
		                this.placeDoor(worldIn, pos, enumfacing, this.block, flag);
		                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
		                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
		                itemstack.shrink(1);
		                return EnumActionResult.SUCCESS;
		            }
		            else
		            {
		                return EnumActionResult.FAIL;
		            }
		        }
		    }

		    public void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge)
		    {
		        BlockPos blockpos = pos.offset(facing.rotateY());
		        BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
		        int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
		        int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
		        boolean flag = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
		        boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up()).getBlock() == door;

		        if ((!flag || flag1) && j <= i)
		        {
		            if (flag1 && !flag || j < i)
		            {
		                isRightHinge = false;
		            }
		        }
		        else
		        {
		            isRightHinge = true;
		        }

		        BlockPos blockpos2 = pos.up();
		        boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);
		        IBlockState iblockstate = door.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, Boolean.valueOf(flag2)).withProperty(BlockDoor.OPEN, Boolean.valueOf(flag2));
		        worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
		        worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
		        worldIn.notifyNeighborsOfStateChange(pos, door, false);
		        worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
		    }
		}.setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		IStateMapper custom_mapper = (new StateMap.Builder()).ignore(POWERED).build();
		ModelLoader.setCustomStateMapper(this, custom_mapper);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}

	//If we are going to toggle the door, check if a player is standing inside it and push him into the room.
	@Override
	public void toggleDoor(World world, BlockPos pos, boolean open) {
		if (world.getBlockState(pos).getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
			for(EntityPlayer player : world.playerEntities) {
				if(player.getPosition().equals(pos)) {
					if (!world.isRemote) {
						((EntityPlayerMP) player).connection.setPlayerLocation(player.posX + player.getLookVec().x * 2,
						player.posY, player.posZ + player.getLookVec().z * 2, player.rotationYaw, player.rotationPitch);
					}
				}
			}
		}
		super.toggleDoor(world, pos, open);
	}
	
	//Removed the redstone functionality.
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            BlockPos blockpos = pos.down();
            IBlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
            else if (blockIn != this)
            {
                iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
            }
        }
        else
        {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

            if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn,  pos.down(), EnumFacing.UP))
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;

                if (iblockstate1.getBlock() == this)
                {
                    worldIn.setBlockToAir(blockpos1);
                }
            }

            if (flag1)
            {
                if (!worldIn.isRemote)
                {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
        }
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : Item.getItemFromBlock(this);
    }

}
