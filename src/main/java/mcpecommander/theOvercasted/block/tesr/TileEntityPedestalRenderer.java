package mcpecommander.theOvercasted.block.tesr;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.block.tileEntity.TileEntityPedestal;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.registryHandler.Registry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TileEntityPedestalRenderer extends TileEntitySpecialRenderer<TileEntityPedestal>{
	
	private static final CraftStudioModelSon MODEL = new CraftStudioModelSon(Reference.MODID, "block_pedestal", 64, 32);
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/block/pedestal.png");
	private static int rotationTick;
	
	@Override
	public void render(TileEntityPedestal te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		rotationTick ++;
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(.5, .5, .5);
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.translate(0, -1, 0);
		for(CSModelRendererOvercasted box : MODEL.getParentBlocks()) {
			box.render(0.0625f);
		}
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.translate(0, -.7, 0);
		GlStateManager.translate(0, Math.abs(Math.sin(rotationTick/50f)/10f), 0);
		GlStateManager.rotate(rotationTick/2f, 0, 1, 0);
		GlStateManager.scale(.7, .7, .7);
		Minecraft.getMinecraft().getRenderItem().renderItem(te.getItemStack(), TransformType.NONE);
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}

}
