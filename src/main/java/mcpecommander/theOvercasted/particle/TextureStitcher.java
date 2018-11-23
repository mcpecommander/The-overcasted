package mcpecommander.theOvercasted.particle;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitcher {

	@SubscribeEvent
	public void stitcherEventPre(TextureStitchEvent event) {
		ResourceLocation lava = new ResourceLocation(Reference.MODID ,"particle/lava_particle");
	    event.getMap().registerSprite(lava);
	    ResourceLocation stinky = new ResourceLocation(Reference.MODID ,"particle/stink_particle");
	    event.getMap().registerSprite(stinky);
	}

}
