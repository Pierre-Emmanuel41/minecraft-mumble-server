package fr.pederobien.minecraft.mumble.server.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.managers.PlayerManager;
import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class DeafenPlayerMumbleNode extends MumbleNode {

	/**
	 * Creates a node that deafen players when registered in a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected DeafenPlayerMumbleNode(IMumbleServer server) {
		super(server, "deafen", EMumbleCode.MUMBLE__DEAFEN__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> alreadyMentionned = asList(args);
		Stream<IPlayer> players = getServer().getPlayers().stream().filter(player -> player.getChannel() != null);
		switch (args.length) {
		case 1:
			// Adding all to deafen all players that are in a channel
			return filter(concat(players.map(player -> player.getName()), Stream.of("all")), args);

		// Verification that all player name correspond to an existing player and is registered in a channel.
		default:
			// If the first argument is all -> no player is proposed
			if (args[0].equals("all"))
				return emptyList();

			Stream<String> free = players.map(player -> player.getName()).filter(name -> !alreadyMentionned.contains(name));
			return check(args[args.length - 1], name -> PlayerManager.getPlayer(name) != null, free).collect(Collectors.toList());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args[0].equals("all")) {
			for (IPlayer player : getServer().getPlayers())
				player.setDeafen(true);
			send(eventBuilder(sender, EMumbleCode.MUMBLE__DEAFEN__ALL_PLAYERS_DEAFENED).build());
			return true;
		}

		List<IPlayer> players = new ArrayList<IPlayer>();
		for (String player : args) {
			Optional<IPlayer> optPlayer = getServer().getPlayers().getPlayer(player);
			if (!optPlayer.isPresent()) {
				send(eventBuilder(sender, EMumbleCode.MUMBLE__DEAFEN__PLAYER_NOT_FOUND, optPlayer.get().getName()));
				return false;
			}

			if (optPlayer.get().getChannel() == null) {
				send(eventBuilder(sender, EMumbleCode.MUMBLE__DEAFEN__PLAYER_NOT_REGISTERED_IN_CHANNEL, optPlayer.get().getName()));
				return false;
			}

			players.add(optPlayer.get());
		}

		String playerNames = concat(args);

		for (IPlayer player : players)
			player.setDeafen(true);

		switch (players.size()) {
		case 0:
			send(eventBuilder(sender, EMumbleCode.MUMBLE__DEAFEN__NO_PLAYER_DEAFENED, getServer().getName()));
			break;
		case 1:
			send(eventBuilder(sender, EMumbleCode.MUMBLE__DEAFEN__ONE_PLAYER_DEAFENED, playerNames, getServer().getName()));
			break;
		default:
			send(eventBuilder(sender, EMumbleCode.MUMBLE__DEAFEN__SEVERAL_PLAYERS_DEAFENED, playerNames, getServer().getName()));
			break;
		}

		return true;
	}
}
