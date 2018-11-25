package mcpecommander.theOvercasted.util;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Sets;

import mcpecommander.theOvercasted.block.BlockTNT;
import mcpecommander.theOvercasted.registryHandler.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RayTracedExplosion {
	
	private final BlockPos pos;
	private final World world;
	private final int size;
	public Set<BlockPos> affectedPos = Sets.newHashSet();
	
	public RayTracedExplosion(BlockPos pos, World world, int size) {
		this.pos = pos.add(.5, .5, .5);
		this.world = world;
		this.size = size;
	}
	
	public void doExplosionA() {
		for(int x = 0; x <= 360; x += 5) {
			Vec3d start = new Vec3d(pos);
			Vec3d end = new Vec3d(pos.add(Math.cos(x) * 3, 0, Math.sin(x) * 3));
			affectedPos.addAll(rayTraceBlocks(start, end, true, true));
			
			if(world.isRemote) {
				Tessellator tess = Tessellator.getInstance();
				BufferBuilder buffer = tess.getBuffer();
				
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
				
				buffer.pos(start.x, start.y, start.z).color(255, 255, 255, 1).endVertex();
				buffer.pos(end.x, end.y, end.z).color(255, 255, 255, 1).endVertex();
				buffer.pos(start.x, start.y, start.z).color(255, 255, 255, 1).endVertex();
				buffer.pos(end.x, end.y, end.z).color(255, 255, 255, 1).endVertex();
				
				tess.draw();
			}
		}
		
		
		for(int x = -size; x < size + 1; x ++) {
			for(int z = -size; z < size + 1; z++) {
				if(x == 0 && z == 0) continue;
				BlockPos tempPos = pos.add(x, 0, z);
				if(!this.world.isAirBlock(tempPos)) {
					IBlockState iblockState = this.world.getBlockState(tempPos);
					if(iblockState.getBlock().getExplosionResistance(world, pos, (Entity)null, null) < 10f) {
						affectedPos.add(tempPos);
					}
				}
			}
		}
	}
	
	public void doExplosionB() {
		this.world.playSound((EntityPlayer)null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		if(this.world.isRemote) {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.pos.getX(), this.pos.getY(),
					this.pos.getZ(), 1.0D, 0.0D, 0.0D);
		} else {
			((WorldServer) this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.pos.getX(),
					this.pos.getY(), this.pos.getZ(), 5, 1.0D, 0.0D, 0.0D, 0.1);
			
		}
		
		if(!affectedPos.isEmpty()) {
			for(BlockPos pos : affectedPos) {
				if(world.getBlockState(pos).getBlock().getExplosionResistance(world, pos, (Entity)null, null) < 10f) {
					Block block = world.getBlockState(pos).getBlock();
					if(block instanceof BlockTNT) {
						world.setBlockState(pos, world.getBlockState(pos).withProperty(Registry.TNT.READY, true));
						world.scheduleUpdate(pos, block, 2);
						continue;
					}
					if (block.canDropFromExplosion(null))
		            {
		                block.dropBlockAsItemWithChance(this.world, pos, this.world.getBlockState(pos), 1.0F / this.size, 0);
		            }
		
		            block.onBlockExploded(this.world, pos, null);
				}
			}
		}
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(null,
				this.world.getBlockState(this.pos).getBoundingBox(world, pos).grow(size));
		for (Entity entity : list){
			if(!entity.isImmuneToExplosions()) {
//				DamageSource damage = DamageSource.causeExplosionDamage(new Explosion(world, null, 0, 0, 0, 0, null));
				entity.attackEntityFrom(DamageSource.MAGIC, 1 * size);
				
			}
		}
	}
	
	//Taken and modified from #World
	@Nonnull
	public Set<BlockPos> rayTraceBlocks(Vec3d vec31, Vec3d vec32, boolean stopOnLiquid, boolean ignoreFirstBlock)
    {
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
						&& block.canCollideCheck(iblockstate, stopOnLiquid) && !ignoreFirstBlock)
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
                    if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z))
                    {
                        return null;
                    }

                    if (startX == endX && startY == endY && startZ == endZ)
                    {
                    	if(raytraceresult2 != null && raytraceresult2.typeOfHit == Type.BLOCK)
                        set.add(raytraceresult2.getBlockPos());
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
                        if (block1.canCollideCheck(iblockstate1, stopOnLiquid))
                        {
                            RayTraceResult nextBlockRaytrace = iblockstate1.collisionRayTrace(this.world, blockpos, vec31, vec32);

                            if (nextBlockRaytrace != null && nextBlockRaytrace.typeOfHit == Type.BLOCK)
                            {
                                set.add(nextBlockRaytrace.getBlockPos());
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
