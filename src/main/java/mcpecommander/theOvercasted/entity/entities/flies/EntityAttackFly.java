package mcpecommander.theOvercasted.entity.entities.flies;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityAttackFly extends EntityFly {

	public EntityAttackFly(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5d);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(1, new EntityAIAttackMelee(this, .7, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false));
	}
	
	@Override
	public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
		this.setBaseHeight((float) (y + 1.4f));
	}
	
	@Override
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
		super.setLocationAndAngles(x, y, z, yaw, pitch);
		this.setBaseHeight((float) (y + 1.4f));
	}


}
