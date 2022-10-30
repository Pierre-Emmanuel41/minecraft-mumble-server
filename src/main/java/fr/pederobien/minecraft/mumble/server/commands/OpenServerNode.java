package fr.pederobien.minecraft.mumble.server.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.minecraft.mumble.server.MumbleServerPlugin;
import fr.pederobien.mumble.server.exceptions.MumbleServerTypeDismatchException;
import fr.pederobien.mumble.server.impl.MathHelper;
import fr.pederobien.mumble.server.impl.SimpleMumbleServer;

public class OpenServerNode extends MumbleServerNode {
	private MumbleServerCommandTree tree;
	private Path path;

	/**
	 * Creates a server in order to open a simple mumble server or a stand-alone mumble server.
	 * 
	 * @param tree The command tree associated to this node.
	 * @param path The path to the folder in which the configuration file of a mumble server is written.
	 */
	protected OpenServerNode(MumbleServerCommandTree tree, Path path) {
		super(() -> tree.getServer(), "open", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__OPEN__EXPLANATION, s -> true);
		this.tree = tree;
		this.path = path;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		String[] servers = path.toFile().list();
		switch (args.length) {
		case 1:
			Stream<String> stream = Stream.of(servers);
			return filter(stream.filter(server -> !server.equals("logs")).map(name -> name.substring(0, name.lastIndexOf("."))), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__OPEN__NAME_IS_MISSING).build());
			return false;
		}

		try {
			SimpleMumbleServer server = new SimpleMumbleServer(name, path.toFile().getAbsolutePath());
			server.open();
			tree.setServer(server);

			for (Player player : MumbleServerPlugin.instance().getServer().getOnlinePlayers()) {
				// Player's X coordinate
				double x = player.getLocation().getX();

				// Player's y coordinate
				double y = -player.getLocation().getZ();

				// Player's X coordinate
				double z = player.getLocation().getY();

				// Player's X coordinate
				double yaw = MathHelper.inRange(Math.toRadians(-player.getLocation().getYaw() - 90));

				// Player's X coordinate
				double pitch = Math.toRadians(player.getLocation().getPitch());

				getServer().getPlayers().add(player.getName(), player.getAddress(), player.isOp(), x, y, z, yaw, pitch);
			}

			sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__OPEN__SIMPLE_SERVER_OPENED, name, server.getConfigurationPort(), server.getVocalPort());
			return true;
		} catch (MumbleServerTypeDismatchException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__OPEN__SERVER_TYPE_DISMATCH, name, e.getExpected(), e.getActual()));
			return false;
		}
	}
}