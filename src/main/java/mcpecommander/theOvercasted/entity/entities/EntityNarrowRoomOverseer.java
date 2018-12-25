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

public class EntityNarrowRoomOverseer extends EntityOverseer {

	private static final DataParameter<EnumFacing> DIRECTION = EntityDataManager.<EnumFacing>createKey(EntityNarrowRoomOverseer.class, DataSerializers.FACING);
	
	public EntityNarrowRoomOverseer(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void updateEntitiesList() {
		hasPlayer = hasPlayer();
		currentListSize = 0;
		for(int i = 0; i < 2; i++)
		for (ClassInheritanceMultiMap<Entity> list : this.world
				.getChunkFromChunkCoords(this.chunkCoordX + (i == 1 ? getDirection().getDirectionVec().getX() : 0)
						, this.chunkCoordZ + (i == 1 ? getDirection().getDirectionVec().getZ() : 0)).getEntityLists()) {
			if (!list.isEmpty()) {
				currentListSize += list.size();
			}
			if (hasChunkChanged) {
				for (Entity entity : list) {
					if (entity != this) {
						entities.add(entity);
					}

				}
			}
		}

		
		if(prevListSize != currentListSize) {
			hasChunkChanged = true;
			entities.clear();
			this.prevListSize = currentListSize;
		}else {
			hasChunkChanged = false;
		}
		entities.forEach(entity -> {
			entity.updateBlocked = !hasPlayer;
		});
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
