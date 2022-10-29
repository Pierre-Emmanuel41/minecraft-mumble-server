package fr.pederobien.minecraft.mumble.server.commands;

import java.util.function.Supplier;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class ChannelRemoveNode extends MumbleServerNode {
	private ChannelRemoveChannelNode channelNode;
	private ChannelRemovePlayerNode playerNode;

	/**
	 * Creates a node in order to remove channels from a mumble server or players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelRemoveNode(Supplier<IMumbleServer> server) {
		super(server, "remove", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelRemoveChannelNode(server));
		add(playerNode = new ChannelRemovePlayerNode(server));
	}

	/**
	 * @return The node that removes channels from a mumble server.
	 */
	public ChannelRemoveChannelNode getChannelNode() {
		return channelNode;
	}

	/**
	 * @return The node that remove players from a channel.
	 */
	public ChannelRemovePlayerNode getPlayerNode() {
		return playerNode;
	}
}
