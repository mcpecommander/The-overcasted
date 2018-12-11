package mcpecommander.theOvercasted.item.teisr;

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
import net.minecraft.util.math.MathHelper;

public class ItemTearStackRenderer extends TileEntityItemStackRenderer{
	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/fly.png");
	private static final CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "tear", 16);
	
	@Override
	public void renderByItem(ItemStack itemstack, float partialTicks) {
		if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemTear) {
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			GlStateManager.pushMatrix();
			GlStateManager.color(1f, 1f, 1f, 1f);
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
