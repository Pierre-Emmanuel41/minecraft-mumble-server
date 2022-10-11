package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.exceptions.ChannelAlreadyRegisteredException;
import fr.pederobien.mumble.server.exceptions.SoundModifierDoesNotExistException;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class ChannelAddChannelNode extends MumbleServerNode {

	/**
	 * Creates a node in order to add a channel to a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelAddChannelNode(Supplier<IMumbleServer> server) {
		super(server, "channel", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__NAME__COMPLETION));

		case 2:
			Predicate<String> nameValid = name -> !getServer().getChannels().get(name).isPresent();
			return check(args[0], nameValid, filter(SoundManager.toStream().map(soundModifier -> soundModifier.getName()), args));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__NAME_IS_MISSING, getServer().getName()));
			return false;
		}

		String soundModifier;
		try {
			soundModifier = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_IS_MISSING, getServer().getName()));
			return false;
		}

		try {
			getServer().getChannels().add(name, soundModifier);
			sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__CHANNEL_ADDED, name, getServer().getName());
			return true;
		} catch (ChannelAlreadyRegisteredException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__CHANNEL_ALREADY_REGISTERED, name, getServer().getName()));
			return false;
		} catch (SoundModifierDoesNotExistException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_NOT_FOUND, name, getServer().getName(),
					soundModifier));
			return false;
		}
	}
}
