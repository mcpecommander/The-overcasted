package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.entities.EntityLangolier;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderLangolier extends RenderLiving<EntityLangolier>{
	
	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "langolier", 64, 64);

	public RenderLangolier(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLangolier entity) {
		
		return new ResourceLocation(Reference.MODID, "textures/entity/langolier.png");
	}
	
	public static class Factory<T extends EntityLangolier> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderLangolier(manager);
		}

	}
	

}
