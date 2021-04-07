package fr.pederobien.minecraftmumbleserver.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;
import fr.pederobien.minecraftgameplateform.impl.editions.AbstractSimpleMapEdition;
import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;
import fr.pederobien.mumble.server.exceptions.ChannelNotRegisteredException;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class CommonMumbleEdition extends AbstractSimpleMapEdition {
	private IMumbleServer mumbleServer;

	public CommonMumbleEdition(ILabel label, IMinecraftMessageCode explanation, IMumbleServer mumbleServer) {
		super(label.getLabel(), explanation);
		this.mumbleServer = mumbleServer;
	}

	/**
	 * @return The mumble server to modify its properties.
	 */
	protected IMumbleServer get() {
		return mumbleServer;
	}

	/**
	 * @return A stream that contains all registered channels.
	 */
	protected Stream<IChannel> getChannels() {
		return get().getChannels().values().stream();
	}

	/**
	 * Remove channels already mentioned from the list returned by {@link IMumbleServer#getChannels()}.
	 * 
	 * @param alreadyMentionedChannels A list that contains already mentioned channels.
	 * @return A stream that contains not mentioned channels.
	 */
	protected Stream<IChannel> getFreeChannels(List<String> alreadyMentionedChannels) {
		return getChannels().filter(channel -> !alreadyMentionedChannels.contains(channel.getName()));
	}

	/**
	 * Get a list of channels associated to each channel name in array <code>channelNames</code>
	 * 
	 * @param channelNames The array that contains channel names.
	 * @return The list of channels.
	 * 
	 * @see #getChannel(String)
	 */
	protected List<IChannel> getChannels(String[] channelNames) {
		List<IChannel> channels = new ArrayList<IChannel>();
		for (String channelName : channelNames)
			channels.add(getChannel(channelName));
		return channels;
	}

	/**
	 * Find the channel associated to the given name for this mumble server.
	 * 
	 * @param channelName The channel's name.
	 * @return The associated channel if it exists.
	 * 
	 * @throws ChannelNotRegisteredException If the name does not correspond to a channel.
	 */
	protected IChannel getChannel(String channelName) {
		IChannel channel = get().getChannels().get(channelName);
		if (channel == null)
			throw new ChannelNotRegisteredException(channelName);
		return channel;
	}

	/**
	 * Get a list of string that correspond to the name of each channel in the given list <code>channels</code>
	 * 
	 * @param channels The list of channel used to get their name.
	 * 
	 * @return The list of channel's name.
	 */
	protected List<String> getChannelNames(List<IChannel> channels) {
		return channels.stream().map(channel -> channel.getName()).collect(Collectors.toList());
	}
}
