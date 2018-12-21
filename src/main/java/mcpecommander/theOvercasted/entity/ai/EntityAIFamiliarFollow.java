package mcpecommander.theOvercasted.entity.ai;

import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.animationSystem.Animation;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityBasicFamiliar;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIFamiliarFollow extends EntityAIBase {
	
	private EntityBasicFamiliar familiar;
	private EntityLivingBase master;
	private Vec3d prevPos, currentPos;
	World world;
	
	public EntityAIFamiliarFollow(EntityBasicFamiliar familiar) {
		this.familiar = familiar;
		this.world = familiar.world;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		this.master = familiar.getMaster();
		if(master != null) {
			if(master.isDead) {
				return false;
			}else {
				if (familiar.getDistance(master) > 1f) {
					return true;
				}
				return false;
			}
		}
		//Wait for master update;
		return false;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		
		return super.shouldContinueExecuting();
	}
	
	@Override
	public void resetTask() {
		super.resetTask();
		familiar.motionX = 0f;
		familiar.motionZ = 0f;
	}
	
	@Override
	public void updateTask() {
		super.updateTask();

		familiar.motionX = (master.posX - familiar.posX)/10f;
		familiar.motionZ = (master.posZ - familiar.posZ)/10f;
	}

}
