package mcpecommander.theOvercasted.item.effects;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;

public class TearEffect extends Effect {
	
	ITearEffect[] effects;
	
	public TearEffect(ActionType type, ITearEffect... effects) {
		super(type);
		this.effects = effects;
	}
	
	public NonNullList<EntityTear> onCreation(NonNullList<EntityTear> tears) {
		for(ITearEffect effect : effects) {
			effect.onCreation(tears);
		}
		return tears;
	}
	
	public void onUpdate(EntityTear tear) {
		for(ITearEffect effect : effects) {
			effect.onUpdate(tear);
		}
	}
	
	public void onHit(NonNullList<EntityTear> tears, RayTraceResult result) {
		for(ITearEffect effect : effects) {
			effect.onHit(tears, result);
		}
	}

}
