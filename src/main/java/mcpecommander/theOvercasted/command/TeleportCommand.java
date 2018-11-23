package mcpecommander.theOvercasted.command;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.maze.DungeonWorldProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TeleportCommand extends CommandBase {

	public TeleportCommand() {
		aliases = Lists.newArrayList(Reference.MODID, "TP", "tp");
	}

	private final List<String> aliases;

	@Override
	@Nonnull
	public String getName() {
		return "tp";
	}

	@Override
	@Nonnull
	public String getUsage(@Nonnull ICommandSender sender) {
		return "tp <id>";
	}

	@Override
	@Nonnull
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args)
			throws CommandException {
		if (args.length < 1) {
			return;
		}
		String s = args[0];
		int dim;
		try {
			dim = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error parsing dimension!"));
			return;
		}

		if (sender instanceof EntityPlayer) {
			DungeonWorldProvider provider = (DungeonWorldProvider) FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(100).provider;
			CustomTeleporter.teleportToDimension((EntityPlayer) sender, dim, provider.getDungeon().getSpawnPos().getX(), provider.getDungeon().getSpawnPos().getY(), provider.getDungeon().getSpawnPos().getZ());
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	@Nonnull
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}
}