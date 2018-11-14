package mcpecommander.theOvercasted.maze;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class DungeonGenerator {

	private BlockPos spawnPos;
	private int maxLength, maxRows, maxColumns, maxTunnels, spawnHeight;
	private Random random = new Random();
	private int[][] layout;

	private DungeonGenerator(int maxLength, int maxRows, int maxColumns, int maxTunnels, int spawnHeight) {
		this.maxLength = MathHelper.clamp(maxLength, 3, Integer.MAX_VALUE);
		this.maxRows = maxRows;
		this.maxColumns = maxColumns;
		this.maxTunnels = maxTunnels;
		this.spawnHeight = spawnHeight;
		layout = this.createMap(maxRows, maxColumns, maxLength, maxTunnels);
		generateWideRooms();
	}
	
	public static DungeonGenerator createDungeon(int maxLength, int maxRows, int maxColumns, int maxTunnels, int spawnHeight) {
		return new DungeonGenerator(maxLength, maxRows, maxColumns, maxTunnels, spawnHeight);
	}

	public int[][] createMap(int row, int column, int length, int maxTunnels) {
		int[][] array = empty2DArray(1, row, column);
		int currentRow = (int) Math.floor(Math.random() * row); // our current row - start at a random spot
		int currentColumn = (int) Math.floor(Math.random() * column); // our current column - start at a random spot
		this.spawnPos = new BlockPos(currentRow * 16, spawnHeight, currentColumn * 16);
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
//					int randomLength = (int)Math.ceil(Math.random() * length),
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
					array[currentRow][currentColumn] = this.random.nextFloat() > 0.4f ? 0 : 2; // set the value of the
																								// index in map to 0 (a
																								// tunnel, making it
					// one longer)
					currentRow += randomDirection.row; // add the value from randomDirection to row and col (-1, 0, or
														// 1) to update our location
					currentColumn += randomDirection.column;
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
		for(int x = 0; x < this.layout.length; x++) {
			for(int y = 0; y < this.layout[x].length; y++) {
				if(this.layout[x][y] == 2 && !canBeNarrowTunnel(x, y)) {
					this.layout[x][y] = 0;
				}
			}
		}
		return array; // all our tunnels have been created and our map is complete, so lets return it
						// to our render()
	}

	public static int[][] empty2DArray(int num, int row, int column) {
		int[][] array = new int[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				array[i][j] = num;
			}
		}
		return array;
	}
	
	public void generateWideRooms() {
		for(int x = 0; x < this.layout.length - 1; x++) {
			for(int y = 0; y < this.layout[x].length -1; y++) {
				if(this.layout[x][y] == 0 && this.layout[x + 1][y] == 0 && this.layout[x][y + 1] == 0 && this.layout[x + 1][y + 1] == 0) {
					this.layout[x][y] = 3; 
					this.layout[x + 1][y] = 4;
					this.layout[x][y + 1] = 5;
					this.layout[x + 1][y + 1] = 6;
				}
			}
		}
	}
	
	public void generateSpecialRooms(){
		//TODO
	}

	/**
	 * Checks if this room can be a narrow room be making sure it has only two non perpendicular rooms.
	 * @param x Chunk coords
	 * @param z Chunk coords
	 * @return true if this room can be a narrow room 
	 */
	public boolean canBeNarrowTunnel(int x, int z) {
		if(this.layout[x][z] != 2) return false;
		if(x == 0 || x == maxRows - 1 || z == 0 || z == maxColumns - 1) return false;
		return (this.layout[x + Direction.LEFT.row][z] != 1 && this.layout[x + Direction.RIGHT.row][z] != 1) //can be Right Left narrow and does not have a room up or down.
				&& !(this.layout[x][z + Direction.DOWN.column] != 1 || this.layout[x][z + Direction.UP.column] != 1) || 
				!(this.layout[x + Direction.LEFT.row][z] != 1 || this.layout[x + Direction.RIGHT.row][z] != 1) //can be Up Down narrow and does not have a room to the right or left.
				&& (this.layout[x][z + Direction.DOWN.column] != 1 && this.layout[x][z + Direction.UP.column] != 1);
	}
	
	/**
	 * Checks if this (already narrow room) is a north-south oriented room.
	 * @param x Chunk coords
	 * @param z Chunk coords
	 * @return is this narrow room south-north oriented
	 */
	public boolean isNarrowSouthNorth(int x, int z) {
		return (this.layout[x][z + Direction.DOWN.column] != 1 && this.layout[x][z + Direction.UP.column] != 1);
	}

	public int[][] getLayout() {
		return layout;
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

}
