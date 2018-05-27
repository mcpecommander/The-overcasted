package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.entities.EntityMushroomPerson;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMushroomPerson extends RenderLiving<EntityMushroomPerson>{

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "mushroom_person", 64, 32);
	
	public RenderMushroomPerson(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityMushroomPerson entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/mushroom_person.png");
	}
	
	@Override
	protected void renderModel(EntityMushroomPerson entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.shadowSize = 0.3f + entitylivingbaseIn.size/3f;
		GlStateManager.pushMatrix();
		GlStateManager.scale(1d + entitylivingbaseIn.size, 1d + entitylivingbaseIn.size, 1d + entitylivingbaseIn.size);
		GlStateManager.translate(0d, 0d - (entitylivingbaseIn.size/1.33d), 0d);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.popMatrix();
	}
	
	public static class Factory<T extends EntityMushroomPerson> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderMushroomPerson(manager);
		}

	}

}
