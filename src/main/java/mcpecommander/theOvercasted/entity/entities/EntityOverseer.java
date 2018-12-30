package mcpecommander.theOvercasted.entity.entities;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mcpecommander.theOvercasted.events.CapabilityEvents;
import mcpecommander.theOvercasted.init.ModRoomLayouts;
import mcpecommander.theOvercasted.maze.RoomLayout;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityOverseer extends Entity {
	
	private static final DataParameter<Integer> LAYOUT = EntityDataManager.<Integer>createKey(EntityOverseer.class, DataSerializers.VARINT);
	
	protected boolean hasPlayer, isRoomCleared, finishedSerialization, finishedSpawning;
	private final List<Entity> entities = Lists.newArrayList();
	
	public EntityOverseer(World worldIn) {
		super(worldIn);
	}

	
	@Override
	public void onUpdate() {
		super.onUpdate();
		updateEntitiesList();
		if(world.isRemote) {
			if(hasPlayer && !finishedSerialization && CapabilityEvents.isUPressed) {
				serializeMobs();
			}
		}else {
			if(hasPlayer && !finishedSpawning) {
				spawnMobs();
			}
		}
		
	}
	
	private void updateEntitiesList() {
		hasPlayer = hasPlayer();
		isRoomCleared = true;
		for(int i = 0; i < getEntities().size(); i++) {
			Entity entity = getEntities().get(i);
			if(entity == null || entity.isDead) {
				getEntities().remove(i);
			}else {
				if(entity instanceof IRoomRequirement) {
					isRoomCleared = false;
					entity.updateBlocked = !hasPlayer;
					entity.setInvisible(!hasPlayer);
				}
				
			}
		}
		if(!world.isRemote) {
			if(isRoomCleared || !hasPlayer) {
				toggleDoors(true);
			}else {
				toggleDoors(false);
			}
		}
		
		
		if(ticksExisted % 10 == 0) {
			finishedSerialization = false;
		}

	}

	protected void serializeMobs() {
		finishedSerialization = true;

		RoomLayout layout = new RoomLayout( "normal", saveEntities(), saveDecorations());
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
	
	protected ResourceLocation[][] saveEntities(){
		ResourceLocation[][] mobs = new ResourceLocation[14][14];
		for(Entity entity : getEntities()) {
			mobs[entity.getPosition().getX()%16 - 1][entity.getPosition().getZ()%16 - 1] = EntityRegistry.getEntry(entity.getClass()).getRegistryName();
		}
		return mobs;
	}
	
	protected ResourceLocation[][] saveDecorations(){
		ResourceLocation[][] deco = new ResourceLocation[14][14];
		for(int x = 1; x <15; x++) {
			for(int z = 1; z <15; z++) {
				BlockPos pos = new BlockPos(this.chunkCoordX * 16 + x, 65, this.chunkCoordZ * 16 + z);
				if(world.isAirBlock(pos)) continue;
				IBlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				deco[x - 1][z - 1] = block.getRegistryName();
			}
		}
		return deco;
	}
	
	protected void toggleDoors(boolean open) {
		for(int x = 7; x <= 8; x ++) {
			BlockPos pos = new BlockPos(this.chunkCoordX * 16 + x, 65, this.chunkCoordZ * 16 );
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
				((BlockDoor) block).toggleDoor(world, pos, open);
			}
		}
		for(int z = 7; z <= 8; z++) {
			BlockPos pos = new BlockPos(this.chunkCoordX * 16, 65, this.chunkCoordZ * 16 + z);
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
				((BlockDoor) block).toggleDoor(world, pos, open);
			}
		}
		for(int x = 7; x <= 8; x ++) {
			BlockPos pos = new BlockPos(this.chunkCoordX * 16 + x, 65, this.chunkCoordZ * 16 + 15);
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
				((BlockDoor) block).toggleDoor(world, pos, open);
			}
		}
		for(int z = 7; z <= 8; z++) {
			BlockPos pos = new BlockPos(this.chunkCoordX * 16 + 15, 65, this.chunkCoordZ * 16 + z);
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockDoor && state.getValue(BlockDoor.OPEN) != open) {
				((BlockDoor) block).toggleDoor(world, pos, open);
			}
		}
		

	}
	
	public void spawnMobs() {
		this.finishedSpawning = true;
		RoomLayout layout = ModRoomLayouts.basement_normal_layouts.getObject(this.getRoomLayout());
		if(layout != null) {
			ResourceLocation[][] mobs = layout.getMobs();
			for(int i = 0; i < mobs.length; i++) {
				for(int j = 0; j < mobs[i].length; j ++) {
					if(mobs[i][j] == null) {
						continue;
					}else {
						
						EntityEntry entry = ForgeRegistries.ENTITIES.getValue(mobs[i][j]);
						Entity entity = null;
						if (entry != null)
							entity = entry.newInstance(world);
						
						// We multiple chunk coords by 16 to get the world pos and then we add the
						// layout position to get the in-room pos but we add 1 to correct the layout
						// starting at 0 and we add 0.5 to center the entity on the block.
						entity.setLocationAndAngles(this.chunkCoordX * 16 + i + 1.5, 65,
								this.chunkCoordZ * 16 + j + 1.5, 0, 0);
						this.world.spawnEntity(entity);
						this.getEntities().add(entity);

					}
				}
			}
			
		}
	}
	
	public boolean isChunkUnderThisControl(Chunk chunk) {
		return chunk.x == this.chunkCoordX && chunk.z == this.chunkCoordZ;
	}

	public boolean isRoomCleared() {
		return isRoomCleared;
	}
	
	protected boolean hasPlayer() {
		for(EntityPlayer player : this.world.playerEntities) {
			if(player.chunkCoordX == this.chunkCoordX && player.chunkCoordZ == this.chunkCoordZ) {
				return true;
			}
		}
		return false;
	}

	public void setRoomLayout(int roomLayout) {
		this.dataManager.set(LAYOUT, roomLayout);
		this.dataManager.setDirty(LAYOUT);
	}
	
	public int getRoomLayout() {
		return this.dataManager.get(LAYOUT);
	}

	public List<Entity> getEntities() {
		return entities;
	}


	@Override
	protected void entityInit() {
		this.dataManager.register(LAYOUT, -1);

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.finishedSpawning = compound.getBoolean("spawning");
		this.setRoomLayout(compound.getInteger("room_layout"));

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("spawning", finishedSpawning);
		compound.setInteger("room_layout", this.getRoomLayout());
	}

}
