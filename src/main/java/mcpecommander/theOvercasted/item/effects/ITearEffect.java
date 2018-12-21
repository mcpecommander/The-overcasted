package mcpecommander.theOvercasted.item.effects;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import net.minecraft.entity.Entity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public interface ITearEffect {
	
	default NonNullList<EntityTear> onCreation(NonNullList<EntityTear> tear) {
		return tear;
		
	};
	default void onUpdate(EntityTear tear) {
		
	};
	default void onHit(EntityTear tear, Entity hitTarget, BlockPos hitPos) {
		
	};

}
