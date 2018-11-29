package mcpecommander.theOvercasted.proxy;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.init.ModDimensions;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.pickupsSystem.IPickups;
import mcpecommander.theOvercasted.pickupsSystem.Pickups;
import mcpecommander.theOvercasted.pickupsSystem.PickupsStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	
	static int ID = 0;
	public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	public void preInit(FMLPreInitializationEvent e) {
		ModDimensions.init();
		CapabilityManager.INSTANCE.register(IPickups.class, new PickupsStorage(), new Pickups.Factory());
		CHANNEL.registerMessage(PacketSendVec3i.Handler.class, PacketSendVec3i.class, ID++, Side.CLIENT);
	}
	
	public void init(FMLInitializationEvent e) {
		
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		
		
	}

}
