package mcpecommander.theOvercasted.events;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.init.ModPotions;
import mcpecommander.theOvercasted.util.DebuggerVisualizer;
import mcpecommander.theOvercasted.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class ClientEvents {

	
	private static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation(Reference.MODID, "textures/particle/beam.png");
	public static List<Vec3d> vector1 = Lists.newArrayList();
	public static List<Vec3d> vector2 = Lists.newArrayList();
	public static AxisAlignedBB boundingBox = Block.NULL_AABB;
	public static Vec3d vector;
	public static int timer = 0;

	
	@SubscribeEvent
	public static void inputEvent(InputUpdateEvent e) {
		if(e.getEntityPlayer().isPotionActive(ModPotions.potionStasis)) {
			e.getMovementInput().moveForward = 0;
			e.getMovementInput().moveStrafe = 0;
		}
	}
	
	@SubscribeEvent
	public static void renderWorld(RenderWorldLastEvent render) {
		if(!vector1.isEmpty() && !vector2.isEmpty() && vector1.size() == vector2.size() && timer-- > 0) {
			for(int x = 0; x < vector1.size(); x ++) {
				DebuggerVisualizer.RenderLine(vector1.get(x), vector2.get(x), render.getPartialTicks());
			}
		}
		if(boundingBox != null && timer-- > 0) {
			DebuggerVisualizer.renderBox(boundingBox, render.getPartialTicks());
		}
	
		if(vector != null && timer-- > 0) {
			DebuggerVisualizer.renderSphere(vector, new Color(200, 200, 200, 255), 0.5f, render.getPartialTicks());
		}
		
		if(timer < 0) {
			vector1.clear();
			vector2.clear();
			boundingBox = Block.NULL_AABB;
			vector = null;
		}
		if(timer-- > 0) {
			RenderUtils.drawBeam(Minecraft.getMinecraft().player.getPositionVector(), 5, render.getPartialTicks(), true, 15f
					,TEXTURE_BEACON_BEAM);
		}
		
	}


}
