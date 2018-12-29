package mcpecommander.theOvercasted.events;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.capability.pickups.IPickups;
import mcpecommander.theOvercasted.capability.pickups.PickupsProvider;
import mcpecommander.theOvercasted.capability.stats.EnumCharacter;
import mcpecommander.theOvercasted.capability.stats.IStats;
import mcpecommander.theOvercasted.capability.stats.StatsProvider;
import mcpecommander.theOvercasted.config.OvercastedConfig;
import mcpecommander.theOvercasted.gui.PickupsGUI;
import mcpecommander.theOvercasted.networking.PacketSendStats;
import mcpecommander.theOvercasted.networking.PacketSendVec3i;
import mcpecommander.theOvercasted.proxy.ClientProxy;
import mcpecommander.theOvercasted.proxy.CommonProxy;
import mcpecommander.theOvercasted.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class CapabilityEvents {
	
	public static boolean isUPressed;
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event) {
		if(!event.player.world.isRemote) {
			IPickups cap = event.player.getCapability(PickupsProvider.PICKUPS, null);
			if(cap != null) {
				BlockPos pickups = new BlockPos(cap.getBombs(), cap.getCoins(), cap.getKeys());
				CommonProxy.CHANNEL.sendTo(new PacketSendVec3i(pickups), (EntityPlayerMP) event.player);
			}
			IStats stat = event.player.getCapability(StatsProvider.STATS_CAP, null);
			if(stat != null) {
				stat.setDefaults(EnumCharacter.ISAAC);
				CommonProxy.CHANNEL.sendTo(new PacketSendStats(stat), (EntityPlayerMP) event.player);
			}
		}
	}
	
	@SubscribeEvent
	public static void playerTick(PlayerTickEvent event) {
//		if(event.player.ticksExisted % 10 == 0) {
//			if(event.side == Side.SERVER) {
//				IStats stat = event.player.getCapability(StatsProvider.STATS_CAP, null);
//				CommonProxy.CHANNEL.sendTo(new PacketSendStats(stat), (EntityPlayerMP) event.player);
//			}
//		}
		
	}
	
	@SubscribeEvent
	public static void renderCapbilityOverlay(RenderGameOverlayEvent.Post event) {
		int x = OvercastedConfig.hud.xPos;
		int y = OvercastedConfig.hud.yPos;
		int scale = OvercastedConfig.hud.scale;
		if(event.getType() == ElementType.ALL && Minecraft.getMinecraft().player.dimension == 100 && Minecraft.getMinecraft().currentScreen == null) {
			IPickups pickups = Minecraft.getMinecraft().player.getCapability(PickupsProvider.PICKUPS, null);
			IStats stats = Minecraft.getMinecraft().player.getCapability(StatsProvider.STATS_CAP, null);
			if(pickups != null && stats != null) {
				
				RenderUtils.drawTexture(Reference.GuiTextures.COIN, x, y, scale, Color.WHITE);
				Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getCoins(), x + scale + 2,
						y + scale / 4, Color.BLACK.getRGB());

				RenderUtils.drawTexture(Reference.GuiTextures.BOMB, x, y + scale + 4, scale, new Color(255, 255, 255, 240));
				Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getBombs(), x + scale + 2,
						(y + scale + 4) + scale / 4, Color.BLACK.getRGB());

				RenderUtils.drawTexture(Reference.GuiTextures.KEY, x, y + (scale + 4) * 2, scale, new Color(255, 255, 255, 240));
				Minecraft.getMinecraft().fontRenderer.drawString(": " + pickups.getKeys(), x + scale + 2,
						y + (scale + 4) * 2 + scale / 4, Color.BLACK.getRGB());
				if(OvercastedConfig.hud.extraHud) {
					RenderUtils.drawTexture(Reference.GuiTextures.SPEED, x, y + (scale + 4) * 3, scale, new Color(255, 255, 255, 240));
					Minecraft.getMinecraft().fontRenderer.drawString(
							": " + stats.getSpeed(),
							x + scale + 2, y + (scale + 4) * 3 + scale/4, Color.BLACK.getRGB());
					
					RenderUtils.drawTexture(Reference.GuiTextures.RANGE, x, y + (scale + 4) * 4, scale, new Color(255, 255, 255, 240));
					Minecraft.getMinecraft().fontRenderer.drawString(": " + stats.getRange(), x + scale + 2,
							y + (scale + 4) * 4 + scale / 4, Color.BLACK.getRGB());
					
					RenderUtils.drawTexture(Reference.GuiTextures.FIRE_RATE, x, y + (scale + 4) * 5, scale, new Color(255, 255, 255, 240));
					Minecraft.getMinecraft().fontRenderer.drawString(": " + stats.getFireRate(), x + scale + 2,
							y + (scale + 4) * 5 + scale / 4, Color.BLACK.getRGB());
					
					RenderUtils.drawTexture(Reference.GuiTextures.PROJECTILE_SPEED, x, y + (scale + 4) * 6, scale, new Color(255, 255, 255, 240));
					Minecraft.getMinecraft().fontRenderer.drawString(": " + stats.getProjectileSpeed(), x + scale + 2,
							y + (scale + 4) * 6 + scale / 4, Color.BLACK.getRGB());
					
					RenderUtils.drawTexture(Reference.GuiTextures.DAMAGE, x, y + (scale + 4) * 7, scale, new Color(255, 255, 255, 240));
					Minecraft.getMinecraft().fontRenderer.drawString(": " + stats.getDamage(), x + scale + 2,
							y + (scale + 4) * 7 + scale / 4, Color.BLACK.getRGB());
					
					RenderUtils.drawTextureFlipped(Reference.GuiTextures.CROSS, x, y + (scale + 4) * 8, scale, new Color(255, 255, 255, 240));
					Minecraft.getMinecraft().fontRenderer.drawString(": " + (int)stats.getDevilChance() + "%", x + scale + 2,
							y + (scale + 4) * 8 + scale / 4, Color.BLACK.getRGB());
					
					RenderUtils.drawTexture(Reference.GuiTextures.CROSS, x, y + (scale + 4) * 9, scale, new Color(255, 255, 255, 240));
					Minecraft.getMinecraft().fontRenderer.drawString(": " + (int)stats.getAngelChance() + "%", x + scale + 2,
							y + (scale + 4) * 9 + scale / 4, Color.BLACK.getRGB());
				}
				
			}
			
		}
		
	}
	
	@SubscribeEvent
	public static void onKeyPressed(InputEvent.KeyInputEvent event) {
		if(ClientProxy.yKey.isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new PickupsGUI());
		}
		isUPressed = ClientProxy.uKey.isPressed();
	}
	
	@SubscribeEvent
	public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Reference.MODID)) {
			ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
		}
	}
	

}
