package mcpecommander.theOvercasted.events;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.pickupsSystem.IPickups;
import mcpecommander.theOvercasted.pickupsSystem.PickupsProvider;
import mcpecommander.theOvercasted.proxy.CommonProxy;
import mcpecommander.theOvercasted.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class PickupsEvents {
	
	private static final ResourceLocation BOMB = new ResourceLocation(Reference.MODID, "textures/gui/bomb.png");
	private static final ResourceLocation COIN = new ResourceLocation(Reference.MODID, "textures/gui/coin.png");
	private static final ResourceLocation KEY = new ResourceLocation(Reference.MODID, "textures/gui/key.png");
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event) {
		if(!event.player.world.isRemote) {
			IPickups cap = event.player.getCapability(PickupsProvider.PICKUPS, null);
			if(cap != null) {
				BlockPos pickups = new BlockPos(cap.getBombs(), cap.getCoins(), cap.getKeys());
				CommonProxy.CHANNEL.sendTo(new PacketSendVec3i(pickups), (EntityPlayerMP) event.player);
			}
		}
	}
	
	@SubscribeEvent
	public static void renderCapbilityOverlay(RenderGameOverlayEvent.Post event) {
		double scale = 10;
		if(event.getType() == ElementType.ALL) {
			IPickups pickups = Minecraft.getMinecraft().player.getCapability(PickupsProvider.PICKUPS, null);
			if(pickups != null) {
				Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getCoins(), 16, 62, Color.BLACK.getRGB());
				RenderUtils.drawTexture(COIN, 2, 60, scale, new Color(255, 255, 255, 255));
				Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getBombs(), 16, 74, Color.BLACK.getRGB());
				RenderUtils.drawTexture(BOMB, 2, 72, scale, new Color(255, 255, 255, 255));
				Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getKeys(), 16, 86, Color.BLACK.getRGB());
				RenderUtils.drawTexture(KEY, 2, 84, scale, new Color(255, 255, 255, 255));
			}
			
		}
		
	}
	

}
