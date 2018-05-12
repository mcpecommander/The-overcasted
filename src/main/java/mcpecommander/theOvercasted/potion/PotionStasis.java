package mcpecommander.theOvercasted.potion;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionStasis extends Potion{
	
	private static final ResourceLocation STASIS = new ResourceLocation(Reference.MODID, "textures/potion/stasis.png");

	public PotionStasis() {
		super(true, 0x996600);
		this.setPotionName(Reference.ModPotions.STASIS.getName());
		this.setRegistryName(Reference.ModPotions.STASIS.getRegistryName());
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		render(x + 6, y + 7, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		render(x + 3, y + 3, alpha);
	}

	@SideOnly(Side.CLIENT)
	private void render(int x, int y, float alpha) {
		Minecraft.getMinecraft().renderEngine.bindTexture(STASIS);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		buf.begin(7, DefaultVertexFormats.POSITION_TEX);
		GlStateManager.color(1, 1, 1, alpha);

		buf.pos(x, (y + 18) , 0).tex(0, 1).endVertex();
		buf.pos(x + 18, y + 18, 0).tex(1,1).endVertex();
		buf.pos(x + 18, y, 0).tex(1,0).endVertex();
		buf.pos(x, y, 0).tex(0,0).endVertex();

		tessellator.draw();
	}

}
