package fr.pederobien.minecraft.mumble.server;

import java.util.Optional;
import java.util.function.Supplier;

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
	private Supplier<IMumbleServer> server;

	/**
	 * Creates an event listener in order to update the list of players of a mumble server, the player's coordinates and administrator
	 * status.
	 * 
	 * @param server The server associated to this event listener.
	 */
	public MumbleEventListener(Supplier<IMumbleServer> server) {
		this.server = server;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		if (server.get() == null)
			return;

		Player player = event.getPlayer();

		// Player's X coordinate
		double x = player.getLocation().getX();

		// Player's y coordinate
		double y = -player.getLocation().getZ();

		// Player's X coordinate
		double z = player.getLocation().getY();

		// Player's X coordinate
		double yaw = MathHelper.inRange(Math.toRadians(-player.getLocation().getYaw() - 90));

		// Player's X coordinate
		double pitch = Math.toRadians(player.getLocation().getPitch());

		server.get().getPlayers().add(player.getName(), player.getAddress(), player.isOp(), x, y, z, yaw, pitch);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		if (server.get() == null)
			return;

		server.get().getPlayers().remove(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		if (server.get() == null)
			return;

		String[] command = event.getMessage().substring(1).split(" ");
		if (command.length < 2)
			return;

		if (!command[0].equals("op") || !command[0].equals("deop"))
			return;

		Optional<IPlayer> optPlayer = server.get().getPlayers().get(command[1]);
		if (optPlayer.isPresent())
			optPlayer.get().setAdmin(command[0].equals("op"));
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onPlayerMoveEvent(PlayerMoveEvent event) {
		if (server.get() == null)
			return;

		Optional<IPlayer> optPlayer = server.get().getPlayers().get(event.getPlayer().getName());
		if (!optPlayer.isPresent())
			return;

<<<<<<< HEAD
	/**
	 * Register the given player in the mumble server.
	 * 
	 * @param minecraft The minecraft player to register.
	 */
	private void registerPlayer(Player minecraft) {
		IPlayer mumble = mumbleServer.getPlayers().add(minecraft.getName(), minecraft.getAddress(), minecraft.isOp(), 0, 0, 0, 0, 0);
		updatePlayerLocation(minecraft, mumble);
		addPlayer(minecraft, mumble);
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
=======
		Location loc = event.getPlayer().getLocation();
		optPlayer.get().getPosition().update(loc.getX(), -loc.getZ(), loc.getY(), MathHelper.inRange(Math.toRadians(-loc.getYaw() - 90)), Math.toRadians(loc.getPitch()));
>>>>>>> origin/1.0_MC_1.16.5-SNAPSHOT
	}
}
