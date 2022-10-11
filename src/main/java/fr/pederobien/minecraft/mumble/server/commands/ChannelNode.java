package fr.pederobien.minecraft.mumble.server.commands;

import java.util.function.Supplier;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class ChannelNode extends MumbleServerNode {
	private ChannelAddNode addNode;
	private ChannelRemoveNode removeNode;
	private ChannelRenameNode renameNode;
	private ChannelSoundModifierNode soundModifierNode;

	/**
	 * Creates a node that adds or removes channel from a mumble server or adds/removes players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelNode(Supplier<IMumbleServer> server) {
		super(server, "channel", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__EXPLANATION, s -> s != null);

		add(addNode = new ChannelAddNode(server));
		add(removeNode = new ChannelRemoveNode(server));
		add(renameNode = new ChannelRenameNode(server));
		add(soundModifierNode = new ChannelSoundModifierNode(server));
	}

	/**
	 * @return The node that adds a channel to a mumble server or players to a channel.
	 */
	public ChannelAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node that removes channels from a mumble server or players from a channel.
	 */
	public ChannelRemoveNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The node that renames a channel.
	 */
	public ChannelRenameNode getRenameNode() {
		return renameNode;
	}

	/**
	 * @return The node that modifies the sound modifier of a channel or the parameters of a sound modifier.
	 */
	public ChannelSoundModifierNode getSoundModifierNode() {
		return soundModifierNode;
	}
}
