package mcpecommander.theOvercasted.entity.renderer.fliesRenderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.flies.EntityFly;
import mcpecommander.theOvercasted.entity.models.CSModelRendererOvercasted;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.renderer.layers.LayerFlyGlow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public abstract class RenderFly<T extends EntityFly> extends RenderLiving<T>{

	protected RenderFly(RenderManager rendermanagerIn, CraftStudioModelSon model) {
		super(rendermanagerIn, model, 0f);
//		this.addLayer(new LayerFlyGlow());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFly entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/fly.png");
	}
	
	

}

