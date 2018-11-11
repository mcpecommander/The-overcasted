package mcpecommander.theOvercasted.registryHandler;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.block.BlockMushroomSprout;
import mcpecommander.theOvercasted.block.multiBlocks.BlockTorch;
import mcpecommander.theOvercasted.block.multiBlocks.BlockTorchExt;
import mcpecommander.theOvercasted.block.renderer.TileEntityItemMushroomSproutRenderer;
import mcpecommander.theOvercasted.block.renderer.TileEntityMushroomSproutRenderer;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityMushroomSprout;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BuiltInModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BlocksRegistry {

	private static BlockMushroomSprout mushroom = new BlockMushroomSprout();
	private static BlockTorch torch = new BlockTorch();
	private static BlockTorchExt torch_ext = new BlockTorchExt();
	private static ItemMushroom item_mushroom;
	private static Item item_torch;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		e.getRegistry().registerAll(mushroom, torch, torch_ext);

	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		item_mushroom = new ItemMushroom(mushroom);
		item_torch = new ItemBlock(torch).setRegistryName(torch.getRegistryName());
		e.getRegistry().registerAll(item_mushroom, item_torch);
	}
	
	public static class ItemMushroom extends ItemBlock{

		public ItemMushroom(Block block) {
			super(block);
			setRegistryName(mushroom.getRegistryName());
			setTileEntityItemStackRenderer(new TileEntityItemMushroomSproutRenderer());
			
			
		}
		
		@Override
		public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
			IBlockState state = world.getBlockState(pos);
			if(state != null && state.getBlock() instanceof BlockDoor) {
				System.out.println(state.getProperties());
			}
			return super.canDestroyBlockInCreative(world, pos, stack, player);
		}
		
	}

	public static void registerTileEntityRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMushroomSprout.class,
				new TileEntityMushroomSproutRenderer(Reference.MODID, "mushroom_sprout", 64, 32,
						new ResourceLocation(Reference.MODID, "textures/tile/mushroom.png")));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(item_mushroom, 0, new ModelResourceLocation(item_mushroom.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item_torch, 0, new ModelResourceLocation(item_torch.getRegistryName(), "inventory"));
	}

	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event) {
		event.getModelRegistry().putObject(new ModelResourceLocation(mushroom.getRegistryName(), "inventory"),
				new BuiltInModel(ItemCameraTransforms.DEFAULT, ItemOverrideList.NONE ));
	}

}

