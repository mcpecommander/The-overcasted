package mcpecommander.theOvercasted.entity.renderer.layers;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface LayerEntityRenderer<E extends Entity>
{
    void doRenderLayer(E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale);

    boolean shouldCombineTextures();
}
