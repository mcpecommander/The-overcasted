package mcpecommander.theOvercasted.capability.follower;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FollowerProvider implements ICapabilitySerializable<NBTBase>{
	
	@CapabilityInject(IFollower.class)
	public static final Capability<IFollower> FOLLOWER_CAP = null;
	
	private IFollower instance = FOLLOWER_CAP.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == FOLLOWER_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == FOLLOWER_CAP ? FOLLOWER_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return FOLLOWER_CAP.getStorage().writeNBT(FOLLOWER_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		FOLLOWER_CAP.getStorage().readNBT(FOLLOWER_CAP, instance, null, nbt);
		
	}

}
