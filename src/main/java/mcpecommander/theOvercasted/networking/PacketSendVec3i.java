package mcpecommander.theOvercasted.networking;

import io.netty.buffer.ByteBuf;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendVec3i implements IMessage {
	
	private BlockPos pickups;
	
	public PacketSendVec3i() {
	}
	
	public PacketSendVec3i(BlockPos pickups) {
		this.pickups = pickups;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pickups = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pickups.toLong());
	}
	
	public static class Handler implements IMessageHandler<PacketSendVec3i, IMessage>{

		@Override
		public IMessage onMessage(PacketSendVec3i message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() ->
            Minecraft.getMinecraft().player.getCapability(PickupsProvider.PICKUPS, null).setBombs(message.pickups.getX())
            .setCoins(message.pickups.getY()).setKeys(message.pickups.getZ()));
			return null;
		}
		
	}

}
