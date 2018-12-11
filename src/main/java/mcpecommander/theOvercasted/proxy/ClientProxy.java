package mcpecommander.theOvercasted.proxy;

import org.lwjgl.input.Keyboard;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.animationSystem.AnimationRegistryHelper;
import mcpecommander.theOvercasted.init.ModAnimations;
import mcpecommander.theOvercasted.init.ModEntities;
import mcpecommander.theOvercasted.particle.TextureStitcher;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	public static KeyBinding yKey;

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		ModEntities.initModel();
		AnimationRegistryHelper.init();
		MinecraftForge.EVENT_BUS.register(new TextureStitcher());
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		ModAnimations.init();
	    yKey = new KeyBinding("key.tutorial", Keyboard.KEY_Y, "key.categories." + Reference.MODID);
	    ClientRegistry.registerKeyBinding(yKey);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	

}
