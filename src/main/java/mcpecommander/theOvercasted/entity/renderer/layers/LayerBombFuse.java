package mcpecommander.theOvercasted.entity.renderer.layers;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.pickups.EntityBomb;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.renderer.pickupsRenderer.RenderBomb;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class LayerBombFuse implements LayerEntityRenderer<EntityBomb> {
	
	private RenderBomb renderer;
	private static final CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "bomb_layer", 128, 64);
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/bomb.png");
	
	public LayerBombFuse(RenderBomb renderer) {
		this.renderer = renderer;
	}

	@Override
	public void doRenderLayer(EntityBomb entity, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		GlStateManager.pushMatrix();
		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		renderer.bindTexture(texture);
		GlStateManager.popMatrix();
		
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

}
