package mcpecommander.theOvercasted.item.effects;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ColorEffect implements IEffect {
	
	private Color color;
	
	public ColorEffect(Color color) {
		this.color = color;
	}
	
	public ColorEffect(String color) {
		this.color = Color.decode(color);
	}
	
	@Override
	public void onGet(EntityPlayer player, ItemStack stack, Attribute attribute) {
		NBTTagCompound tag = stack.getTagCompound();
		if(tag != null) {
			tag.setInteger("color", this.color.getRGB());
		}
		
	}

}
