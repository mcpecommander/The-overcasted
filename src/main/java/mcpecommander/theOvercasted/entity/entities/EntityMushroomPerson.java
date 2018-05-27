package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityMushroomPerson extends EntityBaseAnimated {

	// The animation handler from the api.
	private static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityMushroomPerson.class);
	public int size;
	
	static {
		EntityMushroomPerson.animHandler.addAnim(Reference.MODID, "mushroom_walk", "mushroom_person", true);
		EntityMushroomPerson.animHandler.addAnim(Reference.MODID, "mushroom_hide", "mushroom_person", false);
		EntityMushroomPerson.animHandler.addAnim(Reference.MODID, "mushroom_punch", "mushroom_person", false);
	}

	public EntityMushroomPerson(World worldIn) {
		super(worldIn);
		size = this.getRNG().nextInt(2);
		this.setSize(.8f + size/2.8f, 1.1f + size);
		
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityMushroomPerson.animHandler;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("size", size);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if(compound.hasKey("size", 3)) {
			size = compound.getInteger("size");
		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.getAnimationHandler().animationsUpdate(this);
		if(isWorldRemote()) {
			if(this.size == 0) {
				AnimationHelper.startHoldAnimation(animHandler, this, "mushroom_hide");
			}else {
				AnimationHelper.startAnimation(animHandler, this, "mushroom_punch");
			}
		}else {
			
		}
	}

}
