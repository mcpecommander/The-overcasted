package mcpecommander.theOvercasted.entity.entities;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mcpecommander.theOvercasted.maze.RoomLayout;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityLargeRoomOverseer extends EntityOverseer {

	public EntityLargeRoomOverseer(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void serializeRoom() {

		RoomLayout layout = new RoomLayout( "wide", saveEntities(), saveDecorations());
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
		ResourceLocation[][] mobs = new ResourceLocation[30][30];
		for(Entity entity : getEntities()) {
			mobs[entity.getPosition().getX()%32 - 1][entity.getPosition().getZ()%32 - 1] = EntityRegistry.getEntry(entity.getClass()).getRegistryName();
		}
		return mobs;
	}
	
	@Override
	protected ResourceLocation[][] saveDecorations() {
		ResourceLocation[][] deco = new ResourceLocation[30][30];
		for(int x = 1; x <31; x++) {
			for(int z = 1; z <31; z++) {
				BlockPos pos = new BlockPos(this.chunkCoordX * 16 + x, 65, this.chunkCoordZ * 16 + z);
				if(world.isAirBlock(pos)) continue;
				IBlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				deco[x - 1][z - 1] = block.getRegistryName();
			}
		}
		return deco;
	}
	
	@Override
	protected void toggleDoors(boolean open) {
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				for(int x = 7; x <= 8; x ++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + i) * 16 + x, 65, (this.chunkCoordZ + j) * 16 );
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
				for(int z = 7; z <= 8; z++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + i) * 16, 65, (this.chunkCoordZ + j) * 16 + z);
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
				for(int x = 7; x <= 8; x ++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + i) * 16 + x, 65, (this.chunkCoordZ + j) * 16 + 15);
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
				for(int z = 7; z <= 8; z++) {
					BlockPos pos = new BlockPos((this.chunkCoordX + i) * 16 + 15, 65, (this.chunkCoordZ + j) * 16 + z);
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
						((BlockDoor) block).toggleDoor(world, pos, open);
					}
				}
			}
		}
		
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
