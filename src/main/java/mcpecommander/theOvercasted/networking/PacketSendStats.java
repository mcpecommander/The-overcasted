package mcpecommander.theOvercasted.networking;

import io.netty.buffer.ByteBuf;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.StatsProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendStats implements IMessage{
	
	private float speed, range, fireRate, projectileSpeed, damage, luck, devil, angel;

	public PacketSendStats(IStats cap) {
		this.speed = cap.getSpeed();
		this.range = cap.getRange();
		this.fireRate = cap.getFireRate();
		this.projectileSpeed = cap.getProjectileSpeed();
		this.damage = cap.getDamage();
		this.luck = cap.getLuck();
		this.devil = cap.getDevilChance();
		this.angel = cap.getAngelChance();
	}

	public PacketSendStats() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.speed = buf.readFloat();
		this.range = buf.readFloat();
		this.fireRate = buf.readFloat();
		this.projectileSpeed = buf.readFloat();
		this.damage = buf.readFloat();
		this.luck = buf.readFloat();
		this.devil = buf.readFloat();
		this.angel = buf.readFloat();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(speed);
		buf.writeFloat(range);
		buf.writeFloat(fireRate);
		buf.writeFloat(projectileSpeed);
		buf.writeFloat(damage);
		buf.writeFloat(luck);
		buf.writeFloat(devil);
		buf.writeFloat(angel);
	}
	
	public static class Handler implements IMessageHandler<PacketSendStats, IMessage>{

		@Override
		public IMessage onMessage(PacketSendStats message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					IStats stats = Minecraft.getMinecraft().player.getCapability(StatsProvider.STATS_CAP, null);
					if(stats != null) {
						stats.setSpeed(message.speed);
						stats.setRange(message.range);
						stats.setFireRate(message.fireRate);
						stats.setProjectileSpeed(message.projectileSpeed);
						stats.setDamage(message.damage);
						stats.setLuck(message.luck);
						stats.setDevilChance(message.devil);
						stats.setAngelChance(message.angel);
					}
				}
			});
			return null;
		}
		
	}

}
