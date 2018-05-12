package mcpecommander.theOvercasted.init;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityArmourStand;
import mcpecommander.theOvercasted.entity.entities.EntityLangolier;
import mcpecommander.theOvercasted.entity.renderer.RenderArmourStand;
import mcpecommander.theOvercasted.entity.renderer.RenderLangolier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModEntities {

	@SubscribeEvent
	public static void init(RegistryEvent.Register<EntityEntry> e) {
		int id = 0;

		EntityEntry stand = EntityEntryBuilder.create().entity(EntityArmourStand.class)
				.egg(Color.GREEN.getRGB(), 996600).id(Reference.ModEntities.ARMOURSTAND.getRegistryName(), id++)
				.name(Reference.ModEntities.ARMOURSTAND.getName()).tracker(64, 3, true).build();
		EntityEntry langolier = EntityEntryBuilder.create().entity(EntityLangolier.class)
				.id(Reference.ModEntities.LANGOLIER.getRegistryName(), id++).egg(22342, 23123)
				.name(Reference.ModEntities.LANGOLIER.getName()).tracker(64, 3, true).build();

		e.getRegistry().registerAll(stand, langolier);

	}

	public static void initModel() {
		RenderingRegistry.registerEntityRenderingHandler(EntityArmourStand.class, new RenderArmourStand.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityLangolier.class, new RenderLangolier.Factory());
	}

}
