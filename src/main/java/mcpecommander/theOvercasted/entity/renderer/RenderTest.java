package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityTest;
import mcpecommander.theOvercasted.entity.models.ModelFatty;
import mcpecommander.theOvercasted.entity.renderer.layers.LayerHeldItemModular;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTest extends RenderLiving<EntityTest>{

	public RenderTest(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelFatty(), 0f);
		this.addLayer(new LayerHeldItemModular(this));
	}
	
	@Override
	protected void renderModel(EntityTest entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.5d, 1.5d, 1.5d);
		GlStateManager.translate(0, -.5, 0);
		GlStateManager.color(1f, 1f, 1f, 1f);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.popMatrix();
		
		
		
		
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTest entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/fatty.png");
	}
	
	public static class Factory<T extends EntityTest> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderTest(manager);
		}

	}

}
