package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.ISoundModifier;

public class SoundModifierMumbleNode extends MumbleNode {

	/**
	 * Creates a node that modify the sound modifier of a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected SoundModifierMumbleNode(IMumbleServer server) {
		super(server, "soundModifier", EMumbleCode.MUMBLE__CHANNELS_SOUND_MODIFIER__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		case 2:
			return filter(check(args[0], name -> getServer().getChannels().get(name) != null, SoundManager.toStream().map(sound -> sound.getName())), args);
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
				send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_SOUND_MODIFIER__CHANNEL_DOES_NOT_EXIST, args[0]));
				return false;
			}
			channel = optChannel.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(EMumbleCode.MUMBLE__CHANNELS_SOUND_MODIFIER__CHANNEL_NAME_IS_MISSING).build());
			return false;
		}

		ISoundModifier soundModifier;
		try {
			Optional<ISoundModifier> optModifier = SoundManager.getByName(args[1]);
			if (!optModifier.isPresent()) {
				send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_SOUND_MODIFIER__MODIFIER_DOES_NOT_EXIST, channel.getName(), args[1]));
				return false;
			}
			soundModifier = optModifier.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_SOUND_MODIFIER__MODIFIER_NAME_IS_MISSING).build());
			return false;
		}

		channel.setSoundModifier(soundModifier);
		send(eventBuilder(sender, EMumbleCode.MUMBLE__CHANNELS_SOUND_MODIFIER__MODIFIER_UPDATED, channel.getName(), soundModifier.getName()));
		return true;
	}
}
