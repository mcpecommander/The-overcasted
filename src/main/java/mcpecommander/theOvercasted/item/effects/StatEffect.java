package mcpecommander.theOvercasted.item.effects;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;

import akka.japi.Effect;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.capability.pickups.IPickups;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.StatsProvider;
import mcpecommander.theOvercasted.item.effects.Attribute.Sign;
import mcpecommander.theOvercasted.item.effects.Modifiers.Type;
import mcpecommander.theOvercasted.networking.PacketSendStats;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class StatEffect implements IEffect{
	
	private List<ImmutablePair<Modifiers, Sign>> modifiers = Lists.newArrayList();

	public StatEffect(String... attributes) {
		parseString(attributes);
		
	}
	
	@Override
	public void onGet(EntityPlayer player, ItemStack stack, Attribute attribute) {
		applyAttibutes(player, stack);
	}
	
	private void parseString(String[] strings) {
		for(String string : strings) {
			String[] splits = string.split(",");
			if (splits != null && splits.length == 4) {
				modifiers.add(new ImmutablePair<Modifiers, Sign>(Modifiers.getByString(splits[0], splits[1], splits[2]),
						Sign.getByChar(splits[3].charAt(0))));
				continue;
			}
			TheOvercasted.logger.error(string + " is not parsable");
		}
	}
	
	private void applyAttibutes(EntityPlayer player, ItemStack stack) {
		if(player != null && !player.isDead && !player.world.isRemote) {
			IStats stats = player.getCapability(StatsProvider.STATS_CAP, null);
			IPickups pickups = player.getCapability(PickupsProvider.PICKUPS, null);
			if(stats != null && pickups != null)
			for(ImmutablePair<Modifiers, Sign> pair : modifiers) {
				switch(pair.left.getType()) {
				case HEALTH:
					switch(pair.right) {
					case ADD:
						player.setHealth(player.getHealth() + pair.left.getAmount());
						break;
					case DIVIDE:
						player.setHealth(player.getHealth() /pair.left.getAmount());
						break;
					case MULTIPLE:
						player.setHealth(player.getHealth() * pair.left.getAmount());
						break;
					case SUBTRACT:
						player.setHealth(player.getHealth() - pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case BOMBS:
					switch(pair.right) {
					case ADD:
						pickups.addBombs((int) pair.left.getAmount());
						break;
					case DIVIDE:
						pickups.setBombs((int) (pickups.getBombs()/pair.left.getAmount()));
						break;
					case MULTIPLE:
						pickups.setBombs((int) (pickups.getBombs() * pair.left.getAmount()));
						break;
					case SUBTRACT:
						pickups.removeBombs((int) pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case COINS:
					switch(pair.right) {
					case ADD:
						pickups.addCoins((int) pair.left.getAmount());
						break;
					case DIVIDE:
						pickups.setCoins((int) (pickups.getCoins()/pair.left.getAmount()));
						break;
					case MULTIPLE:
						pickups.setCoins((int) (pickups.getCoins() * pair.left.getAmount()));
						break;
					case SUBTRACT:
						pickups.removeCoins((int) pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case KEYS:
					switch(pair.right) {
					case ADD:
						pickups.addKeys((int) pair.left.getAmount());
						break;
					case DIVIDE:
						pickups.setKeys((int) (pickups.getKeys() / pair.left.getAmount()));
						break;
					case MULTIPLE:
						pickups.setKeys((int) (pickups.getKeys() * pair.left.getAmount()));
						break;
					case SUBTRACT:
						pickups.removeKeys((int) pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case DAMAGE:
					recalculateDamage(stats, stack);
					break;
				case SPEED:
					switch(pair.right) {
					case ADD:
						stats.setSpeed(stats.getSpeed() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setSpeed(stats.getSpeed() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setSpeed(stats.getSpeed() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setSpeed(stats.getSpeed() - pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case RANGE:
					switch(pair.right) {
					case ADD:
						stats.setRange(stats.getRange() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setRange(stats.getRange() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setRange(stats.getRange() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setRange(stats.getRange() - pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case FIRE_RATE:
					recalculateFireRate(stats, stack);
					break;
				case PROJECTILE_SPEED:
					switch(pair.right) {
					case ADD:
						stats.setProjectileSpeed(stats.getProjectileSpeed() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setProjectileSpeed(stats.getProjectileSpeed() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setProjectileSpeed(stats.getProjectileSpeed() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setProjectileSpeed(stats.getProjectileSpeed() - pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case LUCK:
					switch(pair.right) {
					case ADD:
						stats.setLuck(stats.getLuck() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setLuck(stats.getLuck() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setLuck(stats.getLuck() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setLuck(stats.getLuck() - pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case DEVIL:
					switch(pair.right) {
					case ADD:
						stats.setDevilChance(stats.getDevilChance() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setDevilChance(stats.getDevilChance() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setDevilChance(stats.getDevilChance() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setDevilChance(stats.getDevilChance() - pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				case ANGEL:
					switch(pair.right) {
					case ADD:
						stats.setAngelChance(stats.getAngelChance() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setAngelChance(stats.getAngelChance() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setAngelChance(stats.getAngelChance() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setAngelChance(stats.getAngelChance() - pair.left.getAmount());
						break;
					default:
						break;
					}
					break;
				default:
					break;
				
				}
			}
			CommonProxy.CHANNEL.sendTo(
					new PacketSendVec3i(new BlockPos(pickups.getBombs(), pickups.getCoins(), pickups.getKeys())),
					(EntityPlayerMP) player);
			CommonProxy.CHANNEL.sendTo(new PacketSendStats(stats), (EntityPlayerMP) player);
		}
	}
	
	private void recalculateDamage(IStats stats, ItemStack tear) {
		try {
			float baseDamage = 3.5f;
			float extraDamage = 1f;
			float flatAddedDamage = 0f;
			float damageMultiplier = 0f;
			List<StatEffect> statsList = getStatEffects(tear.getTagCompound().getIntArray("items"));
			for(StatEffect effect : statsList) {
				for(ImmutablePair<Modifiers, Sign> effectModifier : effect.modifiers) {
					
					if(effectModifier.left.getType() == Type.DAMAGE ) {
						if(effectModifier.left.isForced()) {
							flatAddedDamage += effectModifier.left.getAmount();
						}else {
							if(effectModifier.right == Sign.ADD) {
								extraDamage += effectModifier.left.getAmount();
							}else if (effectModifier.right == Sign.MULTIPLE) {
								damageMultiplier += effectModifier.left.getAmount();
							}else if (effectModifier.right == Sign.SUBTRACT) {
								extraDamage -= effectModifier.left.getAmount();
							}
						}
					}
				}
			}
			if(damageMultiplier != 0f) {
				stats.setDamage((float) ((baseDamage * Math.sqrt(extraDamage * 1.2 + 1) + flatAddedDamage) * damageMultiplier));
			}else {
				stats.setDamage((float) ((baseDamage * Math.sqrt(extraDamage * 1.2 + 1) + flatAddedDamage)));
			}
			
			
		}catch(NullPointerException e) {
			TheOvercasted.logger.error(e.getMessage());
		}
	}
	
	private void recalculateFireRate(IStats stats, ItemStack tear) {
		try {
			float extraFireRate = 0f;
			float addFireRate = 0f;
			float multipliedFireRate = 0f;
			List<StatEffect> statsList = getStatEffects(tear.getTagCompound().getIntArray("items"));
			for(StatEffect effect : statsList) {
				for(ImmutablePair<Modifiers, Sign> effectModifier : effect.modifiers) {
					
					if(effectModifier.left.getType() == Type.FIRE_RATE ) {
						if(effectModifier.left.isForced()) {
							if(effectModifier.right == Sign.ADD) {
								addFireRate += effectModifier.left.getAmount();
							}else if (effectModifier.right == Sign.SUBTRACT) {
								addFireRate -= effectModifier.left.getAmount();
							}else if (effectModifier.right == Sign.MULTIPLE) {
								multipliedFireRate += effectModifier.left.getAmount();
							}
						}else {
							if(effectModifier.right == Sign.ADD) {
								extraFireRate += effectModifier.left.getAmount();
							}else if (effectModifier.right == Sign.SUBTRACT) {
								extraFireRate -= effectModifier.left.getAmount();
							}
						}
					}
				}
			}
			if(multipliedFireRate != 0f) {
				stats.setFireRate((float) (((16 - 6*Math.sqrt(extraFireRate * 1.3 + 1)) + addFireRate) * multipliedFireRate));
			}else {
				stats.setFireRate((float) ((16 - 6*Math.sqrt(extraFireRate * 1.3 + 1)) + addFireRate));
			}
			
		}catch(NullPointerException e) {
			TheOvercasted.logger.error(e.getMessage());
		}
	}
	
	private static List<StatEffect> getStatEffects(int[] items){
		
		List<StatEffect> stats = Lists.newArrayList();
		for(int i : items) {
			if(i == 0) break;
			for(IEffect effect : Attribute.getAttributeById(i).getEffects()) {
				if(effect instanceof StatEffect) {
					stats.add((StatEffect) effect);
				}
			}
		}
		return stats;
	}
	

}
