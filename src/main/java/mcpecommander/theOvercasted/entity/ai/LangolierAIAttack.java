package mcpecommander.theOvercasted.entity.ai;

import java.util.List;

import com.google.common.base.Predicate;

import mcpecommander.theOvercasted.entity.entities.EntityLangolier;
import mcpecommander.theOvercasted.init.ModPotions;
import mcpecommander.theOvercasted.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LangolierAIAttack extends EntityAIBase {

	private EntityLangolier langolier;
	/**
	 * phase 0 : idle, phase 1 : pull, phase 2 : jumping, phase 3 : stasis.
	 */
	private byte phase = 0;
	private int delay;
	// A small margin of error for the fall.
	private int error = 0;
	World world;

	public LangolierAIAttack(EntityLangolier langolier) {
		this.langolier = langolier;
		this.world = langolier.world;

	}

	@Override
	public boolean shouldExecute() {
		if (this.langolier != null && !this.langolier.isDead) {
			if (this.langolier.getAttackTarget() != null && !this.langolier.getAttackTarget().isDead) {
				return this.phase == 0;
			}
		}
		return false;
	}

	@Override
	public boolean shouldContinueExecuting() {
		if (this.langolier != null && !this.langolier.isDead) {
			if (this.langolier.getAttackTarget() != null && !this.langolier.getAttackTarget().isDead) {
				return this.phase != 0;
			}
		}
		return false;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		this.phase = 1;
		this.delay = this.langolier.getRNG().nextInt(this.phase * 20) + 10;
	}

	@Override
	public void resetTask() {
		super.resetTask();
		this.phase = 0;
		this.error = 0;
		this.langolier.setEating(false);
	}

	@Override
	public boolean isInterruptible() {
		return false;
	}

	@Override
	public void updateTask() {
		super.updateTask();
		EntityLivingBase target = this.langolier.getAttackTarget();
		this.langolier.faceEntity(target, 10, 10);
		if (this.delay-- <= 0) {
			// There are four phases but since we are checking that it is never 0, it is
			// impossible that it is 0.
			if (this.phase == 1) {
				this.langolier.jump(0.74f);
				this.phase = 2;
			} else if (this.phase == 2) {
				if (this.langolier.onGround && this.error++ == 3) {
					error = 0;
					for (int i = 0; i < 360; i += 10) {
						double x = Math.sin(i);
						double z = Math.cos(i);
						((WorldServer) this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.langolier.posX,
								this.langolier.getEntityBoundingBox().minY + 0.3, this.langolier.posZ, 1, x * 3, 0,
								z * 3, 0.1f,
								Block.getStateId(this.world.getBlockState(this.langolier.getPosition().down())));
						this.phase = 0;
					}
					this.langolier.playSound(ModSounds.fall, 2f, .5f);
					for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class,
							this.langolier.getEntityBoundingBox().grow(6), new Predicate<EntityPlayer>() {

								@Override
								public boolean apply(EntityPlayer input) {
									return input.onGround;
								}

							})) {
						player.addPotionEffect(new PotionEffect(ModPotions.potionStasis, 100, 0));
						player.capabilities.allowFlying = false;
					}
					this.phase = 3;
					this.langolier.setEating(true);
				} else if (this.langolier.motionY < 0) {
					this.langolier.motionY *= 3.5;
				}
			} else {
				List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class,
						this.langolier.getEntityBoundingBox().grow(6), new Predicate<EntityPlayer>() {

							@Override
							public boolean apply(EntityPlayer input) {
								return input.isPotionActive(ModPotions.potionStasis);
							}

						});
				if (list.isEmpty()) {
					this.phase = 0;
					return;
				}
				for (EntityPlayer player : list) {
					player.capabilities.allowFlying = false;
					Vec3d motion = new Vec3d(this.langolier.posX - player.posX, this.langolier.posY - player.posY,
							this.langolier.posZ - player.posZ).normalize();
					EntityPlayerMP mp = (EntityPlayerMP) player;
					mp.connection.player.motionX = motion.x / 10;
					//mp.connection.player.motionY = motion.y / 10;
					mp.connection.player.motionZ = motion.z / 10;
					mp.velocityChanged = true;
					if (checkAndPerformAttack(player, this.langolier.getDistanceSq(player))) {
						player.removePotionEffect(ModPotions.potionStasis);
						this.langolier.setEating(false);
						this.phase = 0;
					}
				}

			}
		}

	}

	protected boolean checkAndPerformAttack(EntityLivingBase entity, double currentDistance) {
		double d0 = this.getAttackReachSqr(entity);

		if (currentDistance <= d0) {
			boolean flag = this.langolier.attackEntityAsMob(entity);
			if (flag) {
				entity.knockBack(this.langolier, 1f, this.langolier.posX - entity.posX, this.langolier.posZ - entity.posZ);
			}
			return flag;

		}
		return false;
	}

	protected double getAttackReachSqr(EntityLivingBase attackTarget) {
		return (double) (this.langolier.width * this.langolier.width);
	}

}
