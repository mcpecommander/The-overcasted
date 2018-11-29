package mcpecommander.theOvercasted.pickupsSystem;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PickupsStorage implements IStorage<IPickups> {

	@Override
	public NBTBase writeNBT(Capability<IPickups> capability, IPickups instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("coins", instance.getCoins());
		tag.setInteger("bombs", instance.getBombs());
		tag.setInteger("keys", instance.getKeys());
		return tag;
	}

	@Override
	public void readNBT(Capability<IPickups> capability, IPickups instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagCompound) {
			NBTTagCompound tag = (NBTTagCompound) nbt;
			instance.setCoins(tag.getInteger("coins"));
			instance.setBombs(tag.getInteger("bombs"));
			instance.setKeys(tag.getInteger("keys"));
		}

	}

}
