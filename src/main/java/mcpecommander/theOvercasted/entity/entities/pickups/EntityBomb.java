package mcpecommander.theOvercasted.entity.entities.pickups;

import mcpecommander.theOvercasted.util.RayTracedExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;

public class EntityBomb extends Entity {
	
	private EntityLivingBase placer;
	public int fuseCountDown;
	private int originChunkX, originChunkZ;

	public EntityBomb(World worldIn) {
		super(worldIn);
		this.setSize(0.7f, 0.7f);
	}
	
	public EntityBomb(World worldIn, EntityLivingBase placer, Vec3d position) {
		this(worldIn);
		this.placer = placer;
		this.fuseCountDown = 40;
		this.setPosition(position.x, position.y, position.z);
		this.originChunkX = this.getPosition().getX() >> 4;
		this.originChunkZ = this.getPosition().getZ() >> 4;
		
	}

	@Override
	protected void entityInit() {

	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public boolean canBePushed() {
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(!source.damageType.contains("explosion")) {
			return false;
		}
		if(this.isEntityInvulnerable(source)) {
			return false;
		}else {
			if(source.getImmediateSource() != null) {
				double xRatio = source.getImmediateSource().posX - this.posX;
				double zRatio = source.getImmediateSource().posZ - this.posZ;
				knockBack(source.getImmediateSource(), 0.4f, xRatio, zRatio);
			}
			this.markVelocityChanged();
			return true;
		}
	}
	
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio)
    {

		this.isAirBorne = true;
		float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
		this.motionX /= 2.0D;
		this.motionZ /= 2.0D;
		this.motionX -= xRatio / (double) f * (double) strength;
		this.motionZ -= zRatio / (double) f * (double) strength;

		if (this.onGround) {
			this.motionY /= 2.0D;
			this.motionY += (double) strength;

			if (this.motionY > 0.4000000059604645D) {
				this.motionY = 0.4000000059604645D;
			}
		}

    }
	
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		this.fuseCountDown--;
		if(!world.isRemote) {
			if(this.originChunkX == this.chunkCoordX && this.originChunkZ == this.chunkCoordZ) {
				if(this.fuseCountDown < 0) {
					this.setDead();
					RayTracedExplosion explosion = new RayTracedExplosion(getPosition(), world, 3);
					explosion.causer = placer;
					explosion.doExplosionA();
					explosion.doExplosionB();
				}
			}else {
				this.setDead();
			}
		}else {
			for(int x = 0; x < 3; x++)
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.7d, this.posZ, this.rand.nextDouble()/5d, 0.2, this.rand.nextDouble()/5d);
		}
		
		this.motionX *= 0.9900000095367432D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9900000095367432D;
		
		if (Math.abs(this.motionX) < 0.003D)
        {
            this.motionX = 0.0D;
        }

        if (Math.abs(this.motionY) < 0.003D)
        {
            this.motionY = 0.0D;
        }

        if (Math.abs(this.motionZ) < 0.003D)
        {
            this.motionZ = 0.0D;
        }
        
        if(!this.onGround) {
        	this.motionY -= 0.05d;
        }
        
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("placer")) {
			if(this.world.getPlayerEntityByUUID(compound.getUniqueId("placer")) != null) {
				this.placer = this.world.getPlayerEntityByUUID(compound.getUniqueId("placer"));
			}else {
				if(!world.isRemote) {
					//The placer should be either null or a living entity base (I hope).
					this.placer = (EntityLivingBase) ((WorldServer) world).getEntityFromUuid(compound.getUniqueId("placer"));
				}
			}
		}
		if(compound.hasKey("fuse_countdown", Constants.NBT.TAG_INT)) {
			this.fuseCountDown = compound.getInteger("fuse_countdown");
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setUniqueId("placer", placer.getPersistentID());
		compound.setInteger("fuse_countdown", this.fuseCountDown);
		compound.setInteger("origin_chunkx", this.originChunkX);
		compound.setInteger("origin_chunkz", this.originChunkZ);

	}

}
