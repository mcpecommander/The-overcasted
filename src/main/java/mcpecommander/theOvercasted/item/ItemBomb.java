package mcpecommander.theOvercasted.item;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.entity.entities.pickups.EntityBomb;
import mcpecommander.theOvercasted.entity.entities.pickups.EntityItemBomb;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBomb extends Item implements IHasModel{
	
	public ItemBomb() {
		this.setRegistryName(Reference.ModItems.BOMB.getRegistryName());
		this.setUnlocalizedName(Reference.ModItems.BOMB.getName());
		this.setCreativeTab(TheOvercasted.overcastedTab);
		Registry.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }else {
        	if(facing != EnumFacing.DOWN) {
        		BlockPos newPos = pos.add(facing.getDirectionVec());
        		EntityBomb bomb = new EntityBomb(worldIn, player, new Vec3d(newPos).addVector(0.5d, 0, 0.5d));
        		if(worldIn.getBlockState(newPos.down()).canEntitySpawn(bomb)) {
        			worldIn.spawnEntity(bomb);
        			return EnumActionResult.SUCCESS;
        		}
        	}
        }
		return EnumActionResult.FAIL;
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		EntityItem item = new EntityItemBomb(world, location.posX, location.posY, location.posZ, itemstack);
		item.setDefaultPickupDelay();
		return item;
	}
}
