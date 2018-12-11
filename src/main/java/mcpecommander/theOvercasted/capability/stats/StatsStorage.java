package mcpecommander.theOvercasted.capability.stats;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StatsStorage implements IStorage<IStats>{

	@Override
	public NBTBase writeNBT(Capability<IStats> capability, IStats instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("speed", instance.getSpeed());
		tag.setFloat("range", instance.getRange());
		tag.setFloat("fire_rate", instance.getRange());
		tag.setFloat("projectile_speed", instance.getProjectileSpeed());
		tag.setFloat("damage", instance.getDamage());
		tag.setFloat("luck", instance.getLuck());
		tag.setFloat("devil", instance.getDevilChance());
		tag.setFloat("angel", instance.getAngelChance());
		return tag;
	}

	@Override
	public void readNBT(Capability<IStats> capability, IStats instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagCompound) {
			NBTTagCompound tag = (NBTTagCompound) nbt;
			instance.setSpeed(tag.getFloat("speed"));
			instance.setRange(tag.getFloat("range"));
			instance.setFireRate(tag.getFloat("fire_rate"));
			instance.setProjectileSpeed(tag.getFloat("projectile_speed"));
			instance.setDamage(tag.getFloat("damage"));
			instance.setLuck(tag.getFloat("luck"));
			instance.setDevilChance(tag.getFloat("devil"));
			instance.setAngelChance(tag.getFloat("angel"));
		}
		
	}

}
