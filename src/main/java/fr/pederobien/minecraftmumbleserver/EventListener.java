package fr.pederobien.minecraftmumbleserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class EventListener implements Listener {
	private IMumbleServer mumbleServer;
	private Map<Player, IPlayer> players;

	public EventListener(IMumbleServer mumbleServer) {
		this.mumbleServer = mumbleServer;
		players = new HashMap<Player, IPlayer>();

		for (Player player : Bukkit.getOnlinePlayers()) {
			IPlayer mumblePlayer = mumbleServer.addPlayer(player.getAddress(), player.getName(), player.isOp());
			updatePlayerLocation(player, mumblePlayer);
			players.put(player, mumblePlayer);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		players.put(event.getPlayer(), mumbleServer.addPlayer(event.getPlayer().getAddress(), event.getPlayer().getName(), event.getPlayer().isOp()));
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerQuiEvent(PlayerQuitEvent event) {
		players.remove(event.getPlayer());
		mumbleServer.removePlayer(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		String[] command = event.getMessage().substring(1).split(" ");
		if (command.length == 0)
			return;

		if (command[0].equals("op")) {
			Optional<Map.Entry<Player, IPlayer>> optEntry = players.entrySet().stream().filter(entry -> entry.getKey().getName().equals(command[1])).findFirst();
			if (optEntry.isPresent())
				optEntry.get().getValue().setAdmin(true);
		}

		if (command[0].equals("deop")) {
			Optional<Map.Entry<Player, IPlayer>> optEntry = players.entrySet().stream().filter(entry -> entry.getKey().getName().equals(command[1])).findFirst();
			if (optEntry.isPresent())
				optEntry.get().getValue().setAdmin(false);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		IPlayer player = players.get(event.getPlayer());

		if (player == null)
			return;

		updatePlayerLocation(event.getPlayer(), player);
	}

	private void updatePlayerLocation(Player player, IPlayer mumblePlayer) {
		mumblePlayer.getPosition().setX(player.getLocation().getX());
		mumblePlayer.getPosition().setY(player.getLocation().getY());
		mumblePlayer.getPosition().setZ(player.getLocation().getZ());
		mumblePlayer.getPosition().setYaw(player.getLocation().getYaw());
		mumblePlayer.getPosition().setPitch(player.getLocation().getPitch());
	}
}
