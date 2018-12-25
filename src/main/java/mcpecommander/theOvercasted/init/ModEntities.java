package mcpecommander.theOvercasted.init;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityBasilisk;
import mcpecommander.theOvercasted.entity.entities.EntityLangolier;
import mcpecommander.theOvercasted.entity.entities.EntityLargeRoomOverseer;
import mcpecommander.theOvercasted.entity.entities.EntityManEater;
import mcpecommander.theOvercasted.entity.entities.EntityMushroomPerson;
import mcpecommander.theOvercasted.entity.entities.EntityNarrowRoomOverseer;
import mcpecommander.theOvercasted.entity.entities.EntityOverseer;
import mcpecommander.theOvercasted.entity.entities.EntitySnowRat;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityPrimalFamiliar;
import mcpecommander.theOvercasted.entity.entities.fatties.EntityConjoinedSack;
import mcpecommander.theOvercasted.entity.entities.fatties.EntityFatSack;
import mcpecommander.theOvercasted.entity.entities.flies.EntityAttackFly;
import mcpecommander.theOvercasted.entity.entities.flies.EntityBlackFly;
import mcpecommander.theOvercasted.entity.entities.pickups.EntityBomb;
import mcpecommander.theOvercasted.entity.entities.projectiles.EntitySlaveTear;
import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import mcpecommander.theOvercasted.entity.renderer.RenderBasilisk;
import mcpecommander.theOvercasted.entity.renderer.RenderLangolier;
import mcpecommander.theOvercasted.entity.renderer.RenderManEater;
import mcpecommander.theOvercasted.entity.renderer.RenderMushroomPerson;
import mcpecommander.theOvercasted.entity.renderer.RenderSlaveTear;
import mcpecommander.theOvercasted.entity.renderer.RenderSnowRat;
import mcpecommander.theOvercasted.entity.renderer.RenderTear;
import mcpecommander.theOvercasted.entity.renderer.familiarsRenderer.RenderPrimalFamiliar;
import mcpecommander.theOvercasted.entity.renderer.fattiesRenderer.RenderConjoinedSack;
import mcpecommander.theOvercasted.entity.renderer.fattiesRenderer.RenderFatSack;
import mcpecommander.theOvercasted.entity.renderer.fliesRenderer.RenderAttackFly;
import mcpecommander.theOvercasted.entity.renderer.fliesRenderer.RenderBlackFly;
import mcpecommander.theOvercasted.entity.renderer.pickupsRenderer.RenderBomb;
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

		EntityEntry overseer = EntityEntryBuilder.create().entity(EntityOverseer.class)
				.id(Reference.ModEntities.OVERSEER.getRegistryName(), id++)
				.name(Reference.ModEntities.OVERSEER.getName()).tracker(64, 3, true).build();
		EntityEntry large_overseer = EntityEntryBuilder.create().entity(EntityLargeRoomOverseer.class)
				.id(Reference.ModEntities.LARGE_OVERSEER.getRegistryName(), id++)
				.name(Reference.ModEntities.LARGE_OVERSEER.getName()).tracker(64, 3, true).build();
		EntityEntry small_overseer = EntityEntryBuilder.create().entity(EntityNarrowRoomOverseer.class)
				.id(Reference.ModEntities.SMALL_OVERSEER.getRegistryName(), id++)
				.name(Reference.ModEntities.SMALL_OVERSEER.getName()).tracker(64, 3, true).build();
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
		EntityEntry rat = EntityEntryBuilder.create().entity(EntitySnowRat.class)
				.id(Reference.ModEntities.SNOWRAT.getRegistryName(), id++).egg(354578, 233488)
				.name(Reference.ModEntities.SNOWRAT.getName()).tracker(64, 3, true).build();
		EntityEntry conjoinedSack = EntityEntryBuilder.create().entity(EntityConjoinedSack.class)
				.id(Reference.ModEntities.CONJOINED_SACK.getRegistryName(), id++).egg(354578, 233488)
				.name(Reference.ModEntities.CONJOINED_SACK.getName()).tracker(64, 3, true).build();
		EntityEntry black_fly = EntityEntryBuilder.create().entity(EntityBlackFly.class)
				.id(Reference.ModEntities.BLACK_FLY.getRegistryName(), id++)
				.egg(Color.BLACK.getRGB(), Color.BLACK.brighter().brighter().getRGB())
				.name(Reference.ModEntities.BLACK_FLY.getName()).tracker(64, 3, true).build();
		EntityEntry attack_fly = EntityEntryBuilder.create().entity(EntityAttackFly.class)
				.id(Reference.ModEntities.ATTACK_FLY.getRegistryName(), id++)
				.egg(Color.RED.getRGB(), Color.GRAY.darker().getRGB())
				.name(Reference.ModEntities.ATTACK_FLY.getName()).tracker(64, 3, true).build();
		EntityEntry fat_sack = EntityEntryBuilder.create().entity(EntityFatSack.class)
				.id(Reference.ModEntities.FAT_SACK.getRegistryName(), id++).egg(122578, 23288)
				.name(Reference.ModEntities.FAT_SACK.getName()).tracker(64, 3, true).build();
		EntityEntry bomb = EntityEntryBuilder.create().entity(EntityBomb.class)
				.id(Reference.ModEntities.BOMB.getRegistryName(), id++)
				.name(Reference.ModEntities.BOMB.getName()).tracker(64, 3, true).build();
		EntityEntry tear = EntityEntryBuilder.create().entity(EntityTear.class)
				.id(Reference.ModEntities.TEAR.getRegistryName(), id++)
				.name(Reference.ModEntities.TEAR.getName()).tracker(64, 3, true).build();
		EntityEntry slaveTear = EntityEntryBuilder.create().entity(EntitySlaveTear.class)
				.id(Reference.ModEntities.SLAVE_TEAR.getRegistryName(), id++)
				.name(Reference.ModEntities.SLAVE_TEAR.getName()).tracker(64, 3, true).build();
		EntityEntry primal_familiar = EntityEntryBuilder.create().entity(EntityPrimalFamiliar.class)
				.id(Reference.ModEntities.PRIMAL_FAMILIAR.getRegistryName(), id++)
				.name(Reference.ModEntities.PRIMAL_FAMILIAR.getName()).tracker(64, 3, true).build();
		
		
		e.getRegistry().registerAll(langolier, basilisk, eater, mushroom, rat, conjoinedSack, black_fly, fat_sack, bomb, tear, 
				slaveTear, primal_familiar, attack_fly, overseer, large_overseer, small_overseer);

	}

	public static void initModel() {
		RenderingRegistry.registerEntityRenderingHandler(EntityLangolier.class, new RenderLangolier.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBasilisk.class, new RenderBasilisk.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityManEater.class, new RenderManEater.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityMushroomPerson.class, new RenderMushroomPerson.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntitySnowRat.class, new RenderSnowRat.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityConjoinedSack.class, new RenderConjoinedSack.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackFly.class, new RenderBlackFly.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityAttackFly.class, new RenderAttackFly.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFatSack.class, new RenderFatSack.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBomb.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTear.class, new RenderTear.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntitySlaveTear.class, new RenderSlaveTear.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityPrimalFamiliar.class, new RenderPrimalFamiliar.Factory());
	}

}
