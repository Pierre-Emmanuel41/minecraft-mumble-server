package fr.pederobien.minecraft.mumble.server.commands;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeNode;
import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleCommandTree {
	private IMumbleServer server;
	private IMinecraftCodeNode root;
	private OpenServerNode openNode;

	public MumbleCommandTree() {
		root = new MinecraftCodeRootNode("mumble", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__ROOT__EXPLANATION, () -> true);

		root.add(openNode = new OpenServerNode(this));
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
	public IMinecraftCodeNode getRoot() {
		return root;
	}

	/**
	 * @return The node that open a new server.
	 */
	public OpenServerNode getOpenNode() {
		return openNode;
	}
}
