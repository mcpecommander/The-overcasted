package mcpecommander.theOvercasted.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class DebuggerVisualizer {
	
	public static void RenderLine(Vec3d start, Vec3d end, float partialTicks) {
			
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		EntityPlayerSP player = Minecraft.getMinecraft().player;
		double tempX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double tempY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double tempZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		GlStateManager.translate(-tempX, -tempY, -tempZ);

		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.getBuffer();

		buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		GlStateManager.glLineWidth(2f);

		buffer.pos(start.x, start.y, start.z).color(255, 255, 255, 255).endVertex();
		buffer.pos(end.x, end.y, end.z).color(255, 255, 255, 255).endVertex();

		tess.draw();

		GlStateManager.glLineWidth(1.0f);

		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
		}
	
	public static void renderBox(AxisAlignedBB boundingBox, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		EntityPlayerSP player = Minecraft.getMinecraft().player;
		double tempX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double tempY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double tempZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		GlStateManager.translate(-tempX, -tempY, -tempZ);

		GlStateManager.glLineWidth(2.0F);
		RenderGlobal.drawSelectionBoundingBox(boundingBox.grow(0.002D), 1.0F, 1.0F, 1.0F, 1.0F);
		
		GlStateManager.glLineWidth(1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}
	
	/**
	 * Draws the forbidden object.
	 * @param position A vector with the wanted position to draw the center at. Normal world positions.
	 * @param rgba The color of the sphere.
	 * @param radius The radius of the sphere. (where 1 means one block wide)
	 * @param partialTicks 
	 */
	public static void renderSphere(Vec3d position, Color rgba, float radius, float partialTicks) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		double tempX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double tempY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double tempZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
		
		final Sphere s = new Sphere();
		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(1.2F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		s.setDrawStyle(GLU.GLU_FILL);
		GL11.glTranslated(-tempX, -tempY, -tempZ);
		GL11.glTranslated(position.x, position.y, position.z);
		GL11.glColor4f(rgba.getRed(), rgba.getGreen(), rgba.getBlue(), rgba.getAlpha());
		s.draw(radius, 30, 30);
		GL11.glLineWidth(2.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
	}
	
	/**
	 * Renders a beam with a texture repeated according to length.
	 * @param start Start position (a block position) relative to 0, 0, 0.
	 * @param length Length in blocks.
	 * @param partialTicks
	 * @param texture Texture that will be repeated each block.
	 */
	public static void renderBeam(Vec3d start, int length, float partialTicks, ResourceLocation texture) {
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
		GlStateManager.rotate(-player.ticksExisted * 30f, 1, 0, 0);
		GlStateManager.translate(0, -.5, 0);
		
		GlStateManager.translate(-tempX, -tempY, -tempZ);
		GlStateManager.color(255, 255, 255);
		

		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.getBuffer();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		float texturePosX = length;
		float texturePosY = 1f;
		buffer.pos(start.x , end.y + 0.25, start.z - 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x , end.y + 0.25, start.z - 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x, start.y + 0.25, start.z - 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x , start.y + 0.25, start.z - 0.25).tex(0, 0).endVertex();
		
		buffer.pos(start.x , start.y + 0.25, start.z + 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x, start.y + 0.25, start.z + 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x , end.y + 0.25, start.z + 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x , end.y + 0.25, start.z + 0.25).tex(0, 0).endVertex();
		
		buffer.pos(start.x , end.y + 0.25, start.z + 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x, end.y + 0.25, start.z + 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x , end.y + 0.25, start.z - 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x , end.y + 0.25, start.z - 0.25).tex(0, 0).endVertex();
		
		buffer.pos(start.x, start.y + 0.25, start.z - 0.25).tex(0, texturePosY).endVertex();
		buffer.pos(end.x, start.y + 0.25, start.z - 0.25).tex(texturePosX, texturePosY).endVertex();
		buffer.pos(end.x , start.y + 0.25, start.z + 0.25).tex(texturePosX, 0).endVertex();
		buffer.pos(start.x , start.y + 0.25, start.z + 0.25).tex(0, 0).endVertex();
		
		tess.draw();
		
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

}
