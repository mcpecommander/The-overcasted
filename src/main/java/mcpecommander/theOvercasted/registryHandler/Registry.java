package mcpecommander.theOvercasted.registryHandler;

import java.util.ArrayList;
import java.util.List;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.block.BlockBasementWood;
import mcpecommander.theOvercasted.block.BlockPedestal;
import mcpecommander.theOvercasted.block.BlockPoop;
import mcpecommander.theOvercasted.block.BlockRock;
import mcpecommander.theOvercasted.block.BlockTNT;
import mcpecommander.theOvercasted.block.BlockVase;
import mcpecommander.theOvercasted.item.ItemBlockPedestal;
import mcpecommander.theOvercasted.item.ItemBomb;
import mcpecommander.theOvercasted.item.ItemItem;
import mcpecommander.theOvercasted.item.ItemLaserBeam;
import mcpecommander.theOvercasted.item.ItemTear;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class Registry {


	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	
	public static final BlockPoop POOP = new BlockPoop();
	public static final Block BASEMENT_WOOD = new BlockBasementWood();
	public static final BlockRock ROCK = new BlockRock();
	public static final Block VASE = new BlockVase();
	public static final BlockTNT TNT = new BlockTNT();
	public static final Block PEDESTAL = new BlockPedestal();
	
	public static final Item BOMB = new ItemBomb();
	public static final Item LASER_BEAM = new ItemLaserBeam();
	public static final Item TEAR = new ItemTear();
	public static final ItemBlock ITEM_PEDESTAL = new ItemBlockPedestal();
	public static final ItemItem Item = new ItemItem();
	
	@SubscribeEvent
	public static void onBlockRegister(Register<Block> event) {
		event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onItemRegister(Register<Item> event) {
		event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}
}

