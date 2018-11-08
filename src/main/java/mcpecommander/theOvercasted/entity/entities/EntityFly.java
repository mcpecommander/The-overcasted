package mcpecommander.theOvercasted.entity.entities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityFly extends EntityMob {

	public EntityFly(World worldIn) {
		super(worldIn);
		this.setSize(0.2f, 0.2f);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(!this.world.isAirBlock(getPosition().down()) || !this.world.isAirBlock(getPosition().down().down())) {
			this.motionY = 0.1;
		}else {
			this.motionY = -0.1;
		}
		if(this.world.isRemote) {
			this.posX += (this.getRNG().nextDouble() /10) - 0.05;
		}
		
	}

}
