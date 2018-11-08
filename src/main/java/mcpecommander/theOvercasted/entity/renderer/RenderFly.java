package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityFly;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.renderer.layers.LayerFlyGlow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFly extends RenderLiving<EntityFly>{

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "fly", 16, 16);

	public RenderFly(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);
		this.addLayer(new LayerFlyGlow());
	}
	
	@Override
	protected void renderModel(EntityFly entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		CSModelRendererOvercasted leftWing = model.getModelRendererFromName("LeftWing");
		CSModelRendererOvercasted rightWing = model.getModelRendererFromName("RightWing");
		CSModelRendererOvercasted.rotateBoxes(0, 0, CSModelRendererOvercasted.balancedWave(leftWing.getDefualtZRotation(), ageInTicks, false, 0.3f, 25f), leftWing);
		CSModelRendererOvercasted.rotateBoxes(0, 0, CSModelRendererOvercasted.balancedWave(rightWing.getDefualtZRotation(), ageInTicks, true, 0.3f, 25f), rightWing);
		GlStateManager.pushMatrix();
		GlStateManager.color(0.3f, 0.45f, 0.73f);
		
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.popMatrix();
		GlStateManager.color(1f, 1f, 1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFly entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/fly.png");
	}
	
	public static class Factory<T extends EntityFly> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderFly(manager);
		}

	}

}

