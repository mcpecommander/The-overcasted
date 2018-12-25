package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.entity.entities.EntityBasicChampion;
import mcpecommander.theOvercasted.entity.renderer.layers.LayerGlow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;

public abstract class RenderBasicChampion<T extends EntityBasicChampion> extends RenderLiving<T>{

	public RenderBasicChampion(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
		this.addLayer(new LayerGlow<>(this));
	}
	
	@Override
	protected void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		switch(entitylivingbaseIn.getChampionType()) {
		case 0:
			GlStateManager.color(1f, 0, 0, 1f);
			break;
		case 1:
			GlStateManager.color(1f, 1f, 0f, 1f);
			break;
		case 2:
			GlStateManager.color(0f, 1f, 0f, 1f);
			break;
		case 3:
			GlStateManager.color(1f, 0.5f, 0f, 1f);
			break;
		case 4:
			GlStateManager.color(0f, 0f, 0.4f, 1f);
			break;
		case 5:
			GlStateManager.color(0f, 0.4f, 0f, 1f);
			break;
		case 6:
			//brightness.
			break;
		case 7:
			GlStateManager.color(0.47f, 0.43f, 0.47f, 1f);
			break;
		case 8:
			//brightness.
			GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			GlStateManager.color(1f, 1f, 1f, 0.4f);
			break;
		case 9:
			GlStateManager.color(0f, 0.14f, 0f, 1f);
			break;
		case 10:
			GlStateManager.color(1f, 0.4f, 0.8f, 1f);
			break;
		case 11:
			GlStateManager.color(0.6f, 0f, 0.6f, 1f);
			break;
		case 12:
			GlStateManager.color(0.4f, 0f, 0f, 1f);
			break;
		case 13:
			GlStateManager.color(0.4f, 0.7f, 1f, 1f);
			break;
		case 14:
			GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			GlStateManager.color(1f, 1f, 1f, 0.08f);
			break;
		case 15:
			GlStateManager.color(0f, (float) entitylivingbaseIn.getRNG().nextGaussian(), 0f, 1f);
			break;
		}
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.color(1f, 1f, 1f, 1f);
		GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
	}

}
