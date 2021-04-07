package fr.pederobien.minecraftmumbleserver.commands;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftmumbleserver.EMumbleMessageCode;
import fr.pederobien.mumble.server.exceptions.ChannelNotRegisteredException;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.ISoundModifier;

public class SoundModifierEdition extends CommonMumbleEdition {

	public SoundModifierEdition(IMumbleServer mumbleServer) {
		super(EMumbleLabel.SOUND_MODIFIER, EMumbleMessageCode.SOUND_MODIFIER__EXPLANATION, mumbleServer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		IChannel channel = null;
		String channelName = null;
		try {
			channelName = args[0];
			channel = getChannel(channelName);
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(EMumbleMessageCode.SOUND_MODIFIER__CHANNEL_NAME_IS_MISSING);
			return false;
		} catch (ChannelNotRegisteredException e) {
			sendSynchro(sender, EMumbleMessageCode.SOUND_MODIFIER__CHANNEL_DOES_NOT_EXIST, channelName);
			return false;
		}

		ISoundModifier soundModifier = null;
		String modifierName = null;
		try {
			modifierName = args[1];
			Optional<ISoundModifier> optModifier = get().getSoundManager().getByName(modifierName);
			if (!optModifier.isPresent()) {
				sendSynchro(sender, EMumbleMessageCode.SOUND_MODIFIER__MODIFIER_DOES_NOT_EXIST, channelName, modifierName);
				return false;
			}
			soundModifier = optModifier.get();
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EMumbleMessageCode.SOUND_MODIFIER__MODIFIER_NAME_IS_MISSING);
			return false;
		}

		channel.setSoundModifier(soundModifier);
		sendSynchro(sender, EMumbleMessageCode.SOUND_MODIFIER__MODIFIER_UPDATED, channelName, modifierName);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getChannels().map(channel -> channel.getName()), args);
		case 2:
			Collection<ISoundModifier> sounds = get().getSoundManager().getSoundModifiers().values();
			return filter(check(args[0], name -> get().getChannels().get(name) != null, sounds.stream().map(sound -> sound.getName())), args);
		default:
			return emptyList();
		}
	}
}
