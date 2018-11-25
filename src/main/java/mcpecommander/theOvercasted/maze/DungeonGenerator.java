package mcpecommander.theOvercasted.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class DungeonGenerator {

	private BlockPos spawnPos;
	private int maxLength, maxRows, maxColumns, maxTunnels, spawnHeight, xChunkSpawn, zChunkSpawn;
	public Random random;
	private int[][] layout;
	private List<Pair<Integer, Integer>> specialRoomList;
	private EnumDungeonType type;
	private List<byte[][]> possibleRooms;
	
	//0: no room. 1: normal room. 2: narrow room. 3: Wide room a. 4: Wide room b. 5: Wide room c. 6: Wide room d.
	//7: special room (chest room). 8: special room (curse room). 9: special room (boss room).

	private DungeonGenerator(int maxLength, int maxRows, int maxColumns, int maxTunnels, int spawnHeight, EnumDungeonType type) {
		random = new Random();
		this.maxLength = MathHelper.clamp(maxLength, 3, Integer.MAX_VALUE);
		this.maxRows = maxRows;
		this.maxColumns = maxColumns;
		this.maxTunnels = maxTunnels;
		this.spawnHeight = spawnHeight;
		this.type = type;
		this.possibleRooms = getPossibleRoomLayouts();
		layout = this.createMap(maxRows, maxColumns, maxLength, maxTunnels);
		generateWideRooms();
		specialRoomList = generateSpecialRoomsList();
		addSpecialRoom(7, 2);
		addSpecialRoom(8, 5);
		addSpecialRoom(9, -1);
	}
	
	private DungeonGenerator() {
		random = new Random();
	}
	
	public static DungeonGenerator createDungeon(int maxLength, int maxRows, int maxColumns, int maxTunnels, int spawnHeight, EnumDungeonType type) {
		return new DungeonGenerator(maxLength, maxRows, maxColumns, maxTunnels, spawnHeight, type);
	}
	
	/**
	 * Recreates a dungeon from an NBT tag, called in {@link DungeonWorldProvider#init}
	 * @param tag An NBT tag that includes the necessary elements to recreate the dungeon. 
	 * @return A dungeon similar to the one created and saved in the NBT tag.
	 */
	public static DungeonGenerator fromNBT(NBTTagCompound tag) {
		DungeonGenerator maze = new DungeonGenerator();
		if(tag.hasKey("length", 3)) {
			maze.maxLength = tag.getInteger("length");
		}
		if(tag.hasKey("rows", 3)) {
			maze.maxRows = tag.getInteger("rows");
		}
		if(tag.hasKey("columns", 3)) {
			maze.maxColumns = tag.getInteger("columns");
		}
		if(tag.hasKey("tunnels", 3)) {
			maze.maxTunnels = tag.getInteger("tunnels");
		}
		if(tag.hasKey("x", 3) && tag.hasKey("y", 3) && tag.hasKey("z", 3)) {
			maze.spawnHeight = tag.getInteger("y");
			maze.spawnPos = new BlockPos(tag.getInteger("x"), maze.spawnHeight, tag.getInteger("z"));
			maze.xChunkSpawn = (maze.spawnPos.getX() - 8)/16;
			maze.zChunkSpawn = (maze.spawnPos.getZ() - 8)/16;
		}
		if(tag.hasKey("dungeon_type", 3)) {
			maze.type = EnumDungeonType.getTypeByID(tag.getInteger("dungeon_type"));
			maze.possibleRooms = maze.getPossibleRoomLayouts();
		}
		int[][] lay = new int[maze.maxRows][maze.maxColumns];
		for(int x = 0; x < maze.maxRows; x++) {
			if(tag.hasKey("dungeon" + x, 11)) {
				lay[x] = tag.getIntArray("dungeon" + x);
			}
		}
		maze.layout = lay;
		if(maze.layout == null) {
			return null;
		}
		return maze;
	}

	public int[][] createMap(int row, int column, int length, int maxTunnels) {
		int[][] array = empty2DArray(0, row, column);
		int currentRow = (int) Math.floor(Math.random() * row); // our current row - start at a random spot
		int currentColumn = (int) Math.floor(Math.random() * column); // our current column - start at a random spot
		this.xChunkSpawn = currentRow;
		this.zChunkSpawn = currentColumn;
		this.spawnPos = new BlockPos(currentRow * 16 + 8, spawnHeight, currentColumn * 16 + 8);
		Direction lastDirection = Direction.STOPPING, randomDirection;

		while (maxTunnels > 0 && length > 0) {

			// lets get a random direction - until it is a perpendicular to our
			// lastDirection
			// if the last direction = left or right,
			// then our new direction has to be up or down,
			// and vice versa
			do {
				randomDirection = Direction.getRandom(random.nextInt(4));
			} while ((randomDirection.row == -lastDirection.row && randomDirection.column == -lastDirection.column)
					|| (randomDirection.row == lastDirection.row && randomDirection.column == lastDirection.column));

			int randomLength = (int) MathHelper.clamp(Math.ceil(Math.random() * length), 3, length),
					// length the next tunnel will be (max of maxLength)
					
					tunnelLength = 0; // current length of tunnel being created

			// lets loop until our tunnel is long enough or until we hit an edge
			while (tunnelLength < randomLength) {

				// break the loop if it is going out of the map
				if (((currentRow == 0) && (randomDirection.row == -1))
						|| ((currentColumn == 0) && (randomDirection.column == -1))
						|| ((currentRow == row - 1) && (randomDirection.row == 1))
						|| ((currentColumn == column - 1) && (randomDirection.column == 1))) {
					break;
				} else {
					if(currentRow == xChunkSpawn && currentColumn == zChunkSpawn) {
						array[currentRow][currentColumn] = 1; 
					}else {
						array[currentRow][currentColumn] = this.random.nextFloat() > 0.8f ? 1 : 2; 
					}
					// set the value of the index in map to 1 (a tunnel, making it one longer)
					
					
					currentRow += randomDirection.row; 
					currentColumn += randomDirection.column;
					// add the value from randomDirection to row and col (-1, 0, or 1) to update our location
					
					tunnelLength++; // the tunnel is now one longer, so lets increment that variable
				}
			}

			if (tunnelLength > 0) { // update our variables unless our last loop broke before we made any part of a
									// tunnel
				lastDirection = randomDirection; // set lastDirection, so we can remember what way we went
				maxTunnels--; // we created a whole tunnel so lets decrement how many we have left to create
			}
		}
		//Clean impossible narrow rooms.
		for(int x = 0; x < array.length; x++) {
			for(int y = 0; y < array[x].length; y++) {
				if(array[x][y] == 2 && !canBeNarrowTunnel(array, x, y)) {
					array[x][y] = 1;
				}
			}
		}
		return array; // all our tunnels have been created and our map is complete, so lets return it
	
	}

	/**
	 * Creates a non-null 2D array filled with a specific number.
	 * @param num The number to initiate every cell in the 2D array with.
	 * @param row The number of max rows.
	 * @param column The number of max columns.
	 * @return A {@code int[][]} filled with the number inputed.
	 */
	public static int[][] empty2DArray(int num, int row, int column) {
		int[][] array = new int[row][column];
		for (int i = 0; i < row; i++) {
			Arrays.fill(array[i], num);
		}
		return array;
	}
	
	/**
	 * A generator that goes through every room and checks if there are 4 normal-sized rooms 
	 * in a square that can be turned into a single wide room.
	 */
	public void generateWideRooms() {
		for(int x = 0; x < this.layout.length - 1; x++) {
			for(int z = 0; z < this.layout[x].length -1; z++) {
				if((x == this.xChunkSpawn && z == this.zChunkSpawn) || 
				   (x + 1 == this.xChunkSpawn && z == this.zChunkSpawn) || 
				   (x + 1 == this.xChunkSpawn && z + 1 == this.zChunkSpawn) ||
				   (x == this.xChunkSpawn && z + 1 == this.zChunkSpawn)) continue;
				if(this.layout[x][z] == 1 && this.layout[x + 1][z] == 1 && this.layout[x][z + 1] == 1 && this.layout[x + 1][z + 1] == 1) {
					this.layout[x][z] = 3; 
					this.layout[x + 1][z] = 4;
					this.layout[x][z + 1] = 5;
					this.layout[x + 1][z + 1] = 6;
				}
			}
		}
	}
	
	/**
	 * Generates a list of all possible special rooms (special rooms are rooms that
	 * have one adjacent room) and sorts them according to distance;
	 * @return A {@link List} filled with {@link ImmutablePair}s of chunk coords sorted 
	 * from nearest to the farthest from spawn pos. 
	 */
	public List<Pair<Integer, Integer>> generateSpecialRoomsList(){
		List<Pair<Integer, Integer>> list = new ArrayList<>();
		for(int x = 1; x < this.layout.length - 1; x++) {
			for(int y = 1; y < this.layout[x].length - 1; y++) {
				if(this.layout[x][y] == 0 || this.layout[x][y] == 2) continue;
				if(this.layout[x - 1][y] == 0 && hasOneAdjacent(x-1, y, this.layout[x][y])) {
					list.add(Pair.of(x-1, y));
				}
				if(this.layout[x + 1][y] == 0 && hasOneAdjacent(x+1, y, this.layout[x][y])) {
					list.add(Pair.of(x+1, y));
				}
				if(this.layout[x][y - 1] == 0 && hasOneAdjacent(x, y-1, this.layout[x][y])) {
					list.add(Pair.of(x, y-1));
				}
				if(this.layout[x][y + 1] == 0 && hasOneAdjacent(x, y+1, this.layout[x][y])) {
					list.add(Pair.of(x, y+1));
				}
			}
		}
		Collections.sort(list, new Sorter(new ImmutablePair<Integer, Integer>(this.xChunkSpawn, this.zChunkSpawn)));
		return list;
	}
	
	public void addSpecialRoom(int number, int maxDistance) {
		if (maxDistance == -1) {
			this.layout[specialRoomList.get(specialRoomList.size() - 1).getLeft()][specialRoomList
					.get(specialRoomList.size() - 1).getRight()] = number;
			specialRoomList.remove(specialRoomList.size() - 1);
			return;
		}
		int rand = random.nextInt(maxDistance);
		rand = MathHelper.clamp(rand, 0, specialRoomList.size()-1);
		this.layout[specialRoomList.get(rand).getLeft()][specialRoomList.get(rand).getRight()] = number;
		specialRoomList.remove(rand);
	}
	
	/**
	 * 
	 * @param x The chunk x coords.
	 * @param y The chunk y coords.
	 * @param originalRoomValue The original room value(normal = 1, wide = 3,4,5,6, special = #>6).
	 * @return {@code true} if has a single adjacent room.
	 */
	private boolean hasOneAdjacent(int x, int y, int originalRoomValue) {
		int count = 0;
		if(x - 1 >= 0) count += this.layout[x -1][y];
		if(x + 1 < maxRows) count += this.layout[x + 1][y];
		if(y - 1 >= 0) count += this.layout[x][y - 1];
		if(y + 1 < maxColumns) count += this.layout[x][y + 1];
		return count == originalRoomValue;
	}

	/**
	 * Checks if this room can be a narrow room be making sure it has only two non perpendicular rooms.
	 * @param x Chunk coords
	 * @param z Chunk coords
	 * @return true if this room can be a narrow room 
	 */
	private boolean canBeNarrowTunnel(int[][] array, int x, int z) {
		if(array[x][z] != 2) return false;
		if(x == 0 || x == maxRows - 1 || z == 0 || z == maxColumns - 1) return false;
		return (array[x + Direction.LEFT.row][z] != 0 && array[x + Direction.RIGHT.row][z] != 0) //can be Right Left narrow and does not have a room up or down.
				&& !(array[x][z + Direction.DOWN.column] != 0 || array[x][z + Direction.UP.column] != 0) || 
				!(array[x + Direction.LEFT.row][z] != 0 || array[x + Direction.RIGHT.row][z] != 0) //can be Up Down narrow and does not have a room to the right or left.
				&& (array[x][z + Direction.DOWN.column] != 0 && array[x][z + Direction.UP.column] != 0);
	}
	
	/**
	 * Checks if this (already narrow room) is a north-south oriented room.
	 * @param x Chunk coords
	 * @param z Chunk coords
	 * @return is this narrow room south-north oriented
	 */
	public boolean isNarrowSouthNorth(int x, int z) {
		return (this.layout[x][z + Direction.DOWN.column] != 0 && this.layout[x][z + Direction.UP.column] != 0);
	}
	
	private List<byte[][]> getPossibleRoomLayouts(){
		List<byte[][]> list = new ArrayList<>();
		switch(this.type) {
		case BASEMENT:
			list.add(Reference.RoomLayouts.BASEMENT);
			list.add(Reference.RoomLayouts.BASEMENT1);
			list.add(Reference.RoomLayouts.BASEMENT2);
			list.add(Reference.RoomLayouts.BASEMENT3);
			list.add(Reference.RoomLayouts.BASEMENT4);
			list.add(Reference.RoomLayouts.BASEMENT5);
			list.add(Reference.RoomLayouts.BASEMENT6);
			list.add(Reference.RoomLayouts.BASEMENT7);
			list.add(Reference.RoomLayouts.BASEMENT8);
			list.add(Reference.RoomLayouts.BASEMENT9);
			return list;
		case CELLAR:
			break;
		}
		return null;
	}
	
	public BlockPos getSpawnPos() {
		return spawnPos;
	}

	public int getMaxColumns() {
		return maxColumns;
	}

	public void setMaxColumns(int maxColumns) {
		this.maxColumns = maxColumns;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public int getMaxTunnels() {
		return maxTunnels;
	}

	public int[][] getLayout() {
		return layout;
	}
	
	public int getxChunkSpawn() {
		return xChunkSpawn;
	}

	public int getzChunkSpawn() {
		return zChunkSpawn;
	}

	public EnumDungeonType getType() {
		return type;
	}

	public List<byte[][]> getPossibleRooms() {
		return possibleRooms;
	}

	public static class Sorter implements Comparator<Pair<Integer, Integer>>
    {
        private final Pair<Integer,Integer> spawn;

        public Sorter(Pair<Integer,Integer> spawn)
        {
            this.spawn = spawn;
        }

        @Override
		public int compare(Pair<Integer,Integer> spawn1, Pair<Integer,Integer> spawn2)
        {
        	int distanceFirst = Math.abs(spawn1.getLeft() - this.spawn.getLeft()) + Math.abs(spawn1.getRight() - this.spawn.getRight());
        	int distanceSecond = Math.abs(spawn2.getLeft() - this.spawn.getLeft()) + Math.abs(spawn2.getRight() - this.spawn.getRight());

            if (distanceFirst < distanceSecond)
            {
                return -1;
            }
            else
            {
                return distanceFirst > distanceSecond ? 1 : 0;
            }
        }
    }

	public enum Direction {

		LEFT(-1, 0), RIGHT(1, 0), UP(0, 1), DOWN(0, -1), STOPPING(0, 0);

		private int row, column;

		private Direction(int row, int column) {
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}

		public static Direction getRandom(int random) {
			return values()[random];
		}

	}
	
	public enum EnumDungeonType{
		BASEMENT(0),CELLAR(1);

		private int id;

		private EnumDungeonType(int id){
			this.id = id;
		}
		
		private static EnumDungeonType getTypeByID(int id) {
			for(int x = 0; x < values().length; x ++) {
				if(values()[x].id == id) {
					return values()[x];
				}
			}
			return null;
		}
		
		
		public int getID() {
			return id;
		}
		
	}

}
