package mcpecommander.theOvercasted.pickupsSystem;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PickupsProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(IPickups.class) 
	public static final Capability<IPickups> PICKUPS = null; 

	private IPickups instance = PICKUPS.getDefaultInstance(); 

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == PICKUPS;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == PICKUPS ? PICKUPS.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return PICKUPS.getStorage().writeNBT(PICKUPS, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		PICKUPS.getStorage().readNBT(PICKUPS, instance, null, nbt);
		
	}

}
