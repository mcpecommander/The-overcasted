package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.world.World;

public class EntityMushroomPerson extends EntityBaseAnimated {

	// The animation handler from the api.
	private static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityMushroomPerson.class);

	public EntityMushroomPerson(World worldIn) {
		super(worldIn);
		this.setSize(2f, 2f);
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityMushroomPerson.animHandler;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.getAnimationHandler().animationsUpdate(this);
	}

}
