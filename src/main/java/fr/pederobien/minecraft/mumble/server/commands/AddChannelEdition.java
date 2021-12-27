package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleMessageCode;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.mumble.server.exceptions.ChannelAlreadyExistException;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class AddChannelEdition extends CommonMumbleEdition {

	public AddChannelEdition(IMumbleServer mumbleServer) {
		super(EMumbleLabel.ADD_CHANNEL, EMumbleMessageCode.ADD_CHANNEL__EXPLANATION, mumbleServer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String channelName = null;
		try {
			channelName = args[0];
			get().addChannel(channelName, SoundManager.DEFAULT_SOUND_MODIFIER_NAME);
			sendSynchro(sender, EMumbleMessageCode.ADD_CHANNEL__CHANNEL_ADDED, channelName);
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EMumbleMessageCode.ADD_CHANNEL__NAME_IS_MISSING);
			return false;
		} catch (ChannelAlreadyExistException e) {
			sendSynchro(sender, EMumbleMessageCode.ADD_CHANNEL__CHANNEL_ALREADY_REGISTERED, channelName);
			return false;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, ECommonMessageCode.COMMON_NEW_TAB_COMPLETE));
		default:
			return emptyList();
		}
	}
}
