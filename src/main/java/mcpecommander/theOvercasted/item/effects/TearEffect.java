package mcpecommander.theOvercasted.item.effects;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class TearEffect implements IEffect {
	
	ITearEffect[] effects;
	
	public TearEffect(ITearEffect... effects) {
		this.effects = effects;
	}
	
	@Override
	public void onTearCreation(EntityPlayer entity, NonNullList<EntityTear> tears, Attribute attribute) {
		for(ITearEffect effect : effects) {
			effect.onCreation(tears);
		}
	}
	
	@Override
	public void onEntityTearUpdate(EntityTear tear) {
		for(ITearEffect effect : effects) {
			effect.onUpdate(tear);
		}
	}
	
	@Override
	public void onEntityTearHit(EntityTear tear, Entity hitTarget, BlockPos hitPos) {
		for(ITearEffect effect : effects) {
			effect.onHit(tear, hitTarget, hitPos);
		}
	}

}
