package mcpecommander.theOvercasted.item.effects;

import java.util.Map;

import com.google.common.collect.Maps;

import mcpecommander.theOvercasted.item.effects.Effect.ActionType;

public class Attribute {
	public static final Map<Integer, Attribute> REGISTRY = Maps.newHashMap();
	
	public static final Attribute SAD_ONION = new Attribute(1, new StatEffect(ActionType.ONCE, "fire_rate,0.7,-"));
	public static final Attribute INNER_EYE = new Attribute(2,
			new TearEffect(ActionType.CONTINUOUS, TearEffectCommons.multiShot(2)),
			new StatEffect(ActionType.ONCE, "fire_rate,3,+"));
	public static final Attribute SPOON_BENDER = new Attribute(3,
			new TearEffect(ActionType.CONTINUOUS, TearEffectCommons.homingShot()));
	public static final Attribute CRICKET_HEAD = new Attribute(4,
			new StatEffect(ActionType.ONCE, "damage,0.5,+", "damage,1.5,*"));
	public static final Attribute MY_REFLECTION = new Attribute(5,
			new TearEffect(ActionType.CONTINUOUS, TearEffectCommons.boomerangShot()),
			new StatEffect(ActionType.ONCE, "projectile_speed,0.6,+", "range,1.5,+"));
	
	
	
	
	
	
	
	
	
	
	
	private Effect[] effects;
	private int id;
	
	private Attribute(int id, Effect... effects) {
		this.id = id;
		this.effects = effects;
		registerItemEffect(this);
	}
	
	public Effect[] getEffects() {
		return effects;
	}

	public int getId() {
		return id;
	}

	private static void registerItemEffect(Attribute attribute) {
		REGISTRY.put(attribute.id, attribute);
	}

	public static Attribute getAttributeById(int id) {
		Attribute attribute = REGISTRY.get(id);
		if(REGISTRY.get(id) == null) {
			return SAD_ONION;
		}
		return attribute;
	}

}
