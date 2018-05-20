package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.animations.EyesLookAt;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityBasilisk extends EntityMob implements IAnimated{
	
	private int margin;
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityBasilisk.class);
	
	static {
		EntityBasilisk.animHandler.addAnim(Reference.MODID, "basilisk_idle", "basilisk", true);
		EntityBasilisk.animHandler.addAnim(Reference.MODID, "basilisk_jump", "basilisk", true);
		EntityBasilisk.animHandler.addAnim(Reference.MODID, "look_at", new EyesLookAt("RightEye", "LeftEye"));
	}

	public EntityBasilisk(World worldIn) {
		super(worldIn);
		this.setSize(1.1f, 1.3f);
		
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAILookIdle(this));
	}
	
	@Override
	protected float getJumpUpwardsMotion() {
		return 0.65f;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.getAnimationHandler().animationsUpdate(this);
		if(this.onGround) {
			if(margin++ > 5)
			this.jump();
		}else {
			margin = 0;
		}
		if(this.isWorldRemote()) {
			
			if(margin == 0 && this.onGround) {
				AnimationHelper.startAnimation(animHandler, this, "basilisk_idle");
			}
			AnimationHelper.startAnimation(animHandler, this, "look_at");
			
			if(!this.onGround) {
				AnimationHelper.stopStartAnimation(animHandler, this, "basilisk_idle", "basilisk_jump");
			}
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
