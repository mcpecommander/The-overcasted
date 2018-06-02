package mcpecommander.theOvercasted.registryHandler;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.block.BlockMushroomSprout;
import mcpecommander.theOvercasted.block.renderer.TileEntityMushroomSproutRenderer;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityMushroomSprout;
import mcpecommander.theOvercasted.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BlocksRegistry {
	
	
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		BlockMushroomSprout mushroom = new BlockMushroomSprout();
		e.getRegistry().register(mushroom);
		ForgeRegistries.ITEMS.register(new ItemBlock(mushroom).setRegistryName(mushroom.getRegistryName()));
		
	}
	
//	@SubscribeEvent
//	public static void registerItemBlocks(RegistryEvent.Register<Item> e) {
//		e.getRegistry().register(new ItemBlock(mushroom));
//	}
	
	public static void registerTileEntityRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMushroomSprout.class, new TileEntityMushroomSproutRenderer(Reference.MODID, "mushroom_sprout", 64,
                32, new ResourceLocation(Reference.MODID, "textures/tile/mushroom.png")));
	}
	
	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.mushroomBlock.initModel();
    }

}
