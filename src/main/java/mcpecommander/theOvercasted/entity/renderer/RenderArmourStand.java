package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.EntityArmourStand;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.renderer.layers.LayerHeldItemCraftStudio;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderArmourStand extends RenderLiving<EntityArmourStand>{
	
	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "armour_stand", 64, 32);

	public RenderArmourStand(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0f);
		this.addLayer(new LayerHeldItemCraftStudio(this));
		
	}
	
	@Override
	protected void renderModel(EntityArmourStand entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		ModelRenderer base = model.getModelRendererFromName("StandBase");
		base.showModel = entitylivingbaseIn.getAttacking() == (byte)0;
		if(entitylivingbaseIn.getAttacking() != 0)GlStateManager.translate(0, 0.08, 0);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityArmourStand entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/armour_stand.png");
	}
	
	public static class Factory<T extends EntityArmourStand> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderArmourStand(manager);
		}

	}

}
