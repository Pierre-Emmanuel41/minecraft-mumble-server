package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.mumble.server.exceptions.ChannelAlreadyRegisteredException;
import fr.pederobien.mumble.server.exceptions.ChannelNotRegisteredException;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class RenameChannelMumbleNode extends MumbleNode {

	/**
	 * Creates a node that rename a channel of the given server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected RenameChannelMumbleNode(IMumbleServer server) {
		super(server, "rename", EMumbleCode.MUMBLE__CHANNELS_RENAME__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		case 2:
			return check(args[0], name -> getServer().getChannels().getChannel(name) != null, asList(getMessage(sender, EGameCode.NAME__COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String oldName;
		try {
			oldName = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_RENAME__CHANNEL_NAME_IS_MISSING).build());
			return false;
		}

		String newName;
		try {
			newName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_RENAME__NEW_CHANNEL_NAME_IS_MISSING).build(oldName));
			return false;
		}

		try {
			getServer().getChannels().renameChannel(oldName, newName);
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_RENAME__CHANNEL_RENAMED, oldName, newName));
		} catch (ChannelNotRegisteredException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_RENAME__CHANNEL_DOES_NOT_EXIST, e.getName()));
			return false;
		} catch (ChannelAlreadyRegisteredException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_RENAME__NAME_ALREADY_USED, oldName, e.getChannel().getName()));
			return false;
		}
		return true;
	}
}
