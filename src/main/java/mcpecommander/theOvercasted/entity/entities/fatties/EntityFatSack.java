package mcpecommander.theOvercasted.entity.entities.fatties;

import java.util.Random;

import mcpecommander.theOvercasted.entity.entities.EntityBasicChampion;
import mcpecommander.theOvercasted.particle.GushingBloodParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class EntityFatSack extends EntityBasicChampion{
	
	private static final DataParameter<Byte> TYPE = EntityDataManager.<Byte>createKey(EntityFatSack.class, DataSerializers.BYTE);
	
	public EntityFatSack(World worldIn) {
		super(worldIn);
		this.setSize(1.1f, 1.3f);
		if(this.getSackType() == -1) {
			this.setSackType((byte) 0);
		}
	}
	
	/**
	 * Spawns a sack with a special 
	 * @param world
	 * @param type
	 */
	public EntityFatSack(World world, byte type) {
		this(world);
		this.dataManager.set(TYPE, type);
	}
	
	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
	}
	
	public byte getSackType() {
		return this.dataManager.get(TYPE).byteValue();
	}
	
	public void setSackType(byte sackType) {
		this.dataManager.set(TYPE, sackType);
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		if(!this.world.isRemote) {
			if(this.getSackType() == 1 && this.getRNG().nextFloat() > .7f) {
				this.setSackType((byte) 2);
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16D);
				this.setHealth(16f);
				return;
			}
			if(this.getSackType() == 2 && this.getRNG().nextFloat() > .5f) {
				this.setSackType((byte) 3);
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
				this.setHealth(10f);
				return;
			}
		}
		super.onDeath(cause);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TYPE, (byte)-1);
	}
	
	@Override
	public String getName() {
		if(this.getSackType() == 2) {
			return I18n.translateToLocal("entity.blubber.name");
		}else if (this.getSackType() == 1) {
			return I18n.translateToLocal("entity.pale_sack.name");
		}else if(this.getSackType() == 3){
			return I18n.translateToLocal("entity.half_sack.name");
		}else {
			return I18n.translateToLocal("entity.fat_sack.name");
		}
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.world.isRemote) {
			if(this.getSackType() == 2 && this.isEntityAlive()) {
				Minecraft.getMinecraft().effectRenderer.addEffect(GushingBloodParticle.createParticle(world, posX, posY + this.height - 0.3, posZ));
			}else if(this.getSackType() == 3 && this.isEntityAlive()) {
				Minecraft.getMinecraft().effectRenderer.addEffect(GushingBloodParticle.createParticle(world, posX, posY + this.height - 0.7, posZ));
			}
		}
	}

}
