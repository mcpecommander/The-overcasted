package mcpecommander.theOvercasted.registryHandler;

import java.util.ArrayList;
import java.util.List;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.block.BlockBasementWood;
import mcpecommander.theOvercasted.block.BlockPoop;
import mcpecommander.theOvercasted.block.BlockRock;
import mcpecommander.theOvercasted.block.BlockVase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class Registry {


	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	
	public static final BlockPoop POOP = new BlockPoop();
	public static final Block BASEMENT_WOOD = new BlockBasementWood();
	public static final Block ROCK = new BlockRock();
	public static final Block VASE = new BlockVase();
	
	@SubscribeEvent
	public static void onBlockRegister(Register<Block> event) {
		event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onItemRegister(Register<Item> event) {
		event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}
}

