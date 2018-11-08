package mcpecommander.theOvercasted.entity.renderer.fattiesRenderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.fatties.EntityConjoinedSack;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.renderer.RenderBasicChampion;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderConjoinedSack extends RenderBasicChampion<EntityConjoinedSack> {

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "conjoined_sack", 64, 64);

	public RenderConjoinedSack(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);	
		
	}

	@Override
	protected void renderModel(EntityConjoinedSack entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		float x = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		boolean moving = x != 0 && (x > 0.001f || x < -0.001f);
		if(moving) {
			CSModelRendererOvercasted rightLeg = model.getModelRendererFromName("RightLeg");
			CSModelRendererOvercasted leftLeg = model.getModelRendererFromName("LeftLeg");
			CSModelRendererOvercasted leftArm = model.getModelRendererFromName("LeftArm");
			CSModelRendererOvercasted rightArm = model.getModelRendererFromName("RightArm");
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.unbalancedWave(rightLeg.rotateAngleX, ageInTicks, false, false, 2f), 0, 0, rightLeg);
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.unbalancedWave(leftLeg.rotateAngleX, ageInTicks, true, false, 2f), 0, 0, leftLeg);
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.balancedWave(90, ageInTicks, true, 5f, 10f), -20f, 0f, leftArm);
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.balancedWave(90, ageInTicks, false, 5f, 10f), 20f, 0f, rightArm);
		}else {
			model.resetAllRotations();
		}
		CSModelRendererOvercasted head = model.getModelRendererFromName("Head");
		CSModelRendererOvercasted head1 = model.getModelRendererFromName("Head1");
		CSModelRendererOvercasted.rotateBoxes(0f, 0f, (float)(head.getDefualtZRotation() + Math.sin(ageInTicks / 10f)), head);
		CSModelRendererOvercasted.rotateBoxes(0f, 0f, (float)(head1.getDefualtZRotation() + Math.sin(ageInTicks / 10f)), head1);
//		ModAnimations.fat_walk.playAnimation(60, 0, model);
//		ModAnimations.popping.playAnimation(60, 0, model);
//		ModAnimations.sizing.playAnimation(60, 0, model);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
				scaleFactor);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityConjoinedSack entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/conjoined_sack.png");
	}

	public static class Factory<T extends EntityConjoinedSack> implements IRenderFactory<T> {

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderConjoinedSack(manager);
		}

	}

}
