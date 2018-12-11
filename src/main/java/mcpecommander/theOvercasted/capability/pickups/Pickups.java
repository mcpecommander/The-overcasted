package mcpecommander.theOvercasted.capability.pickups;

import java.util.concurrent.Callable;

import net.minecraft.util.math.MathHelper;

public class Pickups implements IPickups{
	
	private static final int LIMIT = 99;
	private int bombs = 0, keys = 0, coins = 0;

	@Override
	public int getCoins() {
		return this.coins;
	}

	@Override
	public int getBombs() {
		return this.bombs;
	}

	@Override
	public int getKeys() {
		return this.keys;
	}
	
	@Override
	public IPickups setCoins(int coins) {
		this.coins = MathHelper.clamp(coins, 0, LIMIT);
		return this;
		
	}

	@Override
	public IPickups setBombs(int bombs) {
		this.bombs = MathHelper.clamp(bombs, 0, LIMIT);
		return this;
	}

	@Override
	public IPickups setKeys(int keys) {
		this.keys = MathHelper.clamp(keys, 0, LIMIT);
		return this;
	}

	@Override
	public void addCoins(int coins) {
		this.coins += MathHelper.clamp(coins, 0, LIMIT - getCoins()); 
	}

	@Override
	public void addBombs(int bombs) {
		this.bombs += MathHelper.clamp(bombs, 0, LIMIT - getBombs()); 
	}

	@Override
	public void addKeys(int keys) {
		this.keys += MathHelper.clamp(keys, 0, LIMIT - getKeys()); 
	}

	@Override
	public void removeCoins(int coins) {
		this.coins -= MathHelper.clamp(coins, 0, this.getCoins()); 
	}

	@Override
	public void removeBombs(int bombs) {
		this.bombs -= MathHelper.clamp(bombs, 0, this.getBombs()); 
	}

	@Override
	public void removeKeys(int keys) {
		this.keys -= MathHelper.clamp(keys, 0, this.getKeys()); 
	}

	public static class Factory implements Callable<IPickups> {

		@Override
		public IPickups call() throws Exception {
			return new Pickups();
		}
	}

}
