package fr.pederobien.minecraft.mumble.server.commands;

import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class ChannelsNode extends MumbleNode {
	private AddChannelMumbleNode addNode;
	private RemoveChannelMumbleNode removeNode;
	private RenameChannelMumbleNode renameNode;
	private SoundModifierMumbleNode soundModifierMumbleNode;

	public ChannelsNode(IMumbleServer server) {
		super(server, "channels", EMumbleCode.MUMBLE__CHANNELS__EXPLANATION, s -> s != null);
		add(addNode = new AddChannelMumbleNode(server));
		add(removeNode = new RemoveChannelMumbleNode(server));
		add(renameNode = new RenameChannelMumbleNode(server));
		add(soundModifierMumbleNode = new SoundModifierMumbleNode(server));
	}

	/**
	 * @return The node that adds channel to a mumble server.
	 */
	public AddChannelMumbleNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node that removes channels from a mumble server.
	 */
	public RemoveChannelMumbleNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The node that renames a channel of a mumble server.
	 */
	public RenameChannelMumbleNode getRenameNode() {
		return renameNode;
	}

	/**
	 * @return The node that modify the sound modifier of a channel.
	 */
	public SoundModifierMumbleNode getSoundModifierNode() {
		return soundModifierMumbleNode;
	}
}
