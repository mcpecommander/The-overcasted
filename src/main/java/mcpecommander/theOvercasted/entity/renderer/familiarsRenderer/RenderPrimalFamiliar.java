package mcpecommander.theOvercasted.entity.renderer.familiarsRenderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityPrimalFamiliar;
import mcpecommander.theOvercasted.entity.entities.familiars.EntityPrimalFamiliar.Type;
import mcpecommander.theOvercasted.entity.models.CraftStudioModelSon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderPrimalFamiliar extends RenderLiving<EntityPrimalFamiliar>{
	
	private static final CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "primal_familiar", 64, 64);
	private static final ResourceLocation BOBBY = new ResourceLocation(Reference.MODID, "textures/entity/bobby.png");
	private static final ResourceLocation MAGGY = new ResourceLocation(Reference.MODID, "textures/entity/maggy.png");

	public RenderPrimalFamiliar(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPrimalFamiliar entity) {

		return entity.getType() == Type.BOBBY.id ? BOBBY : MAGGY;
	}
	
	public static class Factory<T extends EntityPrimalFamiliar> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderPrimalFamiliar(manager);
		}

	}
	

}
