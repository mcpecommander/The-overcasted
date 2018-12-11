package mcpecommander.theOvercasted.item.effects;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;

public interface ITearEffect {
	
	default NonNullList<EntityTear> onCreation(NonNullList<EntityTear> tear) {
		return tear;
		
	};
	default void onUpdate(EntityTear tear) {
		
	};
	default void onHit(NonNullList<EntityTear> tear, RayTraceResult hitResult) {
		
	};

}
