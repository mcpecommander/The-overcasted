package mcpecommander.theOvercasted.maze;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

import net.minecraft.util.ResourceLocation;

public class RoomLayout {
	
	private String name;
	private ResourceLocation[][] mobs;
	private ResourceLocation[][] deco;
	private RoomType type;
	

	public RoomLayout(String name, RoomType type, ResourceLocation[][] mobs, ResourceLocation[][] deco ) {
		super();
		this.name = name;
		this.type = type;
		this.mobs = mobs;
		this.deco = deco;
		
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(deco);
		result = prime * result + Arrays.deepHashCode(mobs);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomLayout other = (RoomLayout) obj;
		if (!Arrays.deepEquals(deco, other.deco))
			return false;
		if (!Arrays.deepEquals(mobs, other.mobs))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoomLayout [name=" + name + ", mobs=" + Arrays.toString(mobs) + ", deco=" + Arrays.toString(deco)
				+ ", type=" + type + "]";
	}

	public ResourceLocation[][] getMobs() {
		return mobs;
	}

	public void setMobs(ResourceLocation[][] mobs) {
		this.mobs = mobs;
	}

	public ResourceLocation[][] getDeco() {
		return deco;
	}

	public void setDeco(ResourceLocation[][] deco) {
		this.deco = deco;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public RoomType getType() {
		return type;
	}




	public void setType(RoomType type) {
		this.type = type;
	}

	public enum RoomType{
		@SerializedName("normal")
		NORMAL,
		@SerializedName("double_normal")
		DOUBLE_NORMAL,
		@SerializedName("wide")
		WIDE,
		@SerializedName("narrow")
		NARROW,
		@SerializedName("double_narrow")
		DOUBLE_NARROW;
	}
	

}
