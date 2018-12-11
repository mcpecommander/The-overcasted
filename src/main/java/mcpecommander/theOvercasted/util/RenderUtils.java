package mcpecommander.theOvercasted.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

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
	
	public static void drawTextureFlipped(ResourceLocation texture, int x, int y, double scale, Color color) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		float r = color.getRed()/255f;
		float g = color.getGreen()/255f;
		float b = color.getBlue()/255f;
		float a = color.getAlpha()/255f;
		GlStateManager.color(r, g, b, a);

		buf.pos(x, (y + scale) , 0).tex(0, 0).endVertex();
		buf.pos(x + scale, y + scale, 0).tex(1, 0).endVertex();
		buf.pos(x + scale, y, 0).tex(1, 1).endVertex();
		buf.pos(x, y, 0).tex(0, 1).endVertex();

		tessellator.draw();
	}
	/**
	 * Renders a beam with a texture repeated according to length.
	 * @param start Start position (a block position) relative to 0, 0, 0.
	 * @param length Length in blocks.
	 * @param partialTicks
	 * @param rotate Should the beam rotate or not
	 * @param rotationSpeed A speed multiplier (negative will rotate in the other direction).
	 * @param texture Texture that will be repeated each block.
	 */
	public static void drawBeam(Vec3d start, int length, float partialTicks, boolean rotate, float rotationSpeed, ResourceLocation texture) {
		Vec3d end = start.addVector(length, .5, 0);
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();

		EntityPlayerSP player = Minecraft.getMinecraft().player;
		double tempX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double tempY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double tempZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
		GlStateManager.rotate(-90, 0, 1, 0);
		GlStateManager.rotate(-player.getRotationYawHead(), 0, 1, 0);
		GlStateManager.translate(0, 1, 0);
		if (rotate) {
			GlStateManager.rotate(-player.ticksExisted * rotationSpeed, 1, 0, 0);
		}
		GlStateManager.translate(0, -.5, 0);

		GlStateManager.translate(-tempX, -tempY, -tempZ);
		GlStateManager.color(255, 255, 255);

		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.getBuffer();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		float texturePosX = length;
		float texturePosY = 1f;
		buffer.pos(start.x, end.y + 0.25, start.z - 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x, end.y + 0.25, start.z - 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x, start.y + 0.25, start.z - 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x, start.y + 0.25, start.z - 0.25).tex(0, 0).endVertex();

		buffer.pos(start.x, start.y + 0.25, start.z + 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x, start.y + 0.25, start.z + 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x, end.y + 0.25, start.z + 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x, end.y + 0.25, start.z + 0.25).tex(0, 0).endVertex();

		buffer.pos(start.x, end.y + 0.25, start.z + 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x, end.y + 0.25, start.z + 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x, end.y + 0.25, start.z - 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x, end.y + 0.25, start.z - 0.25).tex(0, 0).endVertex();

		buffer.pos(start.x, start.y + 0.25, start.z - 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x, start.y + 0.25, start.z - 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x, start.y + 0.25, start.z + 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x, start.y + 0.25, start.z + 0.25).tex(0, 0).endVertex();

		tess.draw();

		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	

}
