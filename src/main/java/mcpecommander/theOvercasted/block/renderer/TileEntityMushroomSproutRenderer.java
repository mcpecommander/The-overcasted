package mcpecommander.theOvercasted.block.renderer;

import com.leviathanstudio.craftstudio.common.animation.simpleImpl.CSTileEntitySpecialRenderer;

import mcpecommander.theOvercasted.block.tileEntity.TileEntityMushroomSprout;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntityMushroomSproutRenderer extends CSTileEntitySpecialRenderer<TileEntityMushroomSprout>{

	public TileEntityMushroomSproutRenderer(String modid, String modelNameIn, int textureWidth, int textureHeigth,
			ResourceLocation texture) {
		super(modid, modelNameIn, textureWidth, textureHeigth, texture);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void render(TileEntityMushroomSprout te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		EnumFacing enumfacing = EnumFacing.getFront(te.getBlockMetadata() & 7);
		GlStateManager.pushMatrix();
        // Correction of the position.
        GlStateManager.translate(x + 0.5D, y + 1.5D, z + 0.5D);
        // Correction of the rotation.
        GlStateManager.multMatrix(CSTileEntitySpecialRenderer.ROTATION_CORRECTOR);
        GlStateManager.rotate(enumfacing.getHorizontalAngle(), 0, 1, 0);
        this.bindTexture(this.texture); // Binding the texture.
        this.model.render(te); // Rendering the model.
        GlStateManager.popMatrix();
    }

}
