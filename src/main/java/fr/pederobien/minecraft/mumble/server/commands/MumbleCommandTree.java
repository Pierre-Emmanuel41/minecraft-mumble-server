package fr.pederobien.minecraft.mumble.server.commands;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeNode;
import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.minecraft.platform.commands.common.DetailsNode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleCommandTree {
	private IMumbleServer mumbleServer;

	private IMinecraftCodeNode root;
	private ChannelsNode channelsNode;
	private KickPlayerMumbleNode kickNode;
	private MutePlayerMumbleNode muteNode;
	private DeafenPlayerMumbleNode deafenNode;
	private DetailsNode<IMumbleServer> detailsNode;

	public MumbleCommandTree(IMumbleServer server) {
		this.mumbleServer = server;

		root = new MinecraftCodeRootNode("mumble", EMumbleCode.MUMBLE_EXPLANATION, () -> true);
		root.add(channelsNode = new ChannelsNode(server));
		root.add(kickNode = new KickPlayerMumbleNode(server));
		root.add(muteNode = new MutePlayerMumbleNode(server));
		root.add(deafenNode = new DeafenPlayerMumbleNode(server));
		root.add(detailsNode = new DetailsMumbleNode(server).getDetailsNode());
	}

	/**
	 * @return The mumble server associated to this tree.
	 */
	public IMumbleServer getMumbleServer() {
		return mumbleServer;
	}

	/**
	 * @return The root associated to this tree.
	 */
	public IMinecraftCodeNode getRoot() {
		return root;
	}

	/**
	 * @return The node that modifies channels on the mumble server.
	 */
	public ChannelsNode getChannelsNode() {
		return channelsNode;
	}

	/**
	 * @return The node that kicks players from channels.
	 */
	public KickPlayerMumbleNode getKickNode() {
		return kickNode;
	}

	/**
	 * @return The node that mutes players when registered in a channel.
	 */
	public MutePlayerMumbleNode getMuteNode() {
		return muteNode;
	}

	/**
	 * @return The node that deafens players when registered in a channel.
	 */
	public DeafenPlayerMumbleNode getDeafenNode() {
		return deafenNode;
	}

	/**
	 * @return The node that display the details of the mumble server.
	 */
	public DetailsNode<IMumbleServer> getDetailsNode() {
		return detailsNode;
	}
}
