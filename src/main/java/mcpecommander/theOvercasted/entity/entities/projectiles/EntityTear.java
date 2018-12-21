package mcpecommander.theOvercasted.entity.entities.projectiles;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import akka.japi.Effect;
import mcpecommander.theOvercasted.animationSystem.Animation;
import mcpecommander.theOvercasted.capability.follower.FollowerProvider;
import mcpecommander.theOvercasted.capability.follower.IFollower;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.StatsProvider;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityBasicFamiliar;
import mcpecommander.theOvercasted.item.effects.Attribute;
import mcpecommander.theOvercasted.item.effects.IEffect;
import mcpecommander.theOvercasted.item.effects.TearEffect;
import net.minecraft.entity.Entity;
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

public class EntityTear extends Entity {
	
	private static final DataParameter<Boolean> FALLING = EntityDataManager.<Boolean>createKey(EntityTear.class, DataSerializers.BOOLEAN);
	
	public EntityPlayer shooter;
	protected Entity target;
	protected Vec3d originPos;
	protected float damage, lerpTime, range, distanceMoved;
	public int[] effectInts;
	protected List<IEffect> effects;
	
	public EntityTear(World world) {
		super(world);
		this.setSize(.2f, .2f);
	}
	
	public EntityTear(World world, EntityPlayer shooter, int[] effects) {
		this(world);
		this.shooter = shooter;
		this.effectInts = effects;
		this.effects = getEffects(effects);
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
	public void onUpdate() {
		super.onUpdate();
		if(effects != null) {
			for(IEffect effect : effects) {
				effect.onEntityTearUpdate(this);
			}
		}
		if(!world.isRemote) onServerUpdate();
		
		if(this.ticksExisted > 200) {
			this.setDead();
		}
		
		this.posX += motionX;
		this.posY += motionY;
		this.posZ += motionZ;
		distanceMoved += Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);

		if(isFalling()) {
			motionY -= 0.1d;
			motionX *= 0.8d;
			motionZ *= 0.8d;
			this.velocityChanged = true;
		}else {
			motionY = -0.03f;
		}

	}
	
	protected void onServerUpdate(){
		double speed = Math.sqrt(motionX * motionX + motionZ * motionZ);
		if (distanceMoved > this.getRange() - this.getRange() * (0.1f + speed / 10f)) {
			this.dataManager.set(FALLING, true);
			this.dataManager.setDirty(FALLING);
		}
		if (isEntityInsideOpaqueBlock() || onGround) {
			if(effects != null) {
				for(IEffect effect : effects) {
					effect.onEntityTearHit(this, null, this.getPosition());
				}
			}
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
			if(effects != null) {
				for(IEffect effect : effects) {
					effect.onEntityTearHit(this, list.get(0), this.getPosition());
				}
			}
			this.setDead();
			list.get(0).attackEntityFrom(DamageSource.GENERIC, damage);
		}
	}
	
	protected void filterList(List<Entity> list) {
		IFollower follower = shooter.getCapability(FollowerProvider.FOLLOWER_CAP, null);
		if(follower != null) {
			EntityBasicFamiliar familiar = (EntityBasicFamiliar) ((WorldServer) world).getEntityFromUuid(follower.getFollower());
			if(familiar != null) {
				list.remove(familiar);
				getFamiliar(familiar, list);
			}
		}
	}
	
	
	protected void getFamiliar(EntityBasicFamiliar familiar, List<Entity> list) {
		if(familiar.getFollower() != null) {
			list.remove(familiar.getFollower());
			getFamiliar((EntityBasicFamiliar) familiar.getFollower(), list);
		}
		
	}

	public final List<Entity> getCollidedEntities(@Nullable Entity entityIn, AxisAlignedBB aabb)
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
	
	protected static Vec3d getDistanceVectorNormalized(Vec3d start, Vec3d end){
		return end.subtract(start).normalize();
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(FALLING, false);
	}
	
	protected boolean isFalling() {
		return this.dataManager.get(FALLING);
	}
	
	protected void setFalling() {
		this.dataManager.set(FALLING, true);
		this.dataManager.setDirty(FALLING);
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
		this.effects = getEffects(effectInts);
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
	
	public static List<IEffect> getEffects(int[] items) {
		List<IEffect> list = Lists.newArrayList();
		if(items.length == 0) {
			return list;
		}else {
			for(int x = 0; x < items.length; x++) {
				if(items[x] == 0)break;
				
				Attribute attribute = Attribute.getAttributeById(items[x]);
				for(IEffect effect : attribute.getEffects()) {
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
