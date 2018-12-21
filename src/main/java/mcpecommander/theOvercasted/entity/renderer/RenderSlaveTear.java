package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.entity.entities.projectiles.EntitySlaveTear;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSlaveTear extends RenderTear<EntitySlaveTear> {

	protected RenderSlaveTear(RenderManager renderManager) {
		super(renderManager);
	}
	
	public static class Factory<T extends EntitySlaveTear> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderSlaveTear(manager);
		}

	}

}
