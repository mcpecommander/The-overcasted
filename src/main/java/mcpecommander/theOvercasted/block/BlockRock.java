package mcpecommander.theOvercasted.block;

import java.util.Random;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.capability.pickups.IPickups;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.proxy.CommonProxy;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import mcpecommander.theOvercasted.util.RayTracedExplosion;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRock extends Block implements IHasModel{
	
	public static final PropertyEnum<BlockRock.EnumType> TYPE = PropertyEnum.<BlockRock.EnumType>create("type",
			BlockRock.EnumType.class);
	public static final PropertyBool READY = BlockTNT.READY;

	public BlockRock() {
		super(Material.ROCK, MapColor.GRAY_STAINED_HARDENED_CLAY);
		setRegistryName(Reference.ModBlocks.ROCK.getRegistryName());
		setUnlocalizedName(Reference.ModBlocks.ROCK.getName());
		setCreativeTab(TheOvercasted.overcastedTab);
		setBlockUnbreakable();
		setResistance(10f);
		Registry.BLOCKS.add(this);
		Registry.ITEMS.add(new ItemBlock(this) {
			public int getMetadata(int damage) {
				return damage;
			};
		}.setRegistryName(getRegistryName()).setHasSubtypes(true));
		
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return new AxisAlignedBB(0d, 0d, 0d, 1d, 2d, 1d);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0d, 0d, 0d, 1d, 13d/16d, 1d);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
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
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}

	@Override
	public void registerModels() {
		for(int x = 0; x < 3; x++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), x,
					new ModelResourceLocation(getRegistryName() + "_" + x, "inventory"));
		}
		
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) {
			return true;
		}else {
			IPickups cap = playerIn.getCapability(PickupsProvider.PICKUPS, null);
			if(cap == null) {
				return false;
			}
			cap.setBombs(0).setCoins(0).setKeys(0);
			BlockPos pickups = new BlockPos(cap.getBombs(), cap.getCoins(), cap.getKeys());
			CommonProxy.CHANNEL.sendTo(new PacketSendVec3i(pickups), (EntityPlayerMP) playerIn);
			return true;
		}
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		boolean flag = super.removedByPlayer(state, world, pos, player, willHarvest);
		if (flag && state.getValue(TYPE) == EnumType.BOMB) {
			RayTracedExplosion explosion = new RayTracedExplosion(pos, world, 3);
			explosion.causer = player;
			explosion.doExplosionA();
			explosion.doExplosionB();
		}
		return flag;
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		// this should not tick unless forced by a scheduled update and even then, it
		// should not explode unless it was set to ready.
		if (state.getValue(TYPE) == EnumType.BOMB && worldIn.getBlockState(pos).getBlock() == this) {
			if (state.getValue(READY)) {
				worldIn.setBlockToAir(pos);
				RayTracedExplosion explosion = new RayTracedExplosion(pos, worldIn, 3);
				explosion.doExplosionA();
				explosion.doExplosionB();

			}
		}
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0));
		items.add(new ItemStack(this, 1, 1));
		items.add(new ItemStack(this, 1, 2));
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	/**
     * Convert the BlockState into the correct metadata value
     */
	@Override
	public int getMetaFromState(IBlockState state)
    {
		switch(state.getValue(TYPE)) {
		case NORMAL: 
			return state.getValue(READY) ? 3 : 0;
		case MARKED:
			return state.getValue(READY) ? 4 : 1;
		case BOMB:
			return state.getValue(READY) ? 5 : 2;
		}
		return 0;
    }
	
	/**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
		switch(meta) {
		case 0:
		case 3:
			return this.getDefaultState().withProperty(TYPE, EnumType.NORMAL).withProperty(READY, meta == 0 ? false: true);
		case 1:
		case 4:
			return this.getDefaultState().withProperty(TYPE, EnumType.MARKED).withProperty(READY, meta == 1 ? false: true);
		case 2:
		case 5:
			return this.getDefaultState().withProperty(TYPE, EnumType.BOMB).withProperty(READY, meta == 2 ? false: true);
		}
		return this.getDefaultState();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {TYPE, READY});
	}
	
	public static enum EnumType implements IStringSerializable {
		NORMAL, MARKED, BOMB;
		
		@Override
		public String toString() {
			return this.getName();
		}

		@Override
		public String getName() {
			if (this == NORMAL) {
				return "normal";
			} else if (this == MARKED) {
				return "marked";
			}
			return "bomb";
		}
	}
	

}
