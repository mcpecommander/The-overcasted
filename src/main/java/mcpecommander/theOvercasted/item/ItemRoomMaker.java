package mcpecommander.theOvercasted.item;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
import mcpecommander.theOvercasted.entity.entities.EntityOverseer;
import mcpecommander.theOvercasted.maze.DungeonPopulater;
import mcpecommander.theOvercasted.maze.DungeonWorldProvider;
import mcpecommander.theOvercasted.registryHandler.Registry;
import mcpecommander.theOvercasted.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRoomMaker extends Item implements IHasModel{

	public ItemRoomMaker() {
		this.setMaxStackSize(1);
		this.setRegistryName(Reference.ModItems.ROOM_MAKER.getRegistryName());
		this.setUnlocalizedName(Reference.ModItems.ROOM_MAKER.getName());
		this.setCreativeTab(TheOvercasted.overcastedTab);
		Registry.ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
		if(!world.isRemote) {
			if(playerIn.dimension == 100) {
				EntityOverseer entity = DungeonPopulater.getOverseerByChunk((DungeonWorldProvider) world.provider,
						world.getChunkFromBlockCoords(playerIn.getPosition()));
				entity.serializeRoom();
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
			}
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
		}else {
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		this.onItemRightClick(worldIn, player, hand);
		return EnumActionResult.FAIL;
	}
	
	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}

}
