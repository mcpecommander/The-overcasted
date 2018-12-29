package mcpecommander.theOvercasted.entity.entities.fatties;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.entity.entities.EntityBasicChampion;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityConjoinedSack extends EntityBasicChampion {

	public EntityConjoinedSack(World worldIn) {
		super(worldIn);
		this.setSize(1.1f, 1.3f);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

	}


}
