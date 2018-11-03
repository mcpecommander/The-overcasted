package mcpecommander.theOvercasted.entity.renderer;

import java.io.FileNotFoundException;

import javax.vecmath.Vector3f;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.animationTest.Animation;
import mcpecommander.theOvercasted.entity.animationTest.KeyFrame;
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

public class RenderTest extends RenderLiving<EntityTest> {

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "test", 64, 64);
	Animation animation, n2;
	int i ;
	float j;

	public RenderTest(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);
		this.addLayer(new LayerHeldItemModular(this, "Bottom", "RightArm"));

//		KeyFrame frame1 = new KeyFrame(new Vector3f(4, 1.5f, -17), 0);
//		KeyFrame frame2 = new KeyFrame(new Vector3f(-11.19f, -36.67f, -69.16f), 10);
//		KeyFrame frame3 = new KeyFrame(new Vector3f(39.78f, 9.81f, -129.95f), 20);
//		KeyFrame frame4 = new KeyFrame(new Vector3f(63.35f, 1.76f, -27.35f), 30);
//		KeyFrame frame5 = new KeyFrame(new Vector3f(63.35f, 1.76f, 25.37f), 40);
//		KeyFrame frame6 = new KeyFrame(new Vector3f(68.63f, 9.27f, -6.07f), 50);
//		KeyFrame frame7 = new KeyFrame(new Vector3f(4.01f, 1.58f, -17.23f), 60);

//		animation = Animation.BuildAnimation(true, 61,
//				Animation.createAnimation(61, "RightArm" ,frame1, frame2, frame3, frame4, frame5, frame6, frame7));

		n2 = Animation.readAnim(new ResourceLocation(Reference.MODID, "craftstudio/animations/entity/fat_walk.csjsmodelanim"));
		
	}

	@Override
	protected void renderModel(EntityTest entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		// model.getModelRendererFromName("RightArm").rotat
		// model.getModelRendererFromName("RightArm").resetRotationMatrix();
		// System.out.println(model.getModelRendererFromName("RightArm").getPositionAsVector());
		n2.playAnimation(60, 0, model);
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
			return new RenderTest(manager);
		}

	}

}
