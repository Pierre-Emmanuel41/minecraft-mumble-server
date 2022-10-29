package fr.pederobien.minecraft.mumble.server.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;

public class CloseServerNode extends MumbleServerNode {
	private MumbleServerCommandTree tree;

	/**
	 * Creates a node to close a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected CloseServerNode(MumbleServerCommandTree tree) {
		super(() -> tree.getServer(), "close", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CLOSE__EXPLANATION, s -> s != null);
		this.tree = tree;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name = tree.getServer().getName();
		tree.getServer().close();
		tree.setServer(null);
		sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CLOSE__SERVER_CLOSED, name);
		return true;
	}
}
