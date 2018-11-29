package mcpecommander.theOvercasted.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {
	
	public static void drawTexture(ResourceLocation texture, int x, int y, double scale, Color color) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		float r = color.getRed()/255f;
		float g = color.getGreen()/255f;
		float b = color.getBlue()/255f;
		float a = color.getAlpha()/255f;
		GlStateManager.color(r, g, b, a);

		buf.pos(x, (y + scale) , 0).tex(0, 1).endVertex();
		buf.pos(x + scale, y + scale, 0).tex(1, 1).endVertex();
		buf.pos(x + scale, y, 0).tex(1, 0).endVertex();
		buf.pos(x, y, 0).tex(0, 0).endVertex();

		tessellator.draw();
	}

}
