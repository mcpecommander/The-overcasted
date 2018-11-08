package mcpecommander.theOvercasted.entity.entities;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public abstract class EntityBasicChampion extends EntityMob{
	
	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityBasicChampion.class, DataSerializers.VARINT);

	public EntityBasicChampion(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
//		if(this.getRNG().nextFloat() > 0.8f) 
//			this.dataManager.set(TYPE, getAllowedVarints()[this.getRNG().nextInt(16)]);
		
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	protected int[] getAllowedVarints() {
		int[] array = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
		return array;
	}
	
	public int getChampionType() {
		return this.dataManager.get(TYPE).intValue();
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TYPE, -1);
	}

}
