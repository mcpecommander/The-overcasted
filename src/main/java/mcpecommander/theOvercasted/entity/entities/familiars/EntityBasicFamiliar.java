package mcpecommander.theOvercasted.entity.entities.familiars;

import java.util.UUID;

import mcpecommander.theOvercasted.capability.follower.FollowerProvider;
import mcpecommander.theOvercasted.capability.follower.IFollower;
import mcpecommander.theOvercasted.entity.ai.EntityAIFamiliarFollow;
import mcpecommander.theOvercasted.entity.entities.projectiles.EntitySlaveTear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityBasicFamiliar extends EntityLiving {
	
	private EntityLivingBase master, follower;
	public UUID masterID, followerID;

	public EntityBasicFamiliar(World worldIn) {
		super(worldIn);
		this.setSize(.5f, 1f);
	}
	
	public EntityBasicFamiliar(World world, EntityLivingBase master) {
		this(world);
		this.master = master;
		this.masterID = master.getPersistentID();
		if(master instanceof EntityPlayer) {
			IFollower follower = master.getCapability(FollowerProvider.FOLLOWER_CAP, null);
			if(follower != null) {
				follower.setFollower(getPersistentID());
			}
		}else {
			((EntityBasicFamiliar) master).setFollower(this);
			((EntityBasicFamiliar) master).followerID = this.getPersistentID();
		}
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(1, new EntityAIFamiliarFollow(this));
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.motionY = 0;
		if(!world.isRemote) {
			if(this.master == null || this.master.isDead) {
				this.master = this.world.getPlayerEntityByUUID(masterID);
				if(master == null)
				this.master = (EntityLivingBase) ((WorldServer) this.world).getEntityFromUuid(masterID);
				if(ticksExisted > 20) this.setDead();
			}else {
				if(ticksExisted % 3 == 0) {
					if(posY > master.posY + (master instanceof EntityPlayer ? .5f: -0.03f)) {
						this.motionY -= 0.03f;
					}else {
						this.motionY += 0.03f;
					}
				}
				this.rotationYaw = master.rotationYaw;
			}
			if(follower == null && followerID != null) {
				this.follower = (EntityLivingBase) ((WorldServer) this.world).getEntityFromUuid(followerID);
			}
		}else {
			
		}
		
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if(entityIn instanceof EntityBasicFamiliar) {
			super.collideWithEntity(entityIn);
		}
	}
	
	public void shootTear() {
		EntitySlaveTear tear = new EntitySlaveTear(world, this, new int[0]);
		tear.shoot(this.getPositionVector().addVector(0, getEyeHeight() - .3, 0), this,
				this.getLookVec(), 17f, 3.5f, 1.1f);
		world.spawnEntity(tear);
		if(follower != null ) {
			((EntityBasicFamiliar) follower).shootTear();
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if(!world.isRemote) {
			if (followerID != null)
				compound.setUniqueId("follower", followerID);
			compound.setUniqueId("master", masterID);
		}
		
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if(!world.isRemote) {
			this.masterID = compound.getUniqueId("master");
			this.followerID = compound.getUniqueId("follower");
		}
		
	}

	public static EntityLivingBase getLastFollower(EntityPlayer playerIn) {
		IFollower follower = playerIn.getCapability(FollowerProvider.FOLLOWER_CAP, null);
		if(follower != null) {
			EntityBasicFamiliar familiar = (EntityBasicFamiliar) ((WorldServer) playerIn.world).getEntityFromUuid(follower.getFollower());
			if(familiar != null) {
				return getLastFollower(familiar);
			}else {
				return playerIn;
			}
		}
		return playerIn;
	}
	
	private static EntityBasicFamiliar getLastFollower(EntityBasicFamiliar familiar) {
		if(familiar.getFollower() != null) {
			return getLastFollower((EntityBasicFamiliar) familiar.getFollower());
		}else {
			return familiar;
		}
	}
	
	public EntityLivingBase getMaster() {
		return master;
	}

	public void setMaster(EntityPlayer master) {
		this.master = master;
	}
	
	public EntityLivingBase getFollower() {
		return follower;
	}

	public void setFollower(EntityLivingBase follower) {
		this.follower = follower;
	}

}
