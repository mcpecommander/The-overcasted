package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntitySnowRat;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSnowRat extends RenderLiving<EntitySnowRat>{

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "snow_rat", 64, 32);
	
	public RenderSnowRat(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, .3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySnowRat entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/snow_rat.png");
	}
	
	public static class Factory<T extends EntitySnowRat> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderSnowRat(manager);
		}

	}

}
