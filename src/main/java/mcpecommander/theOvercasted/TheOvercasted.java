package mcpecommander.theOvercasted;

import mcpecommander.theOvercasted.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, 
	acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSIONS, useMetadata = true, certificateFingerprint = "4fe096bf3ddb2da90a69bb82abd57398549af8a5")

public class TheOvercasted {

	@Mod.Instance
	public static TheOvercasted instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS, modId = Reference.MODID)
	public static CommonProxy proxy;
	
	public static TheOvercastedTab overcastedTab = new TheOvercastedTab();
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);

	}

}
