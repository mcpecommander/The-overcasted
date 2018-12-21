package mcpecommander.theOvercasted.entity.entities.projectiles;

import java.util.List;

import mcpecommander.theOvercasted.animationSystem.Animation;
import mcpecommander.theOvercasted.capability.follower.FollowerProvider;
import mcpecommander.theOvercasted.capability.follower.IFollower;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityBasicFamiliar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntitySlaveTear extends EntityTear {

	public EntityLivingBase shooter;

	public EntitySlaveTear(World worldIn) {
		super(worldIn);
		this.setSize(.2f, .2f);
	}

	public EntitySlaveTear(World worldIn, EntityLivingBase shooter, int[] effects) {
		this(worldIn);
		this.shooter = shooter;
		this.effectInts = effects;
		this.effects = getEffects(effects);
	}

	public void shoot(Vec3d pos, EntityLivingBase shooter, Vec3d lookVec, float range, float damage, float speed) {
		this.posX = pos.x;
		this.posY = pos.y;
		this.posZ = pos.z;
		originPos = pos;
		float inaccuracy = range * speed / 10f;
		this.damage = damage;
		this.range = range / 2f;
		this.motionX = lookVec.x;
		this.motionZ = lookVec.z;
		this.motionX += this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		this.motionZ += this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		this.motionX *= speed / 2f;
		this.motionZ *= speed / 2f;
		this.rotationYaw = shooter.rotationYawHead;
		this.rotationPitch = shooter.rotationPitch;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	protected void onServerUpdate() {
		double speed = Math.sqrt(motionX * motionX + motionZ * motionZ);

		if (distanceMoved > this.getRange() - this.getRange() * (0.1f + speed / 10f)) {
			setFalling();
		}
		if (isEntityInsideOpaqueBlock() || onGround) {
			this.setDead();
		}
		if (target != null && target.isEntityAlive()) {
			lerpTime = MathHelper.clamp(lerpTime + .01f, 0f, 1f);
			Vec3d targetMotion = getDistanceVectorNormalized(getPositionVector(),
					target.getPositionVector().addVector(0, target.getEyeHeight(), 0));
			motionX = Animation.lerp(motionX, targetMotion.x, lerpTime);
			motionY = Animation.lerp(motionY, targetMotion.y, lerpTime);
			motionZ = Animation.lerp(motionZ, targetMotion.z, lerpTime);
			this.velocityChanged = true;
		} else {
			target = null;
		}
		List<Entity> list = this.getCollidedEntities(shooter,
				this.getEntityBoundingBox().offset(this.getPositionVector()));
		filterList(list);
		if (!list.isEmpty()) {
			this.setDead();
			list.get(0).attackEntityFrom(DamageSource.GENERIC, damage);
		}

	}
	
	protected void filterList(List<Entity> list) {
		list.remove(((EntityBasicFamiliar) shooter).getMaster());
		EntityBasicFamiliar follower = (EntityBasicFamiliar) ((EntityBasicFamiliar) shooter).getFollower();
		if(follower != null) {
			list.remove(follower);
			getFamiliar(follower, list);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("shooter")) {
			shooter = (EntityLivingBase) ((WorldServer) world).getEntityFromUuid(compound.getUniqueId("shooter"));
		}
		damage = compound.getFloat("damage");
		NBTTagList list = compound.getTagList("origin", 6);
		originPos = new Vec3d(list.getDoubleAt(0), list.getDoubleAt(1), list.getDoubleAt(2));
		this.ticksExisted = compound.getInteger("ticks_existed");
		if(compound.hasKey("target")) {
			this.target = ((WorldServer) world).getEntityFromUuid(compound.getUniqueId("target"));
		}
		this.effectInts = compound.getIntArray("effects");
		this.effects = getEffects(effectInts);
		this.distanceMoved = compound.getFloat("distance_flew");

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("damage", damage);
		compound.setTag("origin", this.newDoubleNBTList(originPos.x, originPos.y, originPos.z));
		compound.setInteger("ticks_existed", this.ticksExisted);
		if(target != null) {
			compound.setUniqueId("target", target.getPersistentID());
		}
		compound.setIntArray("effects", effectInts);
		compound.setFloat("distance_flew", distanceMoved);
		compound.setUniqueId("shooter", shooter.getPersistentID());

	}

}
