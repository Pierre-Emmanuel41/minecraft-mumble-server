package fr.pederobien.minecraft.mumble.server.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class RemoveChannelMumbleNode extends MumbleNode {

	/**
	 * Creates a node that removes channels from the given server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected RemoveChannelMumbleNode(IMumbleServer server) {
		super(server, "remove", EMumbleCode.MUMBLE__CHANNELS_REMOVE__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> channelNames = asList(args);
		Stream<String> channels = getServer().getChannels().stream().map(channel -> channel.getName()).filter(name -> !channelNames.contains(name));

		// Adding all to delete all channels.
		if (args.length == 1)
			return filter(concat(channels, Stream.of("all")), args);

		// If the first argument is all -> no channel is proposed
		// Else propose not already mentioned channels
		return filter(args[0].equals("all") ? emptyStream() : channels, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args[0].equals("all")) {
			getServer().getChannels().clear();
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_REMOVE__ALL_CHANNELS_REMOVED, getServer().getName()));
			return true;
		}

		List<IChannel> channels = new ArrayList<IChannel>();
		for (String channel : args) {
			Optional<IChannel> optChannel = getServer().getChannels().getChannel(channel);
			if (!optChannel.isPresent()) {
				return false;
			}

			channels.add(optChannel.get());
		}

		String channelNames = concat(args);

		for (IChannel channel : channels)
			getServer().getChannels().remove(channel.getName());

		switch (channels.size()) {
		case 0:
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_REMOVE__NO_CHANNEL_REMOVED, getServer().getName()));
			break;
		case 1:
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_REMOVE__ONE_CHANNEL_REMOVED, channelNames, getServer().getName()));
			break;
		default:
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_REMOVE__SEVERAL_CHANNELS_REMOVED, channelNames, getServer().getName()));
			break;
		}
		return true;
	}
}
