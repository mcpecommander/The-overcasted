package mcpecommander.theOvercasted.proxy;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityPedestal;
import mcpecommander.theOvercasted.capability.pickups.IPickups;
import mcpecommander.theOvercasted.capability.pickups.Pickups;
import mcpecommander.theOvercasted.capability.pickups.PickupsStorage;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.Stats;
import mcpecommander.theOvercasted.capability.stats.StatsStorage;
import mcpecommander.theOvercasted.gui.GuiProxy;
import mcpecommander.theOvercasted.init.ModDimensions;
import mcpecommander.theOvercasted.networking.PacketSendStats;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	
	static int ID = 0;
	public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	public void preInit(FMLPreInitializationEvent e) {
		GameRegistry.registerTileEntity(TileEntityPedestal.class, new ResourceLocation(Reference.MODID, "tile_entity_pedestal"));
		ModDimensions.init();
		CapabilityManager.INSTANCE.register(IPickups.class, new PickupsStorage(), new Pickups.Factory());
		CapabilityManager.INSTANCE.register(IStats.class, new StatsStorage(), new Stats.Factory());
		CHANNEL.registerMessage(PacketSendVec3i.Handler.class, PacketSendVec3i.class, ID++, Side.CLIENT);
		CHANNEL.registerMessage(PacketSendStats.Handler.class, PacketSendStats.class, ID++, Side.CLIENT);
	}
	
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(TheOvercasted.instance, new GuiProxy());
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		
		
	}

}
