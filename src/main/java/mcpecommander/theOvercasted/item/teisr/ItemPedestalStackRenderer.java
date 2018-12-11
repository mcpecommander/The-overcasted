package mcpecommander.theOvercasted.item.teisr;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.item.ItemBlockPedestal;
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

public class ItemPedestalStackRenderer extends TileEntityItemStackRenderer{
	
	private static final CraftStudioModelSon MODEL = new CraftStudioModelSon(Reference.MODID, "block_pedestal", 64, 32);
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/block/pedestal.png");
	
	@Override
	public void renderByItem(ItemStack itemstack, float partialTicks) {
		if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemBlockPedestal) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			GlStateManager.pushMatrix();
//			GlStateManager.rotate(45, 0, 1, 0);
			GlStateManager.translate(.5, -1.25, .5);
			for(CSModelRendererOvercasted box : MODEL.getParentBlocks()) {
				box.render(0.0625f);
			}
			GlStateManager.popMatrix();
		}else {
			
		}
		
	}
	
	

}
