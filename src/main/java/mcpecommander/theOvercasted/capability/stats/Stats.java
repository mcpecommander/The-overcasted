package mcpecommander.theOvercasted.capability.stats;

import java.util.concurrent.Callable;

public class Stats implements IStats{
	
	private float speed = 0f, range = 0f, fireRate = 0f, projectileSpeed = 0f, damage = 0f, luck = 0f, devilChance = 0f, angelChance = 0f;
	
	@Override
	public void setDefaults(EnumCharacter character) {
		this.speed = character.getSpeed();
		this.range = character.getRange();
		this.fireRate = character.getFireRate();
		this.projectileSpeed = character.getProjectileSpeed();
		this.damage = character.getDamage();
		this.luck = character.getLuck();
		this.devilChance = 0f;
		this.angelChance = 0f;
	}
	
	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
		
	}

	@Override
	public void setRange(float range) {
		this.range = range;
		
	}

	@Override
	public void setFireRate(float fireRate) {
		this.fireRate = fireRate;
		
	}

	@Override
	public void setProjectileSpeed(float projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
		
	}
	
	@Override
	public void setDamage(float damage) {
		this.damage = damage;
	}

	@Override
	public void setLuck(float luck) {
		this.luck = luck;
		
	}

	@Override
	public void setDevilChance(float devil) {
		this.devilChance = devil;
		
	}

	@Override
	public void setAngelChance(float angel) {
		this.angelChance = angel;
		
	}
	
	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public float getRange() {
		return this.range;
	}

	@Override
	public float getFireRate() {
		return this.fireRate;
	}

	@Override
	public float getProjectileSpeed() {
		return this.projectileSpeed;
	}
	
	@Override
	public float getDamage() {
		return this.damage;
	}

	@Override
	public float getLuck() {
		return this.luck;
	}

	@Override
	public float getDevilChance() {
		return this.devilChance;
	}

	@Override
	public float getAngelChance() {
		return this.angelChance;
	}
	
	public static class Factory implements Callable<IStats>{

		@Override
		public IStats call() throws Exception {
			return new Stats();
		}
		
	}
}
