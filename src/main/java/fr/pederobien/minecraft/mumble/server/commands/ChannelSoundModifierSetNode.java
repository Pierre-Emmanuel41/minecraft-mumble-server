package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.ISoundModifier;

public class ChannelSoundModifierSetNode extends MumbleServerNode {

	/**
	 * Creates a node in order to set the sound modifier of a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelSoundModifierSetNode(Supplier<IMumbleServer> server) {
		super(server, "set", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		case 2:
			Optional<IChannel> optChannel = getServer().getChannels().get(args[0]);
			Predicate<String> nameValid = name -> optChannel.isPresent();
			Stream<String> stream = SoundManager.toStream().filter(modifier -> !modifier.equals(optChannel.get().getSoundModifier())).map(modifier -> modifier.getName());
			return check(args[0], nameValid, filter(stream, args));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		IChannel channel;
		try {
			Optional<IChannel> optChannel = getServer().getChannels().get(args[0]);
			if (!optChannel.isPresent()) {
				send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__CHANNEL_NOT_FOUND, args[0]));
				return false;
			}

			channel = optChannel.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__CHANNEL_NAME_IS_MISSING).build());
			return false;
		}

		ISoundModifier soundModifier;
		try {
			Optional<ISoundModifier> optSoundModifier = SoundManager.getByName(args[1]);
			if (!optSoundModifier.isPresent()) {
				send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__SOUND_MODIFIER_NOT_FOUND, args[1],
						channel.getName()));
				return false;
			}

			soundModifier = optSoundModifier.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__SOUND_MODIFIER_NAME_IS_MISSING, channel.getName()));
			return false;
		}

		channel.setSoundModifier(soundModifier);
		sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__SOUND_MODIFIER_SET, channel.getName(),
				soundModifier.getName());
		return true;
	}
}
