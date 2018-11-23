package mcpecommander.theOvercasted.entity.entities;

import com.google.common.base.Predicate;
import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.ai.ArmourStandAIAttack;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityArmourStand extends EntityBaseAnimated {

	//The animation handler from the api.
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityArmourStand.class);
	//A variable that auto syncs to the client (only to the client).
	private static final DataParameter<Byte> ATTACKING = EntityDataManager.<Byte>createKey(EntityArmourStand.class, DataSerializers.BYTE);

	//A special way that the api handles adding its animations.
	static {
		EntityArmourStand.animHandler.addAnim(Reference.MODID, "stand_set", "armour_stand", false);
		//EntityArmourStand.animHandler.addAnim(Reference.MODID, "stand_unset", "stand_set");
		EntityArmourStand.animHandler.addAnim(Reference.MODID, "stand_slash", "armour_stand", false);
		EntityArmourStand.animHandler.addAnim(Reference.MODID, "walk", "armour_stand", true);
		EntityArmourStand.animHandler.addAnim(Reference.MODID, "sword_slash", "armour_stand", false);
	}

	public EntityArmourStand(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 1.975F);
	}
	
	@Override
	public boolean getCanSpawnHere() {
		// TODO Auto-generated method stub
		return super.getCanSpawnHere();
	}
	
	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ATTACKING, Byte.valueOf((byte) 0));
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(1, new ArmourStandAIAttack(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0] ));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 1, true, false, new Predicate<EntityPlayer>() {

        	//To give the feeling of a jump scare.
			@Override
			public boolean apply(EntityPlayer input) {
				return getPosition().distanceSqToCenter(input.posX, input.posY, input.posZ) < 9;
			}

			
		}) {
        	@Override
        	public void startExecuting() {
        		EntityLivingBase temp = this.taskOwner.getAttackTarget();
        		super.startExecuting();
        		//The jump scare.
        		if(temp == null && this.taskOwner.getAttackTarget() != null) {
        			animHandler.networkStartAnimation(Reference.MODID, "stand_set", 0, (IAnimated) this.taskOwner, false);
        			jump();
        		}
        	}
        });
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		IEntityLivingData flag = super.onInitialSpawn(difficulty, livingdata);
		//I am too lazy to fix the left handed renderer so instead make everything right handed.
		if (isLeftHanded()) {
			this.setLeftHanded(false);

		}
		return flag;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		//Mandatory animation update method from the api.
//		this.getAnimationHandler().animationsUpdate(this);

		//running the walking animations from the server to keep it from desyncing when stopping it in the AI tasks.
		if(!this.isWorldRemote()) {
			this.setAttacking(this.getAttackTarget() != null || this.getRevengeTarget() != null ? this.getAttacking() : (byte)0);
			float x = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			boolean moving = x != 0 && (x > 0.001f || x < -0.001f);
			//Moving animations.
			if(moving) {
				if(getAttacking() == 1) {
					AnimationHelper.networkStartAnimation(animHandler, this, "walk", false);
				}
			}else{
				AnimationHelper.networkStopAnimation(animHandler, this, "walk", false);
				
			}
		}

	}
	
	//Modified to jump towards the target entity instead.
	@Override
	public void jump() {
		this.motionY = (double)this.getJumpUpwardsMotion() * 0.6d;

		EntityLivingBase leapTarget = this.getAttackTarget();
		if(leapTarget != null) {
			double d0 = leapTarget.posX - this.posX;
	        double d1 = leapTarget.posZ - this.posZ;
	        float f = MathHelper.sqrt(d0 * d0 + d1 * d1);

	        if ((double)f >= 1.0E-4D)
	        {
	            this.motionX += d0 / (double)f * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
	            this.motionZ += d1 / (double)f * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
	            this.motionX = MathHelper.clamp(motionX, -.65, .65);
	            this.motionZ = MathHelper.clamp(motionZ, -.65, .65);
	        }

		}
        this.isAirBorne = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
	}
	
	/**
	 * 0: idle, 1: attacking.
	 * @param byte mode
	 */
	public void setAttacking(byte mode) {
		if(this.dataManager.get(ATTACKING).byteValue() != mode) {
			this.dataManager.set(ATTACKING, mode);
			//Add the sword when no longer idle.
			if(mode == 0) {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
			}else {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
			}
		}
	}
	
	/**
	 * 0: idle, 1: attacking.
	 * @return byte mode.
	 */
	public byte getAttacking() {
		return this.dataManager.get(ATTACKING).byteValue();
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityArmourStand.animHandler;
	}

}
