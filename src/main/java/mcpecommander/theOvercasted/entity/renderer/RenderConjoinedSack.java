package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityConjoinedSack;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.renderer.layers.LayerHeldItemModular;
import mcpecommander.theOvercasted.init.ModAnimations;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderConjoinedSack extends RenderLiving<EntityConjoinedSack> {

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "test", 64, 64);

	public RenderConjoinedSack(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);
		this.addLayer(new LayerHeldItemModular(this, "Bottom", "RightArm"));

		
		
	}

	@Override
	protected void renderModel(EntityConjoinedSack entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		
		ModAnimations.fat_walk.playAnimation(60, 0, model);
		ModAnimations.popping.playAnimation(60, 0, model);
		ModAnimations.sizing.playAnimation(60, 0, model);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
				scaleFactor);
	}
	
	/*
	 * ModelOvercastedRenderer.rotateBoxes(0, Bottom.balancedWave(0, ageInTicks, true, 2f, 3f), Bottom.balancedWave(0, ageInTicks, false, 2f, 1.2f), Bottom);
		Bottom.render(scale);

		LeftLeg.rotateAngleX = ModelOvercastedRenderer.unbalancedWave(LeftLeg.rotateAngleX, ageInTicks, false, false, 2f);

		RightLeg.rotateAngleX = ModelOvercastedRenderer.unbalancedWave(RightLeg.rotateAngleX, ageInTicks, true, false, 2f);

		if (test) {
			LeftArm.resetRotation();
		} else {
			ModelOvercastedRenderer.rotateBoxes(ModelOvercastedRenderer.balancedWave(-90, ageInTicks, true, 5f, 10f), -20f, 0f, LeftArm);
		}

		if (test) {
			RightArm.resetRotation();
		} else {
			ModelOvercastedRenderer.rotateBoxes(ModelOvercastedRenderer.balancedWave(-90, ageInTicks, false, 5f, 10f), 20f, 0f, RightArm);
		}

		Head.rotateAngleZ = (float) (-18.4556180235F + Math.sin(ageInTicks / 5f));

		Head1.rotateAngleZ = (float) (17.8408916051F + Math.sin(ageInTicks / 5f));
	 * 
	 */

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
