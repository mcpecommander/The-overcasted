package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.entities.EntityBasilisk;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBasilisk extends RenderLiving<EntityBasilisk>{

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "basilisk", 64, 64);
	
	public RenderBasilisk(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0.7f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBasilisk entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/basilisk.png");
	}
	
	public static class Factory<T extends EntityBasilisk> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderBasilisk(manager);
		}

	}

}
