package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.entities.EntityMushroomPerson;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMushroomPerson extends RenderLiving<EntityMushroomPerson>{

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "mushroom_person", 64, 32);
	
	public RenderMushroomPerson(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0.7f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMushroomPerson entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/mushroom_person.png");
	}
	
	public static class Factory<T extends EntityMushroomPerson> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderMushroomPerson(manager);
		}

	}

}
