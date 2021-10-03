package fr.pederobien.minecraftmumbleserver;

import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.mumble.server.impl.MathHelper;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class EventListener implements Listener {
	private IMumbleServer mumbleServer;

	public EventListener(IMumbleServer mumbleServer) {
		this.mumbleServer = mumbleServer;

		for (Player player : Bukkit.getOnlinePlayers()) {
			IPlayer mumblePlayer = mumbleServer.addPlayer(player.getAddress(), player.getName(), player.isOp());
			updatePlayerLocation(player, mumblePlayer);
			getPlayers().put(player.getName(), new MinecraftMumblePlayer(player, mumblePlayer));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player minecraftPlayer = event.getPlayer();
		IPlayer mumblePlayer = mumbleServer.addPlayer(event.getPlayer().getAddress(), event.getPlayer().getName(), event.getPlayer().isOp());
		updatePlayerLocation(minecraftPlayer, mumblePlayer);
		getPlayers().put(minecraftPlayer.getName(), new MinecraftMumblePlayer(minecraftPlayer, mumblePlayer));
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerQuiEvent(PlayerQuitEvent event) {
		getPlayers().remove(event.getPlayer().getName());
		mumbleServer.removePlayer(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		String[] command = event.getMessage().substring(1).split(" ");
		if (command.length == 0)
			return;

		if (command[0].equals("op")) {
			Optional<Map.Entry<String, MinecraftMumblePlayer>> optEntry = getPlayers().entrySet().stream().filter(entry -> entry.getKey().equals(command[1])).findFirst();
			if (optEntry.isPresent())
				optEntry.get().getValue().getMumblePlayer().setAdmin(true);
		}

		if (command[0].equals("deop")) {
			Optional<Map.Entry<String, MinecraftMumblePlayer>> optEntry = getPlayers().entrySet().stream().filter(entry -> entry.getKey().equals(command[1])).findFirst();
			if (optEntry.isPresent())
				optEntry.get().getValue().getMumblePlayer().setAdmin(false);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		MinecraftMumblePlayer player = getPlayers().get(event.getPlayer().getName());

		if (player == null)
			return;

		updatePlayerLocation(event.getPlayer(), player.getMumblePlayer());
	}

	private void updatePlayerLocation(Player player, IPlayer mumblePlayer) {
		Location loc = player.getLocation();
		mumblePlayer.getPosition().update(loc.getX(), -loc.getZ(), loc.getY(), MathHelper.inRange(Math.toRadians(-loc.getYaw() - 90)), Math.toRadians(loc.getPitch()));
	}

	private Map<String, MinecraftMumblePlayer> getPlayers() {
		return MumbleServerPlugin.getPlayers();
	}
}
