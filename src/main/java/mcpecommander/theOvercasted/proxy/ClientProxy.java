package mcpecommander.theOvercasted.proxy;

import mcpecommander.theOvercasted.init.ModEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		ModEntities.initModel();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	

}