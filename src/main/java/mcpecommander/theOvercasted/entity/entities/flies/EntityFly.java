package mcpecommander.theOvercasted.entity.entities.flies;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityFly extends EntityMob {
	
	private float baseHeight;

	public EntityFly(World worldIn) {
		super(worldIn);
		this.setSize(0.2f, 0.2f);
	}
	
	public EntityFly(World world, float baseHeight) {
		this(world);
		this.baseHeight = baseHeight;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		
		stayAfloat();
		
	}
	
	@Override
	protected void onInsideBlock(IBlockState state) {
		
	}
	
	@Override
	protected boolean pushOutOfBlocks(double x, double y, double z) {
		return false;
	}
	
	protected void stayAfloat() {
		if(this.posY < baseHeight) {
			this.motionY = 0.1;
		}else {
			this.motionY = -0.1;
		}
	}
	
	public float getBaseHeight() {
		return baseHeight;
	}

	public void setBaseHeight(float baseHeight) {
		this.baseHeight = baseHeight;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setFloat("base_height", baseHeight);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.baseHeight = compound.getFloat("base_height");
	}

}
