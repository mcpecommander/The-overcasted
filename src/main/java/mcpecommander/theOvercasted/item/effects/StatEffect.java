package mcpecommander.theOvercasted.item.effects;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;

import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.capability.pickups.IPickups;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.StatsProvider;
import mcpecommander.theOvercasted.networking.PacketSendStats;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.proxy.CommonProxy;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

public class StatEffect extends Effect{
	
	private List<ImmutablePair<Modifiers, Sign>> modifiers = Lists.newArrayList();

	public StatEffect(ActionType type, String... attributes) {
		super(type);
		parseString(attributes);
		
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
	
	public void applyAttibutes(EntityPlayer player) {
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
					switch(pair.right) {
					case ADD:
						stats.setDamage(stats.getDamage() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setDamage(stats.getDamage() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setDamage(stats.getDamage() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setDamage(stats.getDamage() - pair.left.getAmount());
						break;
					default:
						break;
					}
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
					switch(pair.right) {
					case ADD:
						stats.setFireRate(stats.getFireRate() + pair.left.getAmount());
						break;
					case DIVIDE:
						stats.setFireRate(stats.getFireRate() / pair.left.getAmount());
						break;
					case MULTIPLE:
						stats.setFireRate(stats.getFireRate() * pair.left.getAmount());
						break;
					case SUBTRACT:
						stats.setFireRate(stats.getFireRate() - pair.left.getAmount());
						break;
					default:
						break;
					}
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

}
