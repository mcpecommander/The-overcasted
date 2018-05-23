package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.animations.EyesLookAt;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityBasilisk extends EntityMob implements IAnimated {

	private int margin, lastJumpTime;
	private boolean flag = false;
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityBasilisk.class);

	static {
		EntityBasilisk.animHandler.addAnim(Reference.MODID, "basilisk_idle", "basilisk", true);
		EntityBasilisk.animHandler.addAnim(Reference.MODID, "basilisk_jump", "basilisk", false);
		EntityBasilisk.animHandler.addAnim(Reference.MODID, "look_at", new EyesLookAt("RightEye", "LeftEye"));
	}

	public EntityBasilisk(World worldIn) {
		super(worldIn);
		this.setSize(1.1f, 1.3f);

	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (DamageSource.FALL.equals(source)) {
			return false;
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAILookIdle(this));
	}

	@Override
	protected float getJumpUpwardsMotion() {
		return 0.65f;
	}

	@Override
	protected void jump() {
		super.jump();
		double yaw = ((this.rotationYawHead + 90) * Math.PI) / 180;
		double z = Math.sin(yaw);
		double x = Math.cos(yaw);
		motionX += x;
		motionZ += z;
		flag = false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		double yaw = ((this.rotationYawHead + 90) * Math.PI) / 180;
		double z = Math.sin(yaw);
		double x = Math.cos(yaw);
		this.getAnimationHandler().animationsUpdate(this);
		if (this.onGround) {
			if (ticksExisted >= lastJumpTime + 4) this.lastJumpTime = ticksExisted;
			if (margin++ > 5)
				this.jump();
		} else {
			margin = 0;
		}
		if (this.isWorldRemote()) {

			if (margin == 0 && this.onGround) {
				AnimationHelper.startAnimation(animHandler, this, "basilisk_idle");
			}
			AnimationHelper.startAnimation(animHandler, this, "look_at");

			if (!this.onGround) {
				AnimationHelper.stopStartAnimation(animHandler, this, "basilisk_idle", "basilisk_jump");
			} else {
				if (ticksExisted <= lastJumpTime + 3) {
					
					for (int i = 0; i < 15; i++) {
						this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
								this.posX + x + this.getRNG().nextGaussian() / 10, this.posY + this.getEyeHeight(),
								this.posZ + z + this.getRNG().nextGaussian() / 10,
								x / 10 + this.getRNG().nextGaussian() / 2, 0.01,
								z / 10 + this.getRNG().nextGaussian() / 2);

					}
					this.lastJumpTime = -1;
				}
			}

		} else {
			if (ticksExisted <= lastJumpTime + 3 && !flag && onGround) {
				EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX + x*12, this.posY,
						this.posZ + z*12);
				entityareaeffectcloud.setOwner(this);
				entityareaeffectcloud.setRadius(5.0F);
				entityareaeffectcloud.setRadiusOnUse(-0.5F);
				entityareaeffectcloud.setWaitTime(10);
				entityareaeffectcloud
						.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
				entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.POISON, 100, 0));
				this.world.spawnEntity(entityareaeffectcloud);
				flag= true;
			}
		}

	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityBasilisk.animHandler;
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public double getX() {
		return this.posX;
	}

	@Override
	public double getY() {
		return this.posY;
	}

	@Override
	public double getZ() {
		return this.posZ;
	}

	@Override
	public boolean isWorldRemote() {
		return world.isRemote;
	}

}
