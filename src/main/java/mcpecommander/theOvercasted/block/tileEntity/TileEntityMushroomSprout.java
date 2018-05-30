package mcpecommander.theOvercasted.block.tileEntity;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityMushroomSprout extends TileEntity implements IAnimated, ITickable{

	int tick;
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(TileEntityMushroomSprout.class);
	static {
		TileEntityMushroomSprout.animHandler.addAnim(Reference.MODID, "mushroom_grow", "mushroom_sprout", false);
	}

	@Override
	public void update() {
		this.animHandler.animationsUpdate(this);
		tick ++;

		if(this.world.isRemote) {
			AnimationHelper.startHoldAnimation(animHandler, this, "mushroom_grow");
		}
		
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		AnimationHelper.stopAnimation(animHandler, this, "mushroom_grow");
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return TileEntityMushroomSprout.animHandler;
	}

	@Override
	public int getDimension() {
		return this.world.provider.getDimension();
	}

	@Override
	public double getX() {
		return this.pos.getX();
	}

	@Override
	public double getY() {
		return this.pos.getY();
	}

	@Override
	public double getZ() {
		return this.pos.getZ();
	}

	@Override
	public boolean isWorldRemote() {
		return this.world.isRemote;
	}
	
	// Here to prevent bugs on the integrated server.
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.pos.getX();
        result = prime * result + this.pos.getY();
        result = prime * result + this.pos.getZ();
        return result;
    }

    // Here to prevent bugs on the integrated server.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        TileEntityMushroomSprout other = (TileEntityMushroomSprout) obj;
        if (this.pos.getX() != other.pos.getX())
            return false;
        if (this.pos.getY() != other.pos.getY())
            return false;
        if (this.pos.getZ() != other.pos.getZ())
            return false;
        return true;
    }

}
