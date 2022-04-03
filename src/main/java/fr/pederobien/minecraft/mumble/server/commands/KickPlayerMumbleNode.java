package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.mumble.server.exceptions.PlayerNotAdministratorException;
import fr.pederobien.mumble.server.exceptions.PlayerNotRegisteredInChannelException;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class KickPlayerMumbleNode extends MumbleNode {

	/**
	 * Creates a node that kicks players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected KickPlayerMumbleNode(IMumbleServer server) {
		super(server, "kick", EMumbleCode.MUMBLE__KICK__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().filter(player -> player.getChannel() != null).map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().get(name).isPresent() && getServer().getPlayers().get(name).get().getChannel() != null;
			Stream<String> filtered = getServer().getPlayers().stream().filter(player -> player.isAdmin()).map(player -> player.getName());
			return filter(check(args[0], nameValid, filtered), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		IPlayer kicked;
		try {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(args[0]);
			if (!optPlayer.isPresent()) {
				send(eventBuilder(sender, EMumbleCode.MUMBLE__KICK__KICKED_PLAYER_NOT_FOUND, args[0]));
				return false;
			}

			kicked = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__KICK__KICKED_PLAYER_NAME_IS_MISSING).build());
			return false;
		}

		IPlayer kicking;
		try {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(args[1]);
			if (!optPlayer.isPresent()) {
				send(eventBuilder(sender, EMumbleCode.MUMBLE__KICK__KICKED_PLAYER_NOT_FOUND, kicked.getName(), args[1]));
				return false;
			}

			kicking = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__KICK__KICKING_PLAYER_NAME_IS_MISSING, kicked.getName()));
			return false;
		}

		IChannel channel = kicked.getChannel();
		try {
			kicking.kick(kicked);
			send(eventBuilder(sender, EMumbleCode.MUMBLE__KICK__PLAYER_KICKED, kicked.getName(), channel.getName(), kicking.getName()));
		} catch (PlayerNotAdministratorException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__KICK__KICKING_PLAYER_NOT_ADMIN, kicked.getName(), kicking.getName()));
			return false;
		} catch (PlayerNotRegisteredInChannelException e) {
			send(eventBuilder(sender, EMumbleCode.MUMBLE__KICK__KICKED_PLAYER_NOT_REGISTERED_IN_CHANNEL, kicked.getName(), kicking.getName()));
			return false;
		}
		return true;
	}
}
