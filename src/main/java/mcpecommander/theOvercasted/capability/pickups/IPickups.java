package mcpecommander.theOvercasted.capability.pickups;

public interface IPickups {
	
	public int getCoins();
	public int getBombs();
	public int getKeys();
	
	public IPickups setCoins(int coins);
	public IPickups setBombs(int bombs);
	public IPickups setKeys(int keys);
	
	public void addCoins(int coins);
	public void addBombs(int bombs);
	public void addKeys(int keys);
	
	public void removeCoins(int coins);
	public void removeBombs(int bombs);
	public void removeKeys(int keys);

}
