package mcpecommander.theOvercasted.entity.entities.fatties;

import mcpecommander.theOvercasted.entity.entities.EntityBasicChampion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class EntityFatSack extends EntityBasicChampion{

	public EntityFatSack(World worldIn) {
		super(worldIn);
		this.setSize(1.1f, 1.3f);
	}
	
	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(46D);
	}
	
	@Override
	public String getName() {
		if(this.getHealth() < 26f && this.getHealth() > 10f) {
			return "Blubber";
		}else if (this.getHealth() < 10f) {
			return "Half Sack";
		}else {
			return "Fat Sack";
		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.world.isRemote) {
			if(this.getHealth() < 26f && this.getHealth() > 10f) {
				this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY + this.height - 0.3, this.posZ, 0.1, 0.1, 0.1);
			}else if(this.getHealth() < 10f) {
				this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY + this.height - 0.7, this.posZ, 0.1, 0.1, 0.1);
			}
		}
	}

}
