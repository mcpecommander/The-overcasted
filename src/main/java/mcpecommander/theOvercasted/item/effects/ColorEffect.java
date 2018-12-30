package mcpecommander.theOvercasted.item.effects;

import java.awt.Color;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

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
	
	@Override
	public void onTearCreation(EntityPlayer entity, NonNullList<EntityTear> tears, Attribute attribute) {
		tears.forEach(tear -> tear.setColor(this.color.getRGB()));
	}

}
