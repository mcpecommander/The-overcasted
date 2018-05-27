package mcpecommander.theOvercasted.init;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityArmourStand;
import mcpecommander.theOvercasted.entity.entities.EntityBasilisk;
import mcpecommander.theOvercasted.entity.entities.EntityLangolier;
import mcpecommander.theOvercasted.entity.entities.EntityManEater;
import mcpecommander.theOvercasted.entity.entities.EntityMushroomPerson;
import mcpecommander.theOvercasted.entity.renderer.RenderArmourStand;
import mcpecommander.theOvercasted.entity.renderer.RenderBasilisk;
import mcpecommander.theOvercasted.entity.renderer.RenderLangolier;
import mcpecommander.theOvercasted.entity.renderer.RenderManEater;
import mcpecommander.theOvercasted.entity.renderer.RenderMushroomPerson;
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
		EntityEntry basilisk = EntityEntryBuilder.create().entity(EntityBasilisk.class)
				.id(Reference.ModEntities.BASILISK.getRegistryName(), id++).egg(12442, 256823)
				.name(Reference.ModEntities.BASILISK.getName()).tracker(64, 3, true).build();
		EntityEntry eater = EntityEntryBuilder.create().entity(EntityManEater.class)
				.id(Reference.ModEntities.MANEATER.getRegistryName(), id++).egg(235366, 256823)
				.name(Reference.ModEntities.MANEATER.getName()).tracker(64, 3, true).build();
		EntityEntry mushroom = EntityEntryBuilder.create().entity(EntityMushroomPerson.class)
				.id(Reference.ModEntities.MUSHROOMPERSON.getRegistryName(), id++).egg(7876366, 5843459)
				.name(Reference.ModEntities.MUSHROOMPERSON.getName()).tracker(64, 3, true).build();

		e.getRegistry().registerAll(stand, langolier, basilisk, eater, mushroom);

	}

	public static void initModel() {
		RenderingRegistry.registerEntityRenderingHandler(EntityArmourStand.class, new RenderArmourStand.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityLangolier.class, new RenderLangolier.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBasilisk.class, new RenderBasilisk.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityManEater.class, new RenderManEater.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityMushroomPerson.class, new RenderMushroomPerson.Factory());
	}

}
