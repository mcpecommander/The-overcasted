package mcpecommander.theOvercasted.gui;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.config.OvercastedConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.config.GuiSlider;

public class PickupsGUI extends GuiScreen{
	
	private TextureGui pickups;
	private GuiSlider slider;
	
	@Override
	public void initGui() {
		super.initGui();
		pickups =  new TextureGui(0, OvercastedConfig.hud.xPos, OvercastedConfig.hud.yPos
				, 10, new Color(255, 255, 255, 255));
		slider = new GuiSlider(1, width/2 - 50, height - 20, 100, 20, "Scale: ",
				"", 1, 20, OvercastedConfig.hud.scale, false, true);
		this.addButton(pickups);
		this.addButton(slider);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if (pickups.current) {
			pickups.mouseDragged(mc, mouseX, mouseY);
		}
	}
	
	@Override
	public void updateScreen() {
		slider.visible = true;
		slider.enabled = true;
		if(slider != null) {
			pickups.scale = slider.getValueInt();
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawWorldBackground(1);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void onGuiClosed() {
		OvercastedConfig.hud.xPos = pickups.x;
		OvercastedConfig.hud.yPos = pickups.y;
		OvercastedConfig.hud.scale = pickups.scale;
		ConfigManager.sync(Reference.MODID, Type.INSTANCE);
	}

}
