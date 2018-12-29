package mcpecommander.theOvercasted.entity.entities;

import org.apache.commons.lang3.tuple.ImmutablePair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityLargeRoomOverseer extends EntityOverseer {

	public EntityLargeRoomOverseer(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void updateEntitiesList() {
		hasPlayer = hasPlayer();
		currentListSize = 0;
		for(int i = 0; i < 2; i++)
			for(int j = 0; j < 2; j++) {
				for(ClassInheritanceMultiMap<Entity> list : this.world.getChunkFromChunkCoords(this.chunkCoordX + i, this.chunkCoordZ + j).getEntityLists()) {
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
	public boolean isChunkUnderThisControl(Chunk chunk) {
		return (chunk.x == this.chunkCoordX && chunk.z == this.chunkCoordZ)
				|| (chunk.x == this.chunkCoordX + 1 && chunk.z == this.chunkCoordZ)
				|| (chunk.x == this.chunkCoordX && chunk.z == this.chunkCoordZ + 1)
				|| 	(chunk.x == this.chunkCoordX + 1 && chunk.z == this.chunkCoordZ + 1);
	}
	
	@Override
	protected boolean hasPlayer() {
		for(EntityPlayer player : this.world.playerEntities) {
			if ((player.chunkCoordX == this.chunkCoordX && player.chunkCoordZ == this.chunkCoordZ)
					|| (player.chunkCoordX == this.chunkCoordX + 1 && player.chunkCoordZ == this.chunkCoordZ)
					|| (player.chunkCoordX == this.chunkCoordX && player.chunkCoordZ == this.chunkCoordZ + 1)
					|| 	(player.chunkCoordX == this.chunkCoordX + 1 && player.chunkCoordZ == this.chunkCoordZ + 1)) {
				return true;
			}
		}
		return false;
	}

}
