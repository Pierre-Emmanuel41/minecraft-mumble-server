package fr.pederobien.minecraftmumbleserver.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.commands.common.ECommonLabel;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftmumbleserver.EMumbleMessageCode;
import fr.pederobien.mumble.server.exceptions.ChannelAlreadyExistException;
import fr.pederobien.mumble.server.exceptions.ChannelNotRegisteredException;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class RenameChannelEdition extends CommonMumbleEdition {

	public RenameChannelEdition(IMumbleServer mumbleServer) {
		super(ECommonLabel.RENAME, EMumbleMessageCode.RENAME_CHANNEL__EXPLANATION, mumbleServer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String oldName = null, newName = null;
		try {
			try {
				oldName = args[0];
			} catch (IndexOutOfBoundsException e) {
				sendSynchro(sender, EMumbleMessageCode.RENAME_CHANNEL__CHANNEL_NAME_IS_MISSING);
				return false;
			}
			try {
				newName = args[1];
			} catch (IndexOutOfBoundsException e) {
				sendSynchro(sender, EMumbleMessageCode.RENAME_CHANNEL__NEW_CHANNEL_NAME_IS_MISSING);
				return false;
			}

			get().renameChannel(oldName, newName);
			sendSynchro(sender, EMumbleMessageCode.RENAME_CHANNEL__CHANNEL_RENAMED, oldName, newName);
		} catch (ChannelAlreadyExistException e) {
			sendSynchro(sender, EMumbleMessageCode.RENAME_CHANNEL__CHANNEL_ALREADY_EXIST, oldName, e.getName());
			return false;
		} catch (ChannelNotRegisteredException e) {
			sendSynchro(sender, EMumbleMessageCode.RENAME_CHANNEL__CHANNEL_DOES_NOT_EXIST, e.getName());
			return false;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getChannels().map(channel -> channel.getName()), args);
		case 2:
			return check(args[0], name -> get().getChannels().get(name) != null, asList(getMessage(sender, ECommonMessageCode.COMMON_RENAME_TAB_COMPLETE)));
		default:
			return emptyList();
		}
	}
}
