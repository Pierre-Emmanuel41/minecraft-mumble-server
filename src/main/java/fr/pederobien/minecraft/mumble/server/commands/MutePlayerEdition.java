package fr.pederobien.minecraft.mumble.server.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleMessageCode;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.exceptions.PlayerNotFoundException;
import fr.pederobien.minecraftgameplateform.interfaces.helpers.IGameConfigurationHelper;
import fr.pederobien.mumble.server.exceptions.PlayerNotRegisteredInChannelException;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class MutePlayerEdition extends CommonMumbleEdition {

	public MutePlayerEdition(IMumbleServer mumbleServer) {
		super(EMumbleLabel.MUTE, EMumbleMessageCode.MUTE__EXPLANATION, mumbleServer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<IPlayer> players = new ArrayList<IPlayer>();

		String playerNamesConcatenated = null;
		try {
			if (args[0].equals(IGameConfigurationHelper.ALL)) {
				for (IChannel channel : get().getChannels().values())
					for (IPlayer player : channel.getPlayers())
						player.setMute(true);
				sendSynchro(sender, EMumbleMessageCode.MUTE__ALL_PLAYERS_MUTE);
				return true;
			}

			players = getPlayers(args);
			playerNamesConcatenated = concat(getPlayerNames(players));
			for (IPlayer player : players)
				player.setMute(true);
		} catch (PlayerNotFoundException e) {
			sendSynchro(sender, ECommonMessageCode.COMMON_PLAYER_DOES_NOT_EXIST, e.getPlayerName(), get().getName());
			return false;
		} catch (PlayerNotRegisteredInChannelException e) {
			sendSynchro(sender, EMumbleMessageCode.MUTE__PLAYER_NOT_REGISTERED_IN_A_CHANNEL, e.getNotRegisteredPlayer().getName());
			return false;
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EMumbleMessageCode.MUTE__NO_PLAYER_MUTE);
			return true;
		}

		switch (players.size()) {
		case 0:
			sendSynchro(sender, EMumbleMessageCode.MUTE__NO_PLAYER_MUTE);
			break;
		case 1:
			sendSynchro(sender, EMumbleMessageCode.MUTE__ONE_PLAYER_MUTE, playerNamesConcatenated);
			break;
		default:
			sendSynchro(sender, EMumbleMessageCode.MUTE__SEVERAL_PLAYERS_MUTE, playerNamesConcatenated);
			break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		Stream<String> players = getFreePlayers(asList(args)).filter(player -> !player.isMute()).map(player -> player.getName());
		long size = getFreePlayers(asList(args)).filter(player -> !player.isMute()).count();
		if (size == 0)
			return emptyList();

		switch (args.length) {
		case 1:
			// Adding all to mute all registered players
			return filter(Stream.concat(players, Stream.of(IGameConfigurationHelper.ALL)), args);
		default:
			// If the first argument is all -> any player is proposed
			// Else propose not already mentioned players
			return filter(args[0].equals(IGameConfigurationHelper.ALL) ? emptyStream() : players, args);
		}
	}
}
