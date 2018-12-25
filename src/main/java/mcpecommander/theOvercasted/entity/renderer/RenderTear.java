package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTear<T extends EntityTear> extends Render<T> {

	public static final CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "tear", 64);
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/tear.png");
	protected RenderTear(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(entity.ticksExisted > 1) {
			GlStateManager.pushMatrix();
			this.bindEntityTexture(entity);
			GlStateManager.translate(x, y, z);
			GlStateManager.rotate(180, 0, 0, 1);
			GlStateManager.translate(0, -1.5, 0);
			this.model.render(entity, 0, 0, entity.ticksExisted, 0, 0, 0.0625f);
			GlStateManager.popMatrix();
			super.doRender(entity, x, y, z, entityYaw, partialTicks);
		}
		
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTear entity) {
		return TEXTURE;
	}
	
	public static class Factory<T extends EntityTear> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderTear(manager);
		}

	}

}
