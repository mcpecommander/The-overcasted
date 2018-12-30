package mcpecommander.theOvercasted.entity.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityNarrowRoomOverseer extends EntityOverseer {

	private static final DataParameter<EnumFacing> DIRECTION = EntityDataManager.<EnumFacing>createKey(EntityNarrowRoomOverseer.class, DataSerializers.FACING);
	
	public EntityNarrowRoomOverseer(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected boolean hasPlayer() {
		for(EntityPlayer player : this.world.playerEntities) {
			if ((player.chunkCoordX == this.chunkCoordX && player.chunkCoordZ == this.chunkCoordZ)
					|| (player.chunkCoordX == this.chunkCoordX + getDirection().getDirectionVec().getX()
					&& player.chunkCoordZ == this.chunkCoordZ + getDirection().getDirectionVec().getZ())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isChunkUnderThisControl(Chunk chunk) {
		return (chunk.x == this.chunkCoordX && chunk.z == this.chunkCoordZ)
				|| (chunk.x == this.chunkCoordX + getDirection().getDirectionVec().getX()
				&& chunk.z == this.chunkCoordZ + getDirection().getDirectionVec().getZ());
	}
	
	public EnumFacing getDirection() {
		return this.dataManager.get(DIRECTION);
	}
	
	public void setDirection(EnumFacing facing) {
		this.dataManager.set(DIRECTION, facing);
		this.dataManager.setDirty(DIRECTION);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(DIRECTION, EnumFacing.UP);
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("direction", this.dataManager.get(DIRECTION).getIndex());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.dataManager.set(DIRECTION, EnumFacing.getFront(compound.getInteger("direction")));
	}

}
