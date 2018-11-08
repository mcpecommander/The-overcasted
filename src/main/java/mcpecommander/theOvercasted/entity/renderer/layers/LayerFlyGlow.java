package mcpecommander.theOvercasted.entity.renderer.layers;

import java.util.Random;

import mcpecommander.theOvercasted.entity.entities.EntityFly;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;

public class LayerFlyGlow implements LayerRenderer<EntityFly>{
	
	int counter = 0;
	boolean back;
	
	public void doRenderLayer(EntityFly entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
		if(back) {
			counter--;
		}else {
			counter++;
		}
		if(counter > 175) {
			back = true;
		}else if(counter < 50) {
			back = false;
		}
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();
		float f = ((float) counter + partialTicks) / 200.0F;
		float f1 = 0.0F;

		if (f > 0.8F) {
			f1 = (f - 0.8F) / 0.2F;
		}

		Random random = new Random(432L);
		GlStateManager.disableTexture2D();
		GlStateManager.shadeModel(7425);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.disableAlpha();
		GlStateManager.enableCull();
		GlStateManager.depthMask(false);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 1.45F, 0f);
		for (int i = 0; (float) i < 30; ++i) {
			GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
			float f2 = random.nextFloat() /4.5f;
			float f3 = random.nextFloat() /4.5f;
			bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
			bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 50, 200, (int) (255.0F * (1.0F - f1))).endVertex();
			bufferbuilder.pos(-0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(0, 0, 255, 0)
					.endVertex();
			bufferbuilder.pos(0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(0, 0, 255, 0)
					.endVertex();
			bufferbuilder.pos(0.0D, (double) f2, (double) (1.0F * f3)).color(0, 0, 255, 0).endVertex();
			bufferbuilder.pos(-0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(0, 0, 255, 0)
					.endVertex();
			tessellator.draw();
		}

		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.shadeModel(7424);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableTexture2D();
		GlStateManager.enableAlpha();
		RenderHelper.enableStandardItemLighting();

    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

}
