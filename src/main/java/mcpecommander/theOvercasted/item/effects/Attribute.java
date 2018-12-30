package mcpecommander.theOvercasted.item.effects;

import java.awt.Color;
import java.util.Map;

import com.google.common.collect.Maps;

import mcpecommander.theOvercasted.entity.entities.familiars.EntityPrimalFamiliar;

public class Attribute {
	public static final Map<Integer, Attribute> REGISTRY = Maps.newHashMap();

	public static final Attribute SAD_ONION = new Attribute(1, new StatEffect("fire_rate,0.7,false,+"));
	public static final Attribute INNER_EYE = new Attribute(2, new TearEffect(TearEffectCommons.multiShot(2)),
			new StatEffect("fire_rate,2.1,true,*"), new StatEffect("fire_rate,3,true,+"));
	public static final Attribute SPOON_BENDER = new Attribute(3, new TearEffect(TearEffectCommons.homingShot()),
			new ColorEffect(Color.PINK.darker().darker()));
	public static final Attribute CRICKET_HEAD = new Attribute(4,
			new StatEffect("damage,0.5,false,+", "damage,1.5,false,*"));
	public static final Attribute MY_REFLECTION = new Attribute(5, new TearEffect(TearEffectCommons.boomerangShot()),
			new StatEffect("projectile_speed,0.6,false,+", "range,1.5,false,+"));
	public static final Attribute NUMBER_ONE = new Attribute(6, new StatEffect("range,17.62,false,-"),
			new StatEffect("fire_rate,1.5,false,+"), new ColorEffect(Color.YELLOW));
	public static final Attribute BLOOD_OF_THE_MARTYR = new Attribute(7, new StatEffect("damage,1,false,+"),
			new ColorEffect(Color.RED));
	public static final Attribute BROTHER_BOBBY = new Attribute(8, new FamiliarEffect(EntityPrimalFamiliar.class));
	
	
	
	
	
	
	
	
	
	private IEffect[] effects;
	private int id;
	
	private Attribute(int id, IEffect... effects) {
		this.id = id;
		this.effects = effects;
		registerItemEffect(this);
	}
	
	public IEffect[] getEffects() {
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
	
	public static enum Sign{
		MULTIPLE('*'), ADD('+'), SUBTRACT('-'), DIVIDE('/');
		
		private char sign;
		
		private Sign(char sign) {
			this.sign = sign;
		}
		
		public static Sign getByChar(char sign) {
			for(Sign x : Sign.values()) {
				if(x.sign == sign) {
					return x;
				}
			}
			return null;
		}
	}

}
