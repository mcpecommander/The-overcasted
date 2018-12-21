package mcpecommander.theOvercasted.capability.follower;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FollowerStorage implements IStorage<IFollower>{

	@Override
	public NBTBase writeNBT(Capability<IFollower> capability, IFollower instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setUniqueId("follower", instance.getFollower());
		return tag;
	}

	@Override
	public void readNBT(Capability<IFollower> capability, IFollower instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagCompound) {
			instance.setFollower(((NBTTagCompound) nbt).getUniqueId("follower"));
		}
		
	}



}
