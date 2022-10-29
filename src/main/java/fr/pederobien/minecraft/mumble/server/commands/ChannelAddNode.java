package fr.pederobien.minecraft.mumble.server.commands;

import java.util.function.Supplier;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class ChannelAddNode extends MumbleServerNode {
	private ChannelAddChannelNode channelNode;
	private ChannelAddPlayerNode playerNode;

	/**
	 * Creates a node in order to add a channel to a mumble server or players to a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelAddNode(Supplier<IMumbleServer> server) {
		super(server, "add", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelAddChannelNode(server));
		add(playerNode = new ChannelAddPlayerNode(server));
	}

	/**
	 * @return The node that adds a channel to a mumble server.
	 */
	public ChannelAddChannelNode getChannelNode() {
		return channelNode;
	}

	/**
	 * @return The node that add players to a channel.
	 */
	public ChannelAddPlayerNode getPlayerNode() {
		return playerNode;
	}
}
