package mcpecommander.theOvercasted.capability.stats;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum EnumCharacter {
	
	ISAAC(combineHealth(3,0,0), 1.15f, 23f, 15f, 1.15f, 3.5f, 1f, Lists.newArrayList()), 
	MAGDALENE(combineHealth(4,0,0), 1.15f, 23f, 15f, 1.15f, 3.5f, 1f, Lists.newArrayList()), 
	CAIN(combineHealth(2,0,0), 1.15f, 23f, 15f, 1.15f, 3.5f, 1f, Lists.newArrayList()), 
	JUDAS(combineHealth(1,0,0), 1.15f, 23f, 15f, 1.15f, 3.5f, 1f, Lists.newArrayList());
	
	private int health;
	private float speed, range, fireRate, projectileSpeed, damage, luck;
	private List<ItemStack> startingItems;
	
	private EnumCharacter(int health, float speed, float range, float fireRate, float projectileSpeed, float damage, float luck, List<ItemStack> startingItems) {
		this.health = health;
		this.speed = speed;
		this.range = range;
		this.fireRate = fireRate;
		this.projectileSpeed = projectileSpeed;
		this.damage = damage;
		this.luck = luck;
		this.startingItems = startingItems;
	}
	
	public int getRedHealth() {
		return (this.health >> 0) & 20;
	}
	
	public int getSoulHealth() {
		return (this.health >> 8) & 20;
	}
	
	public int getBlackHealth() {
		return (this.health >> 16) & 20;
	}
	
	public static int combineHealth(int redHealth, int soulHealth, int blackHealth) {
		return ((blackHealth & 20) << 16)|
				((soulHealth & 20) << 8) |
				((redHealth & 20) << 0);
	}

	public float getSpeed() {
		return speed;
	}

	public float getRange() {
		return range;
	}

	public float getFireRate() {
		return fireRate;
	}

	public float getProjectileSpeed() {
		return projectileSpeed;
	}

	public float getDamage() {
		return damage;
	}

	public float getLuck() {
		return luck;
	}

	public List<ItemStack> getStartingItems() {
		return startingItems;
	}

}
