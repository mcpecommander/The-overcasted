package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityBasilisk extends EntityMob implements IAnimated{
	
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityBasilisk.class);
	
	static {
		EntityBasilisk.animHandler.addAnim(Reference.MODID, "basilisk_idle", "basilisk", true);
	}

	public EntityBasilisk(World worldIn) {
		super(worldIn);
		this.setSize(1.1f, 1.3f);
		
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.getAnimationHandler().animationsUpdate(this);
		if(this.isWorldRemote()) {
			AnimationHelper.startAnimation(animHandler, this, "basilisk_idle");
			
		}else {
			
		}
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityBasilisk.animHandler;
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public double getX() {
		return this.posX;
	}

	@Override
	public double getY() {
		return this.posY;
	}

	@Override
	public double getZ() {
		return this.posZ;
	}

	@Override
	public boolean isWorldRemote() {
		return world.isRemote;
	}

}
