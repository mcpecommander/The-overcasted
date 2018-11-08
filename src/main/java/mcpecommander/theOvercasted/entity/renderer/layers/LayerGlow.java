package mcpecommander.theOvercasted.entity.renderer.layers;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityBasicChampion;
import mcpecommander.theOvercasted.entity.renderer.RenderBasicChampion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerGlow<T extends EntityBasicChampion> implements LayerRenderer<T> {

	private static final ResourceLocation GLOW = new ResourceLocation(Reference.MODID, "textures/entity/conjoined_sack.png");
	private final RenderBasicChampion<T> renderer;

	public LayerGlow(RenderBasicChampion<T> renderer)
	    {
	        this.renderer = renderer;
	    }

	public void doRenderLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if(entitylivingbaseIn.getChampionType() == 6 || entitylivingbaseIn.getChampionType() == 8) {
			this.renderer.bindTexture(GLOW);
			GlStateManager.enableBlend();
			GlStateManager.disableAlpha();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	
			if (entitylivingbaseIn.isInvisible()) {
				GlStateManager.depthMask(false);
			} else {
				GlStateManager.depthMask(true);
			}
	
			int i = 61680;
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 0.4F);
			Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
			this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks,
					netHeadYaw, headPitch, scale);
			Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
			i = entitylivingbaseIn.getBrightnessForRender();
			j = i % 65536;
			k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
			this.renderer.setLightmap(entitylivingbaseIn);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}
