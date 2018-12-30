package mcpecommander.theOvercasted.entity.renderer.fattiesRenderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.fatties.EntityFatSack;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.renderer.RenderBasicChampion;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFatSack extends RenderBasicChampion<EntityFatSack> {

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "fat_sack", 64, 64);
	private static CSModelRendererOvercasted rightLeg, leftLeg, rightArm, leftArm, chest, head;
	
	public RenderFatSack(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);	
		rightLeg = model.getModelRendererFromName("RightLeg");
		leftLeg = model.getModelRendererFromName("LeftLeg");
		leftArm = model.getModelRendererFromName("LeftArm");
		rightArm = model.getModelRendererFromName("RightArm");
		head = model.getModelRendererFromName("Head");
		chest = model.getModelRendererFromName("Chest");
	}

	@Override
	protected void renderModel(EntityFatSack entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		float x = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		boolean moving = x != 0 && (x > 0.001f || x < -0.001f);
		if(moving) {
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.unbalancedWave(rightLeg.rotateAngleX, ageInTicks, false, false, 2f), 0, 0, rightLeg);
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.unbalancedWave(leftLeg.rotateAngleX, ageInTicks, true, false, 2f), 0, 0, leftLeg);
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.balancedWave(90, ageInTicks, true, 5f, 10f), -20f, 0f, leftArm);
			CSModelRendererOvercasted.rotateBoxes(CSModelRendererOvercasted.balancedWave(90, ageInTicks, false, 5f, 10f), 20f, 0f, rightArm);
		}else {
			model.resetAllRotations();
		}
		CSModelRendererOvercasted.rotateBoxes(0f, 0f, (float)(head.getDefualtZRotation() + Math.sin(ageInTicks / 10f)), head);
		if(entitylivingbaseIn.getSackType() == 2) {
			head.isHidden = true;
			chest.isHidden = false;
		}else if (entitylivingbaseIn.getSackType() == 3){
			chest.isHidden = true;
			head.isHidden = true;
		} else {
			head.isHidden = false;
			chest.isHidden = false;
		}
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
				scaleFactor);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFatSack entity) {
		if(entity.getSackType() == 0) {
			return new ResourceLocation(Reference.MODID, "textures/entity/fat_sack.png");
		}
		return new ResourceLocation(Reference.MODID, "textures/entity/pale_sack.png");
	}

	public static class Factory<T extends EntityFatSack> implements IRenderFactory<T> {

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderFatSack(manager);
		}

	}

}
