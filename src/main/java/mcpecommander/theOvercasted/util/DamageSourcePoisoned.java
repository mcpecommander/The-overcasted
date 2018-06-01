package mcpecommander.theOvercasted.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public class DamageSourcePoisoned extends DamageSource{
	
	private TileEntity mushroom;

	public DamageSourcePoisoned(TileEntity mushroom) {
		super("mushroomPoison");
		this.setDifficultyScaled();
		this.mushroom = mushroom;
	}
	
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        return new TextComponentString(entityLivingBaseIn.getName() + " was poisoned by " + mushroom.getBlockType().getUnlocalizedName());
    }

}
