package fr.pederobien.minecraft.mumble.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

public class MumbleEventListener implements Listener {
	private IMumbleServer mumbleServer;
	private Map<String, MinecraftMumblePlayer> players;
	private Lock lock;

	public MumbleEventListener(IMumbleServer mumbleServer) {
		this.mumbleServer = mumbleServer;

		lock = new ReentrantLock(true);
		players = new HashMap<String, MinecraftMumblePlayer>();

		for (Player minecraft : Bukkit.getOnlinePlayers())
			registerPlayer(minecraft);
	}

	/**
	 * @return A copy of the underlying map that contains the minecraft mumble players.
	 */
	public Map<String, MinecraftMumblePlayer> getPlayers() {
		return new HashMap<String, MinecraftMumblePlayer>(players);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		registerPlayer(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		removePlayer(event.getPlayer().getName());
		mumbleServer.getPlayers().remove(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		String[] command = event.getMessage().substring(1).split(" ");
		if (command.length == 0)
			return;

		if (command[0].equals("op")) {
			Optional<Map.Entry<String, MinecraftMumblePlayer>> optEntry = players.entrySet().stream().filter(entry -> entry.getKey().equals(command[1])).findFirst();
			if (optEntry.isPresent())
				optEntry.get().getValue().getMumblePlayer().setAdmin(true);
		}

		if (command[0].equals("deop")) {
			Optional<Map.Entry<String, MinecraftMumblePlayer>> optEntry = players.entrySet().stream().filter(entry -> entry.getKey().equals(command[1])).findFirst();
			if (optEntry.isPresent())
				optEntry.get().getValue().getMumblePlayer().setAdmin(false);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onPlayerMoveEvent(PlayerMoveEvent event) {
		MinecraftMumblePlayer player = players.get(event.getPlayer().getName());

		if (player == null)
			return;

		updatePlayerLocation(event.getPlayer(), player.getMumblePlayer());
	}

	private void updatePlayerLocation(Player player, IPlayer mumblePlayer) {
		Location loc = player.getLocation();
		mumblePlayer.getPosition().update(loc.getX(), -loc.getZ(), loc.getY(), MathHelper.inRange(Math.toRadians(-loc.getYaw() - 90)), Math.toRadians(loc.getPitch()));
	}

	/**
	 * Thread safe operation that consists in adding a minecraft mumble player to the list of players.
	 * 
	 * @param minecraft The minecraft player.
	 * @param mumble    The mumble player.
	 */
	private void addPlayer(Player minecraft, IPlayer mumble) {
		lock.lock();
		try {
			players.put(minecraft.getName(), new MinecraftMumblePlayer(minecraft, mumble));
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that consists in removing a minecraft mumble player from the list of players.
	 * 
	 * @param minecraft The minecraft player.
	 * @param mumble    The mumble player.
	 */
	private MinecraftMumblePlayer removePlayer(String name) {
		lock.lock();
		try {
			return players.remove(name);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Register the given player in the mumble server.
	 * 
	 * @param minecraft The minecraft player to register.
	 */
	private void registerPlayer(Player minecraft) {
		IPlayer mumble = mumbleServer.getPlayers().add(minecraft.getAddress(), minecraft.getName(), minecraft.isOp());
		updatePlayerLocation(minecraft, mumble);
		addPlayer(minecraft, mumble);
	}
}
