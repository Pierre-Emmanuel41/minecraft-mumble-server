package fr.pederobien.minecraftmumbleserver.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.interfaces.helpers.IGameConfigurationHelper;
import fr.pederobien.minecraftmumbleserver.EMumbleMessageCode;
import fr.pederobien.mumble.server.exceptions.ChannelNotRegisteredException;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class RemoveChannelEdition extends CommonMumbleEdition {

	public RemoveChannelEdition(IMumbleServer mumbleServer) {
		super(EMumbleLabel.REMOVE_CHANNEL, EMumbleMessageCode.REMOVE_CHANNEL__EXPLANATION, mumbleServer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<IChannel> channels = new ArrayList<IChannel>();

		if (args[0].equals(IGameConfigurationHelper.ALL)) {
			List<String> channelNames = getChannelNames(get().clearChannels());
			sendSynchro(sender, EMumbleMessageCode.REMOVE_CHANNEL__ALL_CHANNELS_REMOVED, get().getName(), concat(channelNames, ", "));
			return true;
		}

		String channelNamesConcatenated = null;
		try {
			channels = getChannels(args);
			channelNamesConcatenated = concat(getChannelNames(channels));
			for (IChannel channel : channels)
				get().removeChannel(channel.getName());
		} catch (ChannelNotRegisteredException e) {
			sendSynchro(sender, EMumbleMessageCode.REMOVE_CHANNEL__CHANNEL_DOES_NOT_EXIST, e.getName(), get().getName());
			return false;
		}

		switch (channels.size()) {
		case 0:
			sendSynchro(sender, EMumbleMessageCode.REMOVE_CHANNEL__NO_CHANNEL_REMOVED, get().getName());
			break;
		case 1:
			sendSynchro(sender, EMumbleMessageCode.REMOVE_CHANNEL__ONE_CHANNEL_REMOVED, channelNamesConcatenated, get().getName());
			break;
		default:
			sendSynchro(sender, EMumbleMessageCode.REMOVE_CHANNEL__SEVERAL_CHANNELS_REMOVED, channelNamesConcatenated, get().getName());
			break;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		Stream<String> channels = getFreeChannels(asList(args)).map(channel -> channel.getName());

		// Adding all to delete all registered teams
		if (args.length == 1)
			return filter(Stream.concat(channels, Stream.of(IGameConfigurationHelper.ALL)), args);

		// If the first argument is all -> any team is proposed
		// Else propose not already mentioned teams
		return filter(args[0].equals(IGameConfigurationHelper.ALL) ? emptyStream() : channels, args);
	}
}
