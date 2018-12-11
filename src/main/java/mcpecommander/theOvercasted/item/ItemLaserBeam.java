package mcpecommander.theOvercasted.item;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.events.ClientEvents;
import mcpecommander.theOvercasted.item.teisr.ItemLaserBeamStackRenderer;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemLaserBeam extends Item implements IHasModel{
	
	public ItemLaserBeam() {
		setMaxStackSize(1);
		setRegistryName(Reference.ModItems.LASER_BEAM.getRegistryName());
		setUnlocalizedName(Reference.ModItems.LASER_BEAM.getName());
		Registry.ITEMS.add(this);
		setCreativeTab(TheOvercasted.overcastedTab);
		setTileEntityItemStackRenderer(new ItemLaserBeamStackRenderer());
	}
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(stack.getMaxItemUseDuration() - timeLeft > 20 && worldIn.isRemote) {
			ClientEvents.timer = 200;
			NBTTagCompound tag = stack.getTagCompound();
			if(tag != null) {
				tag.setInteger("use_ticks", 0);
			}
		}
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		if(player.world.isRemote) {
			NBTTagCompound tag = stack.getTagCompound();
			if(tag == null) {
				tag = new NBTTagCompound();
			}
			if(stack.getMaxItemUseDuration() - player.getItemInUseCount() < 21) {
				tag.setInteger("use_ticks", stack.getMaxItemUseDuration() - player.getItemInUseCount());
			}
			stack.setTagCompound(tag);
		}
		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(!stack.isEmpty()) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		return false;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		int charge = 0;
		if(tag != null) {
			charge = tag.getInteger("charge");
		}
		if(charge == 0) {
			return 7200;
		}
		return charge;
	}

}
