package mcpecommander.theOvercasted.entity.entities;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.world.World;

public class EntityOverseer extends Entity {
	
	protected boolean hasChunkChanged, hasPlayer;
	protected int prevListSize, currentListSize;
	protected final List<Entity> entities = Lists.newArrayList();

	public EntityOverseer(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		updateEntitiesList();
	}
	
	protected void updateEntitiesList() {
		hasPlayer = hasPlayer();
		currentListSize = 0;
		for(ClassInheritanceMultiMap<Entity> list : this.world.getChunkFromBlockCoords(getPosition()).getEntityLists()) {
			if(!list.isEmpty()) {
				currentListSize += list.size(); 
			}
			if(hasChunkChanged) {
				for(Entity entity: list) {
					if(entity != this) {
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
			entity.setInvisible(!hasPlayer);
		});
	}
	
	protected boolean hasPlayer() {
		for(EntityPlayer player : this.world.playerEntities) {
			if(player.chunkCoordX == this.chunkCoordX && player.chunkCoordZ == this.chunkCoordZ) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub

	}

}
