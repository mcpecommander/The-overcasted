package mcpecommander.theOvercasted.capability.stats;

public interface IStats {
	
	void setSpeed(float speed);
	void setRange(float range);
	void setFireRate(float fireRate);
	void setProjectileSpeed(float projectileSpeed);
	void setDamage(float damage);
	void setLuck(float luck);
	void setDevilChance(float devil);
	void setAngelChance(float angel);
	
	float getSpeed();
	float getRange();
	float getFireRate();
	float getProjectileSpeed();
	float getDamage();
	float getLuck();
	float getDevilChance();
	float getAngelChance();
	
	void setDefaults(EnumCharacter character);

}
