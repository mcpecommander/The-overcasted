package mcpecommander.theOvercasted.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IHasModel {
	
	@SideOnly(Side.CLIENT)
	public void registerModels();

}
