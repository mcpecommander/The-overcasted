package mcpecommander.theOvercasted.entity.entities.familiars;

import mcpecommander.theOvercasted.entity.entities.EntityBasicChampion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityPrimalFamiliar extends EntityBasicFamiliar {
	
	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityPrimalFamiliar.class, DataSerializers.VARINT);
	private final Type type;
	
	public EntityPrimalFamiliar(World world) {
		super(world);
		this.type = null;
		
	}
	
	public EntityPrimalFamiliar(World world, EntityLivingBase master, Type type) {
		super(world, master);
		this.type = type;
		this.dataManager.set(TYPE, type.id);
		this.dataManager.setDirty(TYPE);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TYPE, -1);
	}
	
	public int getType() {
		return this.dataManager.get(TYPE);
	}


	public enum Type{
		MAGGY(67), BOBBY(8);
		
		public int id;
		
		private Type(int id) {
			this.id = id;
		}

		public static Type getByID(int id) {
			return id == MAGGY.id ? MAGGY : BOBBY;
		}
	}

}
