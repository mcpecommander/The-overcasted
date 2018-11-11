package mcpecommander.theOvercasted.proxy;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.animationSystem.AnimationRegistryHelper;
import mcpecommander.theOvercasted.init.ModAnimations;
import mcpecommander.theOvercasted.init.ModEntities;
import mcpecommander.theOvercasted.particle.TextureStitcher;
import mcpecommander.theOvercasted.registryHandler.BlocksRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		ModEntities.initModel();
		OBJLoader.INSTANCE.addDomain(Reference.MODID);
		AnimationRegistryHelper.init();
		MinecraftForge.EVENT_BUS.register(new TextureStitcher());
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		BlocksRegistry.registerTileEntityRenderer();
		ModAnimations.init();
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	

}
