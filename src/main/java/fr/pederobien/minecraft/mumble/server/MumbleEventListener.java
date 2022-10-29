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

		Location loc = event.getPlayer().getLocation();
		optPlayer.get().getPosition().update(loc.getX(), -loc.getZ(), loc.getY(), MathHelper.inRange(Math.toRadians(-loc.getYaw() - 90)), Math.toRadians(loc.getPitch()));
	}
}
