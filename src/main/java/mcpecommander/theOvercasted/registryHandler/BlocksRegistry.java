package mcpecommander.theOvercasted.registryHandler;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.block.BlockMushroomSprout;
import mcpecommander.theOvercasted.block.renderer.TileEntityItemMushroomSproutRenderer;
import mcpecommander.theOvercasted.block.renderer.TileEntityMushroomSproutRenderer;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityMushroomSprout;
import mcpecommander.theOvercasted.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BuiltInModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BlocksRegistry {

	static BlockMushroomSprout mushroom = new BlockMushroomSprout();

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {

		e.getRegistry().register(mushroom);
		ForgeRegistries.ITEMS.register(new ItemMushroom(mushroom));

	}
	
	public static class ItemMushroom extends ItemBlock{

		public ItemMushroom(Block block) {
			super(block);
			setRegistryName(mushroom.getRegistryName());
			setTileEntityItemStackRenderer(new TileEntityItemMushroomSproutRenderer());
			
		}
		
	}

	public static void registerTileEntityRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMushroomSprout.class,
				new TileEntityMushroomSproutRenderer(Reference.MODID, "mushroom_sprout", 64, 32,
						new ResourceLocation(Reference.MODID, "textures/tile/mushroom.png")));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ModBlocks.mushroomBlock.initModel();
	}

	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event) {
		event.getModelRegistry().putObject(new ModelResourceLocation(mushroom.getRegistryName(), "inventory"),
				new BuiltInModel(ItemCameraTransforms.DEFAULT, ItemOverrideList.NONE ));
	}

}

