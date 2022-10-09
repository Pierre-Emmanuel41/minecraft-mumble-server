package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.mumble.server.exceptions.MumbleServerTypeDismatchException;
import fr.pederobien.mumble.server.impl.SimpleMumbleServer;

public class OpenServerNode extends MumbleServerNode {
	private MumbleServerCommandTree tree;

	/**
	 * Creates a server in order to open a simple mumble server or a stand-alone mumble server.
	 * 
	 * @param tree The command tree associated to this node.
	 */
	protected OpenServerNode(MumbleServerCommandTree tree) {
		super(() -> tree.getServer(), "open", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__OPEN__EXPLANATION, s -> true);
		this.tree = tree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__NAME__COMPLETION));
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
			SimpleMumbleServer server = new SimpleMumbleServer(name, Platform.ROOT.resolve("Mumble").toAbsolutePath().toString());
			server.open();
			tree.setServer(server);
			sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__OPEN__SIMPLE_SERVER_OPENED, name, server.getConfigurationPort(), server.getVocalPort());
			return true;
		} catch (MumbleServerTypeDismatchException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__OPEN__SERVER_TYPE_DISMATCH, name, e.getExpected(), e.getActual()));
			return false;
		}
	}
}