package mcpecommander.theOvercasted.util;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import mcpecommander.theOvercasted.block.BlockRock;
import mcpecommander.theOvercasted.block.BlockTNT;
import mcpecommander.theOvercasted.block.BlockRock.EnumType;
import mcpecommander.theOvercasted.registryHandler.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

//Only extending to avoid incompatibilities with #IBlockState raycasting methods.
public class RayTracedExplosion extends Explosion{
	
	private final Vec3d pos;
	private final World world;
	private final int size;
	public Entity causer;
	public Set<BlockPos> affectedPos = Sets.newHashSet();
	
	public RayTracedExplosion(BlockPos pos, World world, int size) {
		super(world, null, pos.getX(), pos.getY(), pos.getZ(), (float)size, true, true);
		this.pos = new Vec3d(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
		this.world = world;
		this.size = size;
	}
	
	public void doExplosionA() {
		
		for(int x = 0; x <= 360; x += 5) {
			
			Vec3d start = pos;
			Vec3d end = pos.addVector(Math.cos(x) * size, 0, Math.sin(x) * size);
			affectedPos.addAll(rayTraceBlocks(start, end, true, true));
		}
	}
	
	public void doExplosionB() {
		this.world.playSound((EntityPlayer)null, this.pos.x, this.pos.y, this.pos.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		if(this.world.isRemote) {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.pos.x, this.pos.y,
					this.pos.z, 1.0D, 0.0D, 0.0D);
		} else {
			((WorldServer) this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.pos.x,
					this.pos.y, this.pos.z, 5, 1.0D, 0.0D, 0.0D, 0.1);
			
		}
		
		if(!affectedPos.isEmpty()) {
			for(BlockPos pos : affectedPos) {
				if(world.getBlockState(pos).getBlock().getExplosionResistance(world, pos, (Entity)null, this) < 10f) {
					Block block = world.getBlockState(pos).getBlock();
					if(block instanceof BlockTNT) {
						world.setBlockState(pos, world.getBlockState(pos).withProperty(Registry.TNT.READY, true));
						world.scheduleUpdate(pos, block, 2);
						continue;
					}else if (block instanceof BlockRock && world.getBlockState(pos).getValue(Registry.ROCK.TYPE ) == EnumType.BOMB) {
						world.setBlockState(pos, world.getBlockState(pos).withProperty(Registry.ROCK.READY, true));
						world.scheduleUpdate(pos, block, 2);
						continue;
					}
					if (block.canDropFromExplosion(this))
		            {
		                block.dropBlockAsItemWithChance(this.world, pos, this.world.getBlockState(pos), 1.0F / this.size, 0);
		            }
		
		            block.onBlockExploded(this.world, pos, this);
				}
			}
		}
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(null,
				new AxisAlignedBB(this.pos.subtract(.5, .5, .5), this.pos.addVector(.5, .5, .5)).grow(size - 1));
		
		//Stolen the damage code from vanilla even though they do some magical calculations but it works.
		for (Entity entity : list){
			if(!entity.isImmuneToExplosions()) {
				entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), 4f * size);
				double effectiveDamageDistance = entity.getDistance(pos.x, pos.y, pos.z) / size;
				if (effectiveDamageDistance <= 1.0D)
                {
                    double distanceX = entity.posX - pos.x;
                    double distanceY = entity.posY + (double)entity.getEyeHeight() - pos.y;
                    double distanceZ = entity.posZ - pos.z;
                    double distanceRoot = (double)MathHelper.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
                    if (distanceRoot != 0.0D)
                    {
                    	distanceX = distanceX / distanceRoot;
                    	distanceY = distanceY / distanceRoot;
                    	distanceZ = distanceZ / distanceRoot;
                        double blockProtection = (double)this.world.getBlockDensity(pos, entity.getEntityBoundingBox());
                        double damageCalculator = (1.0D - effectiveDamageDistance) * blockProtection;
                        entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float)((int)((damageCalculator * damageCalculator + damageCalculator) / 7.0D * (double)size + 1.0D)));
                        double d11 = damageCalculator;

                        if (entity instanceof EntityLivingBase)
                        {
                            d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, damageCalculator);
                        }
                        
                        entity.motionX += distanceX * d11;
                        entity.motionY += distanceY * d11;
                        entity.motionZ += distanceZ * d11;
                    }
				
                }
			}
		}
	}
	
	@Override
	public EntityLivingBase getExplosivePlacedBy() {
		if(this.causer != null && this.causer instanceof EntityLivingBase) {
			return (EntityLivingBase) this.causer;
		}
		return super.getExplosivePlacedBy();
	}
	
	//Taken and modified from #World
	//Observe the impossible nature of vanilla minecraft code with a hint of 
	//my own personal shit code to give the most unreadable undebuggable code on this earth.
	//Touch this code at your risk.
	//May you rest in peace.
	@Nonnull
	public Set<BlockPos> rayTraceBlocks(Vec3d vec31, Vec3d vec32, boolean addLiquid, boolean ignoreFirstBlock)
    {
		float remainingPower = 13f;
		Set<BlockPos> set = Sets.newHashSet();
        if (!Double.isNaN(vec31.x) && !Double.isNaN(vec31.y) && !Double.isNaN(vec31.z))
        {
            if (!Double.isNaN(vec32.x) && !Double.isNaN(vec32.y) && !Double.isNaN(vec32.z))
            {
                int endX = MathHelper.floor(vec32.x);
                int endY = MathHelper.floor(vec32.y);
                int endZ = MathHelper.floor(vec32.z);
                int startX = MathHelper.floor(vec31.x);
                int startY = MathHelper.floor(vec31.y);
                int startZ = MathHelper.floor(vec31.z);
                BlockPos blockpos = new BlockPos(startX, startY, startZ);
                IBlockState iblockstate = this.world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();

				if (iblockstate.getCollisionBoundingBox(this.world, blockpos) != Block.NULL_AABB
						&& block.canCollideCheck(iblockstate, addLiquid) && !ignoreFirstBlock)
                {
                    RayTraceResult raytraceresult = iblockstate.collisionRayTrace(this.world, blockpos, vec31, vec32);

                    if (raytraceresult != null && raytraceresult.typeOfHit == Type.BLOCK)
                    {
                        set.add(raytraceresult.getBlockPos());
                    }
                }

                RayTraceResult raytraceresult2 = null;
                int tempTimer = 200;

                while (tempTimer-- >= 0)
                {

                    if (startX == endX && startY == endY && startZ == endZ)
                    {
                    	if(raytraceresult2 != null && raytraceresult2.typeOfHit == Type.BLOCK)
                        set.add(raytraceresult2.getBlockPos());
                    	return set;
                    }

                    boolean flag2 = true;
                    boolean flag = true;
                    boolean flag1 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if (endX > startX)
                    {
                        d0 = (double)startX + 1.0D;
                    }
                    else if (endX < startX)
                    {
                        d0 = (double)startX + 0.0D;
                    }
                    else
                    {
                        flag2 = false;
                    }

                    if (endY > startY)
                    {
                        d1 = (double)startY + 1.0D;
                    }
                    else if (endY < startY)
                    {
                        d1 = (double)startY + 0.0D;
                    }
                    else
                    {
                        flag = false;
                    }

                    if (endZ > startZ)
                    {
                        d2 = (double)startZ + 1.0D;
                    }
                    else if (endZ < startZ)
                    {
                        d2 = (double)startZ + 0.0D;
                    }
                    else
                    {
                        flag1 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = vec32.x - vec31.x;
                    double d7 = vec32.y - vec31.y;
                    double d8 = vec32.z - vec31.z;

                    if (flag2)
                    {
                        d3 = (d0 - vec31.x) / d6;
                    }

                    if (flag)
                    {
                        d4 = (d1 - vec31.y) / d7;
                    }

                    if (flag1)
                    {
                        d5 = (d2 - vec31.z) / d8;
                    }

                    if (d3 == -0.0D)
                    {
                        d3 = -1.0E-4D;
                    }

                    if (d4 == -0.0D)
                    {
                        d4 = -1.0E-4D;
                    }

                    if (d5 == -0.0D)
                    {
                        d5 = -1.0E-4D;
                    }

                    EnumFacing enumfacing;

                    if (d3 < d4 && d3 < d5)
                    {
                        enumfacing = endX > startX ? EnumFacing.WEST : EnumFacing.EAST;
                        vec31 = new Vec3d(d0, vec31.y + d7 * d3, vec31.z + d8 * d3);
                    }
                    else if (d4 < d5)
                    {
                        enumfacing = endY > startY ? EnumFacing.DOWN : EnumFacing.UP;
                        vec31 = new Vec3d(vec31.x + d6 * d4, d1, vec31.z + d8 * d4);
                    }
                    else
                    {
                        enumfacing = endZ > startZ ? EnumFacing.NORTH : EnumFacing.SOUTH;
                        vec31 = new Vec3d(vec31.x + d6 * d5, vec31.y + d7 * d5, d2);
                    }

                    startX = MathHelper.floor(vec31.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
                    startY = MathHelper.floor(vec31.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
                    startZ = MathHelper.floor(vec31.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
                    blockpos = new BlockPos(startX, startY, startZ);
                    IBlockState iblockstate1 = this.world.getBlockState(blockpos);
                    Block block1 = iblockstate1.getBlock();

                    if (iblockstate1.getMaterial() == Material.PORTAL || iblockstate1.getCollisionBoundingBox(this.world, blockpos) != Block.NULL_AABB)
                    {
                        if (block1.canCollideCheck(iblockstate1, addLiquid))
                        {
                        	
                            RayTraceResult nextBlockRaytrace = iblockstate1.collisionRayTrace(this.world, blockpos, vec31, vec32);

                            if (nextBlockRaytrace != null && nextBlockRaytrace.typeOfHit == Type.BLOCK && !set.contains(nextBlockRaytrace.getBlockPos()))
                            {
                            	if(block1.getExplosionResistance(world, blockpos, null, this) <= remainingPower) {
                            		set.add(nextBlockRaytrace.getBlockPos());
                            		remainingPower -= block1.getExplosionResistance(world, blockpos, null, this);
                            	}else {
                            		break;
                            	}
                                
                            }
                        }
                        else
                        {
                            raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
                        }
                    }
                }
                if(raytraceresult2 != null && raytraceresult2.typeOfHit == Type.BLOCK)
                set.add(raytraceresult2.getBlockPos());
            }
        }

		return set;
    }

}
