package mcpecommander.theOvercasted.entity.entities;

import java.io.File;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.maze.DungeonWorldProvider;
import mcpecommander.theOvercasted.maze.RoomLayout;
import mcpecommander.theOvercasted.maze.RoomLayout.RoomType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityNarrowRoomOverseer extends EntityOverseer {
	
	public EntityNarrowRoomOverseer(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void serializeRoom() {
		RoomLayout layout = new RoomLayout( "narrow", getDirection().getDirectionVec().getY() != 0 ? RoomType.NARROW : RoomType.DOUBLE_NARROW, saveEntities(), saveDecorations());
		if(layout.getDeco() == null || layout.getMobs() == null) return;
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		builder.serializeNulls();
		Gson gson = builder.create();
		try {
			int length = new File("mods/overcasted/layouts").listFiles().length;
			FileWriter writer = new FileWriter("mods/overcasted/layouts/" + layout.getName()
					+ "_"+ length + ".json");
			layout.setName(layout.getName() + "_" + length);
			writer.write(gson.toJson(layout));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected ResourceLocation[][] saveEntities() {
		boolean south = this.getDirection() == EnumFacing.NORTH || this.getDirection() == EnumFacing.SOUTH;
		if(getDirection().getDirectionVec().getY() != 0) {
			TheOvercasted.logger.error("Can not serialize a single narrow room, use debug mode for a sure double narrow room.");
			return null;
		}else if (!south) {
			TheOvercasted.logger.error("Can not serialize a east-west narrow room, use debug mode to get both kinds of narrow rooms.");
			return null;
		}else {
			int addedZ = getDirection().getDirectionVec().getZ();
			ResourceLocation[][] mobs = new ResourceLocation[6][30];
			for(Entity entity : getEntities()) {
				
				if(entity.chunkCoordX == this.chunkCoordX && entity.chunkCoordZ == this.chunkCoordZ) {
					mobs[entity.getPosition().getX() % 16 - 5][entity.getPosition().getZ() % 16 - 1] = EntityRegistry
							.getEntry(entity.getClass()).getRegistryName();
				}else {
					mobs[entity.getPosition().getX() % 16 - 5][(entity.getPosition().getZ() + addedZ)% 16 - 1] = EntityRegistry
							.getEntry(entity.getClass()).getRegistryName();
				}
			}
			
			return mobs;
		}
	}
	
	@Override
	protected ResourceLocation[][] saveDecorations() {
		boolean south = this.getDirection() == EnumFacing.NORTH || this.getDirection() == EnumFacing.SOUTH;
		if(getDirection().getDirectionVec().getY() != 0) {
			TheOvercasted.logger.error("Can not serialize a single narrow room, use debug mode for a sure double narrow room.");
			return null;
		}else if (!south) {
			TheOvercasted.logger.error("Can not serialize a east-west narrow room, use debug mode to get both kinds of narrow rooms.");
			return null;
		}else {
			int addedZ = getDirection().getDirectionVec().getZ();
			ResourceLocation[][] deco = new ResourceLocation[6][30];
			if(addedZ > 0) {
				for(int x = 1; x <7; x++) {
					for(int z = 30; z >0; z--) {
						BlockPos pos = new BlockPos(this.chunkCoordX * 16 + x + 4, 65, this.chunkCoordZ * 16 + z);
						if (world.isAirBlock(pos))
							continue;
						IBlockState state = world.getBlockState(pos);
						Block block = state.getBlock();
						deco[x - 1][z - 1] = block.getRegistryName();
					}
				}
			}else {
				for(int x = 1; x <31; x++) {
					for(int z = 1; z <7; z++) {
						BlockPos pos = new BlockPos(this.chunkCoordX * 16 + x + 4, 65, this.chunkCoordZ * 16 + z);
						if (world.isAirBlock(pos))
							continue;
						IBlockState state = world.getBlockState(pos);
						Block block = state.getBlock();
						deco[x - 1][z - 1] = block.getRegistryName();

					}
				}
			}
			
			return deco;
		}
		
	}
	
	@Override
	protected void toggleDoors(boolean open) {
		int addedX = getDirection().getDirectionVec().getX();
		int addedZ = getDirection().getDirectionVec().getZ();
			for(int j = 0; j < 2; j++) {
				boolean next = j == 1;
				for(int x = 7; x <= 8; x ++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + (next ? addedX : 0)) * 16 + x, 65, (this.chunkCoordZ + (next ? addedZ : 0)) * 16 );
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
				for(int z = 7; z <= 8; z++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + (next ? addedX : 0)) * 16, 65, (this.chunkCoordZ + (next ? addedZ : 0)) * 16 + z);
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
				for(int x = 7; x <= 8; x ++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + (next ? addedX : 0)) * 16 + x, 65, (this.chunkCoordZ + (next ? addedZ : 0)) * 16 + 15);
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
				for(int z = 7; z <= 8; z++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + (next ? addedX : 0)) * 16 + 15, 65, (this.chunkCoordZ + (next ? addedZ : 0)) * 16 + z);
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
			}
		
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
	

	
	@Override
	protected void entityInit() {
		super.entityInit();
	}

}
