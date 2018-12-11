package mcpecommander.theOvercasted.entity.entities.pickups;

import mcpecommander.theOvercasted.capability.pickups.IPickups;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.proxy.CommonProxy;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityItemBomb extends EntityItem{
	
	public EntityItemBomb(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
		this.setNoDespawn();
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if(!world.isRemote) {
			if (this.cannotPickup()) return;
			ItemStack stack = this.getItem();
			if(stack.isEmpty()) {
				this.setDead();
			}else {
				int count = stack.getCount();
				IPickups cap = entityIn.getCapability(PickupsProvider.PICKUPS, null);
				if(cap != null) {
					cap.addBombs(count);
					BlockPos pickups = new BlockPos(cap.getBombs(), cap.getCoins(), cap.getKeys());
					CommonProxy.CHANNEL.sendTo(new PacketSendVec3i(pickups), (EntityPlayerMP) entityIn);
					this.setDead();
				}
			}
			
		}
		
	}
	

}
