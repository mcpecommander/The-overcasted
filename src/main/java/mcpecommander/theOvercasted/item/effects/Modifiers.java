package mcpecommander.theOvercasted.item.effects;

public class Modifiers {

	private final Type type;
	private final float amount;
	private final boolean forced;
	
	Modifiers(Type type, float amount, boolean forced) {
		this.type = type;
		this.amount = amount;
		this.forced = forced;
	}
	
	public static Modifiers getByString(String typeString, String amountString, String forcedString) {
		Type type = Type.valueOf(typeString.toUpperCase());
		float amount = Float.parseFloat(amountString);
		boolean forced = Boolean.parseBoolean(forcedString);
		return new Modifiers(type, amount, forced);
	}

	Type getType() {
		return type;
	}

	float getAmount() {
		return amount;
	}

	static enum Type{
		HEALTH, COINS, KEYS, BOMBS, SPEED, RANGE, FIRE_RATE, PROJECTILE_SPEED, DAMAGE, LUCK, ANGEL, DEVIL;
	}
}
