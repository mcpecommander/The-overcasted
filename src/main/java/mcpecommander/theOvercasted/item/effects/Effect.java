package mcpecommander.theOvercasted.item.effects;

import java.util.Map;

import com.google.common.collect.Maps;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import net.minecraft.util.NonNullList;

public class Effect {
	
	private ActionType type;
	
	protected Effect(ActionType type) {
		this.type = type;

	}

	public ActionType getType() {
		return type;
	}

	public static enum ActionType{
		CONTINUOUS, LIMITED, ONCE;
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
