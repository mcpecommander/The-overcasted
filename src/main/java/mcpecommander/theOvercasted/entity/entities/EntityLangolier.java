package mcpecommander.theOvercasted.entity.entities;

import com.google.common.base.Predicate;
import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.ai.LangolierAIAttack;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityLangolier extends EntityMob implements IAnimated{
	
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityLangolier.class);
	//A variable that auto syncs to the client (only to the client).
	private static final DataParameter<Boolean> EATING = EntityDataManager.<Boolean>createKey(EntityLangolier.class, DataSerializers.BOOLEAN);
	
	static {
		EntityLangolier.animHandler.addAnim(Reference.MODID, "langolier_idle", "langolier", true);
		EntityLangolier.animHandler.addAnim(Reference.MODID, "langolier_eat", "langolier", false);
		EntityLangolier.animHandler.addAnim(Reference.MODID, "eat", "langolier", false);
	}

	public EntityLangolier(World worldIn) {
		super(worldIn);
		this.setSize(1.3f, 1.3f);
	}
	
	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40D);
		//For some reason a non-moving mob still needs movement speed to jump.
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(EATING, false);
	}
	
	@Override
	protected void initEntityAI(){
		this.tasks.addTask(1, new LangolierAIAttack(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0] ));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 4, true, true, new Predicate<EntityPlayer>() {
        	@Override
        	public boolean apply(EntityPlayer input) {
        		return input.onGround;
        	}
        }));
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.rotationYawHead = this.rotationYaw;
		this.animHandler.animationsUpdate(this);

		if(isWorldRemote()) {
			//Do all animation on client for less impact on the game.
			if(!this.getEating()) {
				AnimationHelper.stopAnimation(animHandler, this, "eat");
				AnimationHelper.startAnimation(animHandler, this, "langolier_idle");
			}else {
				AnimationHelper.stopStartAnimation(animHandler, this, "langolier_idle", "eat");
			}
			
		}else {

		}
	}
	
	public void setEating(boolean mode) {
		this.dataManager.set(EATING, mode);
	}
	
	public boolean getEating() {
		return this.dataManager.get(EATING).booleanValue();
	}
	
	//Set the mob to never take fall damage and to never set a revenge target (That is a bug but will fix later).
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.equals(DamageSource.FALL)) {
			return false;
		}
		boolean flag = super.attackEntityFrom(source, amount);
		if(this.getRevengeTarget() != null) {
			this.setRevengeTarget(null);
		}
		return flag;
	}
	
	public void jump(double jumpFactor) {
		motionY = jumpFactor;
		this.isAirBorne = true;
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityLangolier.animHandler;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public double getX() {
		return posX;
	}

	@Override
	public double getY() {
		return posY;
	}

	@Override
	public double getZ() {
		return posZ;
	}

	@Override
	public boolean isWorldRemote() {
		return world.isRemote;
	}

}
