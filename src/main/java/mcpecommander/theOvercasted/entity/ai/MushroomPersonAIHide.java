package mcpecommander.theOvercasted.entity.ai;

import mcpecommander.theOvercasted.entity.entities.EntityMushroomPerson;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MushroomPersonAIHide extends EntityAIBase{
	
	private EntityMushroomPerson hider;
	World world;
	private double distance;
	
	public MushroomPersonAIHide(EntityMushroomPerson hider, double distance) {
		this.hider = hider;
		this.world = hider.world;
		this.distance = distance;
	}

	@Override
	public boolean shouldExecute() {
		if(this.hider != null && !this.hider.isDead) {
			return this.hider.isBaby() && this.hider.getAttacking() == 0;
		}
		return false;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(this.hider != null && !this.hider.isDead) {
			return this.hider.getAttacking() == 2;
		}
		return false;
	}
	
	@Override
	public void resetTask() {
		this.hider.setAttacking((byte) 0);
	}
	
	@Override
	public void updateTask() {
		EntityPlayer player = this.world.getNearestPlayerNotCreative(hider, distance);
		if(player != null && !player.isDead) {
			if(this.hider.getEntitySenses().canSee(player)) {
				this.hider.setAttacking((byte) 2);
			}
		}else {
			this.hider.setAttacking((byte) 0);
		}
	}

}
