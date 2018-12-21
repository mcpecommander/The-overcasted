package mcpecommander.theOvercasted.item.effects;

import java.lang.reflect.InvocationTargetException;

import mcpecommander.theOvercasted.entity.entities.familiars.EntityBasicFamiliar;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityPrimalFamiliar;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityPrimalFamiliar.Type;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FamiliarEffect implements IEffect {
	
	EntityBasicFamiliar entity;

	public FamiliarEffect(EntityBasicFamiliar entity) {
		this.entity = entity;
		
	}
	
	@Override
	public void onGet(EntityPlayer player, ItemStack stack, Attribute attribute) {
		EntityLivingBase master = EntityBasicFamiliar.getLastFollower(player);
		EntityBasicFamiliar familiar = null;
		try {
			if(entity instanceof EntityPrimalFamiliar) {
				familiar = entity.getClass().getConstructor(World.class, EntityLivingBase.class, Type.class)
						.newInstance(player.world, master, Type.getByID(attribute.getId()));
			}else {
				familiar = entity.getClass().getConstructor(World.class, EntityLivingBase.class).newInstance(player.world, master);
			} 
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if(familiar != null) {
			familiar.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
			player.world.spawnEntity(familiar);
		}
	}

	
	

}
