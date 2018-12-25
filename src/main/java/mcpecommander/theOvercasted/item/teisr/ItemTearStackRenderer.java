package mcpecommander.theOvercasted.item.teisr;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.item.ItemTear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemTearStackRenderer extends TileEntityItemStackRenderer{
	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/tear.png");
	private static final CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "tear", 64);
	
	@Override
	public void renderByItem(ItemStack itemstack, float partialTicks) {
		if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemTear) {
			Color color = Color.WHITE;
			NBTTagCompound tag = itemstack.getTagCompound();
			if(tag != null) {
				color = new Color(tag.getInteger("color"));
			}
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			GlStateManager.pushMatrix();
			GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), 1f);
			GlStateManager.translate(.5, -1.25, .5);
			for(CSModelRendererOvercasted box : model.getParentBlocks()) {
				box.render(0.0625f);
			}
			GlStateManager.popMatrix();
		}else {
			
		}
		
	}

}
