package mcpecommander.theOvercasted.gui;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.capability.pickups.IPickups;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class TextureGui extends GuiButton{
	
	int xPressPos, yPressPos, scale;
	private Color color;
	public boolean current;
	private ItemStack item;
	
	private static final ResourceLocation BOMB = new ResourceLocation(Reference.MODID, "textures/gui/bomb.png");
	private static final ResourceLocation COIN = new ResourceLocation(Reference.MODID, "textures/gui/coin.png");
	private static final ResourceLocation KEY = new ResourceLocation(Reference.MODID, "textures/gui/key.png");

	public TextureGui(int buttonId, int x, int y, int scale, Color color) {
		super(buttonId, x, y, scale * 4, scale * 4, "");
		this.scale = scale;
		this.color = color;
		item = new ItemStack(Registry.BOMB);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		IPickups pickups = Minecraft.getMinecraft().player.getCapability(PickupsProvider.PICKUPS, null);
		if(pickups != null) {
			
			RenderUtils.drawTexture(COIN, x, y, scale, new Color(255, 255, 255, 255));
			Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getCoins(), x + scale + 2, y + scale/4, Color.BLACK.getRGB());
			RenderUtils.drawTexture(BOMB, x, y + scale + 4, scale, new Color(255, 255, 255, 255));
			Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getBombs(), x + scale + 2, (y + scale + 4) + scale/4, Color.BLACK.getRGB());
			RenderUtils.drawTexture(KEY, x, y + (scale + 4) * 2, scale, new Color(255, 255, 255, 255));
			Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getKeys(), x + scale + 2, (y + (scale + 4) * 2) + scale/4 , Color.BLACK.getRGB());
		}
	}
	
	@Override
	public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		xPressPos = mouseX;
		yPressPos = mouseY;
		x += xPressPos - x;
		y += yPressPos - y;
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		super.mouseReleased(mouseX, mouseY);
		this.current = false;
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		boolean flag = super.mousePressed(mc, mouseX, mouseY);
		if(flag) this.current = true;
		return flag;
	}


}
