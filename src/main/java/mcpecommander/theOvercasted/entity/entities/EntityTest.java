package mcpecommander.theOvercasted.entity.entities;

import javax.vecmath.Vector3d;

import mcpecommander.theOvercasted.entity.animationTest.Animation;
import mcpecommander.theOvercasted.entity.animationTest.KeyFrame;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTest extends EntityMob {

	public EntityTest(World worldIn) {
		super(worldIn);
		this.setSize(1.1f, 1.3f);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		//this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
		return super.onInitialSpawn(difficulty, livingdata);
	}


}
