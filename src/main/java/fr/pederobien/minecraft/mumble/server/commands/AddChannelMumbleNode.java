package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.mumble.server.exceptions.ChannelAlreadyRegisteredException;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class AddChannelMumbleNode extends MumbleNode {

	/**
	 * Creates a node that adds a channel to the given server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected AddChannelMumbleNode(IMumbleServer server) {
		super(server, "add", EMumbleCode.MUMBLE__CHANNELS_ADD__EXPLANATION, s -> s != null);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_ADD__NAME_IS_MISSING, getServer().getName()));
			return false;
		} catch (ChannelAlreadyRegisteredException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_ADD__CHANNEL_ALREADY_REGISTERED, e.getChannel().getName(), getServer().getName()));
			return false;
		}

		getServer().getChannels().add(name, SoundManager.DEFAULT_SOUND_MODIFIER_NAME);
		send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_ADD__CHANNEL_ADDED, name, getServer().getName()));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.NAME__COMPLETION));
		default:
			return emptyList();
		}
	}
}
