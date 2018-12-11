package mcpecommander.theOvercasted.item;

import java.util.List;

import com.google.common.collect.Lists;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.StatsProvider;
import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import mcpecommander.theOvercasted.item.effects.Attribute;
import mcpecommander.theOvercasted.item.effects.Effect;
import mcpecommander.theOvercasted.item.effects.Effect.ActionType;
import mcpecommander.theOvercasted.item.effects.StatEffect;
import mcpecommander.theOvercasted.item.effects.TearEffect;
import mcpecommander.theOvercasted.item.teisr.ItemTearStackRenderer;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemTear extends Item implements IHasModel{
	
	public ItemTear() {
		setMaxStackSize(1);
		setRegistryName(Reference.ModItems.TEAR.getRegistryName());
		setUnlocalizedName(Reference.ModItems.TEAR.getName());
		setCreativeTab(TheOvercasted.overcastedTab);
		Registry.ITEMS.add(this);
		setTileEntityItemStackRenderer(new ItemTearStackRenderer());
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(handIn);
			NBTTagCompound tag = stack.getTagCompound();
			boolean right = true;
			if(tag != null) {
				right = tag.getBoolean("right");
			}else {
				tag = new NBTTagCompound();
			}
			Vec3d eye = Vec3d.ZERO;
			if(right) {
				eye = getVectorForRotation(playerIn.rotationPitch, playerIn.rotationYaw + 90);
			}else {
				eye = getVectorForRotation(playerIn.rotationPitch, playerIn.rotationYaw - 90);
			}
			NonNullList<EntityTear> tears = NonNullList.create();
			tears.add(new EntityTear(worldIn, playerIn, tag.getIntArray("items")));
			List<Effect> effects = getContinuousEffects(stack);
			for(Effect effect : effects) {
				if(effect instanceof TearEffect) {
					((TearEffect) effect).onCreation(tears);
				}
			}
			for(int x = 0; x < tears.size(); x ++) {
				Vec3d target = getVectorForRotation(playerIn.rotationPitch, playerIn.rotationYaw + (float)(x - tears.size()/2f) * 5f);
				tears.get(x).shoot(playerIn.getPositionVector().addVector(eye.x/8f, playerIn.eyeHeight - 0.1f, eye.z/8f), playerIn, target);
				worldIn.spawnEntity(tears.get(x));
			}
			tag.setBoolean("right", !right);
			stack.setTagCompound(tag);
			IStats stat = playerIn.getCapability(StatsProvider.STATS_CAP, null);
			if(stat != null) {
				playerIn.getCooldownTracker().setCooldown(this, (int) stat.getFireRate());
			}
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}else {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
		}
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		if(!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
			this.onItemRightClick(world, player, hand);
		}
		return EnumActionResult.FAIL;
	}
	
	private static final Vec3d getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
	
	public void onTagChange(World world, EntityPlayer player, ItemStack stack, Attribute attribute) {
		for(Effect effect : attribute.getEffects()) {
			if(effect instanceof StatEffect) {
				((StatEffect) effect).applyAttibutes(player);
			}
		}
		
	}
	
	public List<Effect> getContinuousEffects(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		List<Effect> list = Lists.newArrayList();
		if(tag == null || tag.getIntArray("items").length == 0) {
			return list;
		}else {
			int[] items = tag.getIntArray("items");
			for(int x = 0; x < items.length; x++) {
				if(items[x] == 0)break;
				
				Attribute attribute = Attribute.getAttributeById(items[x]);
				for(Effect effect : attribute.getEffects()) {
					if(effect.getType() == ActionType.CONTINUOUS)
						list.add(effect);
				}
				
			}
			return list;
		}
	}
	
//	@Override
//	public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
//		super.readNBTShareTag(stack, nbt);
//		System.out.println(stack.getItem() + ", " + nbt);
//	}
//	
//	@Override
//	public NBTTagCompound getNBTShareTag(ItemStack stack) {
//		return super.getNBTShareTag(stack);
//	}
//	
//	@Override
//	public boolean updateItemStackNBT(NBTTagCompound nbt) {
//		System.out.println(nbt);
//		return super.updateItemStackNBT(nbt);
//	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		return false;
	}

}
