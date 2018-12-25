package mcpecommander.theOvercasted.entity.entities.flies;

import mcpecommander.theOvercasted.block.BlockPoop;
import mcpecommander.theOvercasted.registryHandler.Registry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class EntityBlackFly extends EntityFly {

	public EntityBlackFly(World worldIn) {
		super(worldIn);
		
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		flyMovment();
	}
	
	@Override
	public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
		this.setBaseHeight((float) (y + 1.4f));
	}
	
	@Override
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
		super.setLocationAndAngles(x, y, z, yaw, pitch);
		this.setBaseHeight((float) (y + 1.4f));
	}


	private void flyMovment() {
		if(ticksExisted % 4 == 0) {
			this.posX += (this.getRNG().nextDouble() /20) - 0.025;
			this.posZ += (this.getRNG().nextDouble() /20) - 0.025;
		}
	}
	
	@Override
	protected void onInsideBlock(IBlockState state) {
		if(state == Registry.POOP.getDefaultState().withProperty(BlockPoop.COLOR, BlockPoop.EnumColor.GOLDEN)) {
			
		}
	}

}
