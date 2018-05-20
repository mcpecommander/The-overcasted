package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.entities.EntityManEater;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderManEater extends RenderLiving<EntityManEater>{

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "man_eater", 128, 64);
	
	public RenderManEater(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);
	}

	@Override
	protected void renderModel(EntityManEater entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		GlStateManager.pushMatrix();
		int color = BiomeColorHelper.getGrassColorAtPos(entitylivingbaseIn.world, entitylivingbaseIn.getPosition());
        if(color != -1){
        	double d0 = (color >> 16 & 255) / 255.0D;
            double d1 = (color >> 8 & 255) / 255.0D;
            double d2 = (color >> 0 & 255) / 255.0D;
            GlStateManager.color((float)d0, (float)d1, (float)d2, .99F);
        }else{
        	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
		if(entitylivingbaseIn.isBeingRidden() || entitylivingbaseIn.deathTime > 0) {
			model.getModelRendererFromName("PetalFront").showModel = true;
			model.getModelRendererFromName("PetalBack").showModel = true;
			model.getModelRendererFromName("PetalLeft").showModel = true;
			model.getModelRendererFromName("PetalRight").showModel = true;
		}else {
			model.getModelRendererFromName("PetalFront").showModel = false;
			model.getModelRendererFromName("PetalBack").showModel = false;
			model.getModelRendererFromName("PetalLeft").showModel = false;
			model.getModelRendererFromName("PetalRight").showModel = false;
		}
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.popMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityManEater entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/man_eater.png");
	}
	
	public static class Factory<T extends EntityManEater> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderManEater(manager);
		}

	}

}
