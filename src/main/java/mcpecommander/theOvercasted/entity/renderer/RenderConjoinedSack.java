package mcpecommander.theOvercasted.entity.renderer;

import java.util.HashSet;

import javax.vecmath.Vector3f;

import org.apache.commons.lang3.tuple.ImmutablePair;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.animationTest.Animation;
import mcpecommander.theOvercasted.entity.animationTest.KeyFrame;
import mcpecommander.theOvercasted.entity.entities.EntityTest;
import mcpecommander.theOvercasted.entity.renderer.layers.LayerHeldItemModular;
import mcpecommander.theOvercasted.init.ModAnimations;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderConjoinedSack extends RenderLiving<EntityTest> {

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "test", 64, 64);

	public RenderConjoinedSack(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);
		this.addLayer(new LayerHeldItemModular(this, "Bottom", "RightArm"));

		
		
	}

	@Override
	protected void renderModel(EntityTest entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		
		ModAnimations.fat_walk.playAnimation(60, 0, model);
		ModAnimations.popping.playAnimation(60, 0, model);
		ModAnimations.sizing.playAnimation(60, 0, model);
//		System.out.println(ModAnimations.popping);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
				scaleFactor);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTest entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/conjoined_sack.png");
	}

	public static class Factory<T extends EntityTest> implements IRenderFactory<T> {

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderConjoinedSack(manager);
		}

	}

}
