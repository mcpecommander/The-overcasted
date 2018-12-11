package mcpecommander.theOvercasted.gui;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.PICKUPS_GUI_ID) {
			return new PickupsGUI();
		}
		return new PickupsGUI();
	}
	
	

}
