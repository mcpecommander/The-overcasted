package mcpecommander.theOvercasted.entity.entities;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.init.ModSounds;
import mcpecommander.theOvercasted.util.AnimationHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityManEater extends EntityMob implements IAnimated {

	private static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(EntityManEater.class);

	static {
		EntityManEater.animHandler.addAnim(Reference.MODID, "man_eat", "man_eater", false);
	}

	public EntityManEater(World worldIn) {
		super(worldIn);
		this.setSize(1.2f, 0.3f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
	}

	@Override
	protected void onDeathUpdate() {
		super.onDeathUpdate();
		if (this.isBeingRidden()) {
			this.getPassengers().forEach(entity -> entity.dismountRidingEntity());
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.rotationYawHead = 0;
		this.getAnimationHandler().animationsUpdate(this);
		if (this.isWorldRemote()) {
			if (this.isBeingRidden()) {
				AnimationHelper.startHoldAnimation(animHandler, this, "man_eat");
			} else {
				AnimationHelper.stopAnimation(animHandler, this, "man_eat");
			}
		} else {

		}
		if (this.isBeingRidden() && this.getPassengers().get(0) instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) this.getPassengers().get(0);
			if (this.ticksExisted % 3 == 0 && !player.capabilities.isCreativeMode)
				this.attackEntityAsMob(player);
			// The cheapest way to stop a player from dismounting this.
			if (player.isSneaking()) {
				player.setSneaking(false);
			}
		} else {
			this.setSize(1.2f, 0.2f);
		}
	}

	// We increase the size to make it easier to attack the mob when being trapped
	// so we adjust the mounting height too.
	@Override
	public double getMountedYOffset() {
		return this.height * .2f;
	}

	// The player is trapped not riding so we do not render it in riding position.
	@Override
	public boolean shouldRiderSit() {
		return false;
	}

	// Should not be pushed.
	@Override
	public boolean canBePushed() {
		return false;
	}

	// The fucking canCollide boolean does not stop collision so we override this
	// and add our trapping code.
	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (entityIn instanceof EntityPlayer && !this.isBeingRidden()
				&& !((EntityPlayer) entityIn).capabilities.isCreativeMode && this.deathTime < 1 && !entityIn.isRiding()
				&& !entityIn.isBeingRidden() && !((EntityPlayer) entityIn).isSpectator()) {
			entityIn.startRiding(this, true);
			this.playSound(ModSounds.man_eater_trap, 2f, 1.5f);
			this.setSize(2.3f, 2.3f);
		}
	}

	@Override
	public <T extends IAnimated> AnimationHandler<T> getAnimationHandler() {
		return EntityManEater.animHandler;
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public double getX() {
		return this.posX;
	}

	@Override
	public double getY() {
		return this.posY;
	}

	@Override
	public double getZ() {
		return this.posZ;
	}

	@Override
	public boolean isWorldRemote() {
		return this.world.isRemote;
	}

}
