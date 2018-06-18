package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySnowRat extends EntityBaseAnimated {
	
	private static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntitySnowRat.class);

	static{
		EntitySnowRat.animHandler.addAnim(Reference.MODID, "rat_idle", "snow_rat", true);
		EntitySnowRat.animHandler.addAnim(Reference.MODID, "rat_walk", "snow_rat", true);
	}
	
	public EntitySnowRat(World worldIn) {
		super(worldIn);
		this.setSize(.8f, .5f);
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntitySnowRat.animHandler;
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.1d, true));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.animHandler.animationsUpdate(this);
		if(isWorldRemote()) {
			float x = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			boolean moving = x != 0 && (x > 0.001f || x < -0.001f);
			if(moving) {
				AnimationHelper.stopAnimation(animHandler, this, "rat_idle");
				AnimationHelper.startAnimation(animHandler, this, "rat_walk");
			}else {
				AnimationHelper.stopAnimation(animHandler, this, "rat_walk");
				AnimationHelper.startAnimation(animHandler, this, "rat_idle");
			}
		}
	}

}
