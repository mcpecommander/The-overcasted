package mcpecommander.theOvercasted.item.teisr;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.item.ItemLaserBeam;
import mcpecommander.theOvercasted.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ItemLaserBeamStackRenderer extends TileEntityItemStackRenderer{
	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/projectile.png");
	private static final CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "projectile", 32);
	
	@Override
	public void renderByItem(ItemStack itemstack, float partialTicks) {
		if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemLaserBeam) {
			NBTTagCompound tag = itemstack.getTagCompound();
			float redValue = 0f;
			if (tag != null) {
				redValue = tag.getInteger("use_ticks");
			}
			redValue = MathHelper.clamp(1 - redValue/20f, 0f, 1f);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			GlStateManager.pushMatrix();
			GlStateManager.color(1f, redValue, redValue, 1f);
//			GlStateManager.rotate(45, 0, 1, 0);
			GlStateManager.translate(.5, -1.25, .5);
			for(CSModelRendererOvercasted box : model.getParentBlocks()) {
				box.render(0.0625f);
			}
			GlStateManager.popMatrix();
		}else {
			
		}
		
	}
	
	

}
