package mcpecommander.theOvercasted.block.renderer;

import com.leviathanstudio.craftstudio.common.animation.simpleImpl.CSTileEntitySpecialRenderer;

import mcpecommander.theOvercasted.block.tileEntity.TileEntityMushroomSprout;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMushroomSproutRenderer extends CSTileEntitySpecialRenderer<TileEntityMushroomSprout>{

	public TileEntityMushroomSproutRenderer(String modid, String modelNameIn, int textureWidth, int textureHeigth,
			ResourceLocation texture) {
		super(modid, modelNameIn, textureWidth, textureHeigth, texture);
	}
	
	@Override
    public void render(TileEntityMushroomSprout te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		World world = te.getWorld();
		if (te != null && world != null) {
			EnumFacing enumfacing = EnumFacing.getFront(te.getBlockMetadata() & 7);
			GlStateManager.pushMatrix();
			// Correction of the position.
			GlStateManager.translate(x + 0.5D, y + 1.5D, z + 0.5D);
			// Correction of the rotation.
			GlStateManager.multMatrix(CSTileEntitySpecialRenderer.ROTATION_CORRECTOR);
			GlStateManager.rotate(enumfacing.getHorizontalAngle(), 0, 1, 0);
		} else {
			GlStateManager.pushMatrix();
			// Correction of the position.
			GlStateManager.translate(x + 0.5D, y + 1.5D, z + 0.5D);
			// Correction of the rotation.
			GlStateManager.multMatrix(CSTileEntitySpecialRenderer.ROTATION_CORRECTOR);
		}
		if (destroyStage >= 0) {
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		this.bindTexture(this.texture); // Binding the texture.
		this.model.render(te); // Rendering the model.
		GlStateManager.popMatrix();
		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

}
