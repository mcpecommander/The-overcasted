package mcpecommander.theOvercasted.block;

import java.util.List;
import java.util.Random;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.particle.StinkyParticle;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPoop extends Block implements IHasModel {

	public static final PropertyEnum<BlockPoop.EnumColor> COLOR = PropertyEnum.<BlockPoop.EnumColor>create("color",
			BlockPoop.EnumColor.class);
	public static final PropertyEnum<BlockPoop.EnumPhase> PHASE = PropertyEnum.<BlockPoop.EnumPhase>create("phase",
			BlockPoop.EnumPhase.class);
	private static final AxisAlignedBB[] BOUNDING = new AxisAlignedBB[] {};

	public BlockPoop() {
		super(Material.CLAY);
		setRegistryName(Reference.ModBlocks.POOP.getRegistryName());
		setUnlocalizedName(Reference.ModBlocks.POOP.getName());
		setCreativeTab(TheOvercasted.overcastedTab);
		setBlockUnbreakable();
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
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		switch(state.getValue(COLOR)) {
		case GOLDEN:
			return MapColor.GOLD;
		case WHITE:
			return MapColor.WHITE_STAINED_HARDENED_CLAY;
		case PINK:
			return MapColor.PINK;
		case BLACK:
			return MapColor.BLACK;
		case BROWN:
			return MapColor.BROWN;
		}
		return super.getMapColor(state, worldIn, pos);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(1d/16d, 0d, 1d/16d, 15d/16d, 12d/16d, 15/16d);
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(rand.nextBoolean()) {
			double x = MathHelper.clamp(rand.nextDouble(), 0.4d, 0.7d);
			double z = MathHelper.clamp(rand.nextDouble(), 0.4d, 0.7d);
			int i = Minecraft.getMinecraft().getBlockColors().colorMultiplier(stateIn, worldIn, pos, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(StinkyParticle.createParticle(worldIn,
					pos.getX() + x, pos.getY() + 0.4d, pos.getZ() + z, i));
		}	
		
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if(state.getValue(COLOR) == EnumColor.WHITE) {
			return 5;
		}
		return super.getLightValue(state, world, pos);
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		for(int i = 0; i < 15;i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i,
					new ModelResourceLocation(getRegistryName(), "inventory"));
		}
		
	}
	
	//TODO: Added floor variants to limit the colors of poop per floor.
	public IBlockState getAllowedStates() {
		return this.getDefaultState().withProperty(COLOR, EnumColor.values()[RANDOM.nextInt(5)]);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0));
		items.add(new ItemStack(this, 1, 1));
		items.add(new ItemStack(this, 1, 2));
		items.add(new ItemStack(this, 1, 3));
		items.add(new ItemStack(this, 1, 4));
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch(meta) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			return this.getDefaultState().withProperty(PHASE, EnumPhase.FULL).withProperty(COLOR, EnumColor.values()[meta]);
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return this.getDefaultState().withProperty(PHASE, EnumPhase.HALF).withProperty(COLOR, EnumColor.values()[meta - 5]);
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			return this.getDefaultState().withProperty(PHASE, EnumPhase.HALF).withProperty(COLOR, EnumColor.values()[meta - 10]);
		default:
			return null;
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		int meta = 0;
		switch (state.getValue(COLOR)) {
		case PINK:
			break;
		case BLACK:
			meta = 1;
			break;
		case GOLDEN:
			meta = 2;
			break;
		case BROWN:
			meta = 3;
			break;
		case WHITE:
			meta = 4;
			break;
		}
		switch (state.getValue(PHASE)) {
		case FULL:
			break;
		case HALF:
			meta += 5;
			break;
		case QUARTER:
			meta += 10;
		}
		return meta;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { COLOR, PHASE });
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	public static enum EnumColor implements IStringSerializable {
		PINK, BLACK, GOLDEN, BROWN, WHITE;

		@Override
		public String toString() {
			return this.getName();
		}

		@Override
		public String getName() {
			if (this == BROWN) {
				return "brown";
			} else if (this == PINK) {
				return "pink";
			} else if (this == BLACK) {
				return "black";
			} else if (this == WHITE) {
				return "white";
			}
			return "golden";
		}
	}

	public static enum EnumPhase implements IStringSerializable {
		FULL, HALF, QUARTER;

		@Override
		public String toString() {
			return this.getName();
		}

		@Override
		public String getName() {
			if (this == FULL) {
				return "full";
			} else if (this == HALF) {
				return "half";
			}
			return "quarter";
		}

	}

}
