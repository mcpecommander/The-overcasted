package mcpecommander.theOvercasted.entity.renderer.fliesRenderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.flies.EntityAttackFly;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderAttackFly extends RenderFly<EntityAttackFly> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/fly.png");
	private static final CraftStudioModelSon MODEL = new CraftStudioModelSon(Reference.MODID, "fly", 16);
	private CSModelRendererOvercasted leftWing, rightWing;

	public RenderAttackFly(RenderManager rendermanagerIn) {
		super(rendermanagerIn, MODEL);
		leftWing = MODEL.getModelRendererFromName("LeftWing");
		rightWing = MODEL.getModelRendererFromName("RightWing");

	}
	
	@Override
	protected void renderModel(EntityAttackFly entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		CSModelRendererOvercasted.rotateBoxes(0, 0,
				CSModelRendererOvercasted.balancedWave(leftWing.getDefualtZRotation(), ageInTicks, false, .75f, 25f),
				leftWing);
		CSModelRendererOvercasted.rotateBoxes(0, 0,
				CSModelRendererOvercasted.balancedWave(rightWing.getDefualtZRotation(), ageInTicks, true, .75f, 25f),
				rightWing);
		if(entitylivingbaseIn.ticksExisted / 4 % 2 == 0) {
			GlStateManager.color(.05f, .05f, .05f, 1.0f);
		}else {
			GlStateManager.color(1f, .05f, .05f, 1.0f);
		}
		
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAttackFly entity) {
		return TEXTURE;
	}
	
	public static class Factory<T extends EntityAttackFly> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderAttackFly(manager);
		}

	}

}
