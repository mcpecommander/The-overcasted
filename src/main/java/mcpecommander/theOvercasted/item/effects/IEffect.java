package mcpecommander.theOvercasted.item.effects;

import javax.annotation.Nullable;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public interface IEffect {
	
	default void onGet(EntityPlayer player, ItemStack stack, Attribute attribute) {
		
	};
	default void onTearCreation(EntityPlayer entity, NonNullList<EntityTear> tears, Attribute attribute) {
		
	};
	default void onEntityTearUpdate(EntityTear tear) {
		
	};
	default void onEntityTearHit(EntityTear tear, @Nullable Entity hitTarget, @Nullable BlockPos hitPos) {
		
	};

}
