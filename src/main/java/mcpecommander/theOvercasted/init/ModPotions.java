package mcpecommander.theOvercasted.init;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.potion.PotionStasis;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModPotions {
	
	@GameRegistry.ObjectHolder("overcasted:stasis_potion")
	public static PotionStasis potionStasis;

}
