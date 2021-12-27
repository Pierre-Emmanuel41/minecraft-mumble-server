package fr.pederobien.minecraft.mumble.server.commands;

import java.util.function.Function;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleNode extends MinecraftCodeNode {
	private IMumbleServer server;

	/**
	 * Create a mumble node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param server      The server associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected MumbleNode(IMumbleServer server, String label, IMinecraftCode explanation, Function<IMumbleServer, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(server));
		this.server = server;
	}

	/**
	 * Create a minecraft node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param server      The server associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected MumbleNode(IMumbleServer server, String label, IMinecraftCode explanation) {
		super(label, explanation);
		this.server = server;
	}

	/**
	 * @return The server associated to this node.
	 */
	public IMumbleServer getServer() {
		return server;
	}
}
