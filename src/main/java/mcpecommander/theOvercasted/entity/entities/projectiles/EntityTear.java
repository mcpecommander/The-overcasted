package mcpecommander.theOvercasted.entity.entities.projectiles;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mcpecommander.theOvercasted.animationSystem.Animation;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.StatsProvider;
import mcpecommander.theOvercasted.item.effects.Attribute;
import mcpecommander.theOvercasted.item.effects.Effect;
import mcpecommander.theOvercasted.item.effects.Effect.ActionType;
import mcpecommander.theOvercasted.item.effects.TearEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
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

public class EntityTear extends Entity implements IProjectile {
	
	private static final DataParameter<Boolean> FALLING = EntityDataManager.<Boolean>createKey(EntityTear.class, DataSerializers.BOOLEAN);
	
	public EntityPlayer shooter;
	private Entity target;
	private Vec3d originPos;
	private float damage, lerpTime, range, distanceMoved;
	public int[] effectInts;
	private List<Effect> continuousEffects;
	
	public EntityTear(World world) {
		super(world);
		this.setSize(.2f, .2f);
	}
	
	public EntityTear(World world, EntityPlayer shooter, int[] effects) {
		this(world);
		this.shooter = shooter;
		this.effectInts = effects;
		this.continuousEffects = getContinuousEffects(effects);
	}
	
	public void shoot(Vec3d pos, EntityPlayer shooter, Vec3d lookVec) {
		this.posX = pos.x;
		this.posY = pos.y;
		this.posZ = pos.z;
		originPos = pos;
		IStats stats = shooter.getCapability(StatsProvider.STATS_CAP, null);
		if(stats != null) {
			float range = stats.getRange();
			float projectileSpeed = stats.getProjectileSpeed();
			float inaccuracy = range * projectileSpeed / 10f;
			this.damage = stats.getDamage();
			this.range = stats.getRange()/2f;
			this.motionX = lookVec.x;
			this.motionZ = lookVec.z;
			this.motionX += this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
			this.motionZ += this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
			this.motionX *= projectileSpeed /2f;
			this.motionZ *= projectileSpeed /2f;
			this.rotationYaw = shooter.rotationYawHead;
			this.rotationPitch = shooter.rotationPitch;
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		
	}	
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(continuousEffects != null) {
			for(Effect effect : continuousEffects) {
				if(effect instanceof TearEffect) {
					((TearEffect) effect).onUpdate(this);
				}
			}
		}
		this.posX += motionX;
		this.posY += motionY;
		this.posZ += motionZ;
		distanceMoved += Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		
		if(this.ticksExisted > 200) {
			this.setDead();
		}
		double speed = Math.sqrt(motionX * motionX + motionZ * motionZ);
		if(!world.isRemote) {
			if (distanceMoved > this.getRange() - this.getRange() * (0.1f + speed/10f)) {
				this.dataManager.set(FALLING, true);
				this.dataManager.setDirty(FALLING);
			}
			if(isEntityInsideOpaqueBlock() || onGround) {
				this.setDead();
			}
			if(target != null && target.isEntityAlive()) {
				lerpTime = MathHelper.clamp(lerpTime + .01f, 0f, 1f);
				Vec3d targetMotion = getDistanceVectorNormalized(getPositionVector(), target.getPositionVector().addVector(0, target.getEyeHeight(), 0));
				motionX = Animation.lerp(motionX, targetMotion.x, lerpTime);
				motionY = Animation.lerp(motionY, targetMotion.y, lerpTime);
				motionZ = Animation.lerp(motionZ, targetMotion.z, lerpTime);
				this.velocityChanged = true;
			}else {
				target = null;
			}
			List<Entity> list = this.getCollisionBoxes(shooter, this.getEntityBoundingBox().offset(this.getPositionVector()));
			if(!list.isEmpty()) {
				this.setDead();
				list.get(0).attackEntityFrom(DamageSource.GENERIC, damage);
			}
				
		}else {
			
		}

		if(isFalling()) {
			motionY -= 0.1d;
			motionX *= 0.8d;
			motionZ *= 0.8d;
			this.velocityChanged = true;
		}else {
			motionY = -0.03f;
		}

	}
	
	
	public List<Entity> getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb)
    {
        List<Entity> list = Lists.<Entity>newArrayList();

        if (entityIn != null)
        {
            List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(entityIn, aabb.grow(0.25D));

            for (int i = 0; i < list1.size(); ++i)
            {
                Entity entity = list1.get(i);

				AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox();
				if (axisalignedbb != null && axisalignedbb.intersects(aabb)) {
					list.add(entity);
				}

            }
        }

        return list;
    }
	
	private static Vec3d getDistanceVectorNormalized(Vec3d start, Vec3d end){
		return end.subtract(start).normalize();
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(FALLING, false);
	}
	
	private boolean isFalling() {
		return this.dataManager.get(FALLING);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("shooter")) {
			shooter = world.getPlayerEntityByUUID(compound.getUniqueId("shooter"));
		}
		damage = compound.getFloat("damage");
		NBTTagList list = compound.getTagList("origin", 6);
		originPos = new Vec3d(list.getDoubleAt(0), list.getDoubleAt(1), list.getDoubleAt(2));
		this.ticksExisted = compound.getInteger("ticks_existed");
		if(compound.hasKey("target")) {
			this.target = ((WorldServer) world).getEntityFromUuid(compound.getUniqueId("target"));
		}
		this.effectInts = compound.getIntArray("effects");
		this.continuousEffects = getContinuousEffects(effectInts);
		this.distanceMoved = compound.getFloat("distance_flew");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setUniqueId("shooter", shooter.getPersistentID());
		compound.setFloat("damage", damage);
		compound.setTag("origin", this.newDoubleNBTList(originPos.x, originPos.y, originPos.z));
		compound.setInteger("ticks_existed", this.ticksExisted);
		if(target != null) {
			compound.setUniqueId("target", target.getPersistentID());
		}
		compound.setIntArray("effects", effectInts);
		compound.setFloat("distance_flew", distanceMoved);
	}
	
	public List<Effect> getContinuousEffects(int[] items) {
		List<Effect> list = Lists.newArrayList();
		if(items.length == 0) {
			return list;
		}else {
			for(int x = 0; x < items.length; x++) {
				if(items[x] == 0)break;
				
				Attribute attribute = Attribute.getAttributeById(items[x]);
				for(Effect effect : attribute.getEffects()) {
					if(effect.getType() == ActionType.CONTINUOUS)
						list.add(effect);
				}
			}
			return list;
		}
	}

	public void setTarget(Entity entity) {
		this.target = entity;
	}

	public boolean hasTarget() {
		return target != null && target.isEntityAlive();
	}

	public float getDistanceMoved() {
		return distanceMoved;
	}

	public float getRange() {
		return range;
	}

}
