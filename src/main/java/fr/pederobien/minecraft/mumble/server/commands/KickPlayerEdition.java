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
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class KickPlayerEdition extends CommonMumbleEdition {

	public KickPlayerEdition(IMumbleServer mumbleServer) {
		super(EMumbleLabel.KICK, EMumbleMessageCode.KICK__EXPLANATION, mumbleServer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<IPlayer> players = new ArrayList<IPlayer>();

		String playerNamesConcatenated = null;
		try {
			if (args[0].equals(IGameConfigurationHelper.ALL)) {
				for (IChannel channel : get().getChannels().values())
					channel.clear();
				sendSynchro(sender, EMumbleMessageCode.KICK__ALL_PLAYERS_KICKED);
				return true;
			}

			players = getPlayers(args);
			for (IPlayer player : players)
				if (player.getChannel() == null) {
					sendSynchro(sender, EMumbleMessageCode.KICK__PLAYER_NOT_REGISTERED_IN_CHANNEL, player.getName());
					return false;
				}

			playerNamesConcatenated = concat(getPlayerNames(players));
			for (int i = 0; i < players.size(); i++) {
				IPlayer player = players.get(i);
				player.getChannel().removePlayer(player);
			}
		} catch (PlayerNotFoundException e) {
			sendSynchro(sender, ECommonMessageCode.COMMON_PLAYER_DOES_NOT_EXIST, e.getPlayerName(), get().getName());
			return false;
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EMumbleMessageCode.KICK__NO_PLAYER_KICKED);
			return true;
		}

		switch (players.size()) {
		case 0:
			sendSynchro(sender, EMumbleMessageCode.KICK__NO_PLAYER_KICKED);
			break;
		case 1:
			sendSynchro(sender, EMumbleMessageCode.KICK__ONE_PLAYER_KICKED, playerNamesConcatenated);
			break;
		default:
			sendSynchro(sender, EMumbleMessageCode.KICK__SEVERAL_PLAYERS_KICKED, playerNamesConcatenated);
			break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		Stream<String> players = getFreePlayers(asList(args)).filter(player -> player.getChannel() != null).map(player -> player.getName());
		long size = getFreePlayers(asList(args)).filter(player -> player.getChannel() != null).count();
		if (size == 0)
			return emptyList();

		switch (args.length) {
		case 1:
			// Adding all to kick all registered players
			return filter(Stream.concat(players, Stream.of(IGameConfigurationHelper.ALL)), args);

		// Verification that all player name correspond to an existing player and is registered in a channel.
		default:
			// If the first argument is all -> no player is proposed
			// If a player is misspelled -> no player is proposed
			// Else propose not already mentioned players that are registered in a channel.
			if (args[0].equals(IGameConfigurationHelper.ALL) || !checkPlayer(args[args.length - 1]))
				return emptyList();

			return filter(getFreePlayers(asList(args)).filter(p -> p.getChannel() != null).map(p -> p.getName()), args);
		}
	}

	private boolean checkPlayer(String playerName) {
		try {
			getPlayer(playerName);
			return true;
		} catch (PlayerNotFoundException e) {
			return false;
		}
	}
}
