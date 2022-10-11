package fr.pederobien.minecraft.mumble.server.commands;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;
import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleServerCommandTree {
	private IMumbleServer server;
	private IMinecraftCodeRootNode root;
	private OpenServerNode openNode;
	private CloseServerNode closeNode;
	private ChannelNode channelNode;
	private DetailsNode detailsNode;

	public MumbleServerCommandTree() {
		root = new MinecraftCodeRootNode("mumble", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__ROOT__EXPLANATION, () -> true);

		root.add(openNode = new OpenServerNode(this));
		root.add(closeNode = new CloseServerNode(this));
		root.add(channelNode = new ChannelNode(() -> getServer()));
		root.add(detailsNode = new DetailsNode(() -> getServer()));
	}

	/**
	 * @return The mumble server associated to this tree.
	 */
	public IMumbleServer getServer() {
		return server;
	}

	/**
	 * Set the server associated to this command tree
	 * 
	 * @param server The new server managed by this command tree
	 */
	public void setServer(IMumbleServer server) {
		this.server = server;
	}

	/**
	 * @return The root associated to this tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node that opens a mumble server.
	 */
	public OpenServerNode getOpenNode() {
		return openNode;
	}

	/**
	 * @return The node that closes a mumble server
	 */
	public CloseServerNode getCloseNode() {
		return closeNode;
	}

	/**
	 * @return The node that adds or removes channel from a mumble server or adds/removes players from a channel.
	 */
	public ChannelNode getChannelNode() {
		return channelNode;
	}

	/**
	 * @return The node that displays the current configuration of a mumble server.
	 */
	public DetailsNode getDetailsNode() {
		return detailsNode;
	}
}
