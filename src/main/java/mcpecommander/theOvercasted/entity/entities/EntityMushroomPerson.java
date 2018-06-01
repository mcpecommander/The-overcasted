package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.ai.MushroomPersonAIAttack;
import mcpecommander.theOvercasted.entity.ai.MushroomPersonAIHide;
import mcpecommander.theOvercasted.init.ModBlocks;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityMushroomPerson extends EntityBaseAnimated {

	// The animation handler from the api.
	private static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityMushroomPerson.class);
	private static final DataParameter<Byte> ATTACKING = EntityDataManager.<Byte>createKey(EntityMushroomPerson.class,
			DataSerializers.BYTE);
	private static final DataParameter<Boolean> BABY = EntityDataManager.<Boolean>createKey(EntityMushroomPerson.class,
			DataSerializers.BOOLEAN);

	static {
		EntityMushroomPerson.animHandler.addAnim(Reference.MODID, "mushroom_walk", "mushroom_person", true);
		EntityMushroomPerson.animHandler.addAnim(Reference.MODID, "mushroom_hide", "mushroom_person", false);
		EntityMushroomPerson.animHandler.addAnim(Reference.MODID, "mushroom_punch", "mushroom_person", false);
	}

	public EntityMushroomPerson(World worldIn) {
		super(worldIn);

	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ATTACKING, (byte) 0);
		this.dataManager.register(BABY, false);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D) {
			@Override
			public boolean shouldExecute() {
				boolean flag = super.shouldExecute();
				if (flag && !isBaby() && getAttacking() != 2) {
					return true;
				}
				return false;
			}
		});
		this.tasks.addTask(3, new EntityAILookIdle(this));
	}

	public boolean isBaby() {
		return this.dataManager.get(BABY);
	}

	public void setBaby(boolean baby) {
		this.dataManager.set(BABY, baby);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
	}

	public void setAttacking(byte mode) {
		this.dataManager.set(ATTACKING, mode);
	}

	public byte getAttacking() {
		return this.dataManager.get(ATTACKING);
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityMushroomPerson.animHandler;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		boolean flag = this.getRNG().nextBoolean();
		if (livingdata instanceof IsBaby) {
			flag = this.isBaby();
		} else {
			this.dataManager.set(BABY, flag);
		}
		if (!flag) {
			this.tasks.addTask(1, new MushroomPersonAIAttack(this, 1.0d, false));
			this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
		} else {
			this.tasks.addTask(1, new MushroomPersonAIHide(this, 4));
		}
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
	protected void onDeathUpdate() {
		++this.deathTime;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		if (isWorldRemote()) {
			for (int i = 0; i < 5; i++) {
				this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST,
						this.posX + this.getRNG().nextGaussian() / 2 - .25,
						(this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) / 2 + this.posY
								+ this.getRNG().nextGaussian() / 2 - .25,
						this.posZ + this.getRNG().nextGaussian() / 2 - .25, (this.getRNG().nextGaussian() - .5) / 15,
						(this.getRNG().nextGaussian() - .5) / 15, (this.getRNG().nextGaussian() - .5) / 15,
						Block.getStateId(ModBlocks.mushroomBlock.getDefaultState()));
			}
		}

		if (this.deathTime == 10) {
			if (!this.world.isRemote) {
				if ((this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot()
						&& this.world.getGameRules().getBoolean("doMobLoot"))) {
					int i = this.getExperiencePoints(this.attackingPlayer);
					i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
					while (i > 0) {
						int j = EntityXPOrb.getXPSplit(i);
						i -= j;
						this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
					}
				}

				if (this.isBaby()) {
					BlockPos pos = this.getPosition();
					if (ModBlocks.mushroomBlock.canPlaceBlockAt(world, pos)) {
						world.setBlockState(pos, ModBlocks.mushroomBlock.getDefaultState());
						WorldServer worldS = (WorldServer) world;
						double d0 = this.getRNG().nextGaussian() * 0.02D;
						double d1 = this.getRNG().nextGaussian() * 0.02D;
						double d2 = this.getRNG().nextGaussian() * 0.02D;
						worldS.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
								(double) ((float) pos.getX() + this.getRNG().nextFloat()),
								(double) pos.getY() + (double) this.getRNG().nextFloat()
										* world.getBlockState(pos).getBoundingBox(world, pos).maxY,
								(double) ((float) pos.getZ() + this.getRNG().nextFloat()), 5, d0, d1, d2, 0.1d);
					}
				} else {
					for (int i = 0; i < 2; i++) {
						EntityMushroomPerson mushroom = new EntityMushroomPerson(world);
						mushroom.setBaby(true);
						mushroom.onInitialSpawn(world.getDifficultyForLocation(getPosition()), new IsBaby());
						mushroom.setPositionAndRotation(posX + (this.getRNG().nextDouble() * 2 - 1), posY,
								posZ + (this.getRNG().nextDouble() * 2 - 1), this.rotationYaw, this.rotationPitch);
						world.spawnEntity(mushroom);
					}
				}
			}

			this.setDead();

			for (int k = 0; k < 20; ++k) {
				double d2 = this.rand.nextGaussian() * 0.02D;
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST,
						this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
						this.posY + (double) (this.rand.nextFloat() * this.height),
						this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d2, d0,
						d1, Block.getStateId(Blocks.RED_MUSHROOM_BLOCK.getDefaultState()));
			}
		}
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);
		if (BABY.equals(key)) {
			if ((boolean) this.dataManager.get(key)) {
				this.setSize(.8f, 1.1f);
			} else {
				this.setSize(.8f + 1 / 2.8f, 2.1f);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("baby", this.dataManager.get(BABY));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("baby")) {
			this.dataManager.set(BABY, compound.getBoolean("baby"));
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.getAnimationHandler().animationsUpdate(this);
		if (isWorldRemote()) {
			float x = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			boolean moving = x != 0 && (x > 0.001f || x < -0.001f);
			if (isBaby()) {
				if (this.getAttacking() == (byte) 2) {
					AnimationHelper.stopAnimation(animHandler, this, "mushroom_walk");
					AnimationHelper.startHoldAnimation(animHandler, this, "mushroom_hide");
				} else if (moving && !this.getAnimationHandler()
						.isHoldAnimationActive(Reference.MODID + ":mushroom_hide", this)) {
					AnimationHelper.startAnimation(animHandler, this, "mushroom_walk");
				} else {
					AnimationHelper.stopAnimation(animHandler, this, "mushroom_walk");
					if (this.getAttacking() == 0) {
						AnimationHelper.stopAnimation(animHandler, this, "mushroom_hide");
					}
				}
			} else {
				if (this.getAttacking() == (byte) 1) {
					AnimationHelper.stopStartAnimation(animHandler, this, "mushroom_walk", "mushroom_punch");
				} else if (moving
						&& !this.getAnimationHandler().isAnimationActive(Reference.MODID, "mushroom_punch", this)) {
					AnimationHelper.startAnimation(animHandler, this, "mushroom_walk");
				} else {
					AnimationHelper.stopAnimation(animHandler, this, "mushroom_walk");
				}
			}
		} else {

		}
	}

	private class IsBaby implements IEntityLivingData {

	}

}
