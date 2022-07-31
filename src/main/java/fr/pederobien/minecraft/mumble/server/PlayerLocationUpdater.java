package fr.pederobien.minecraft.mumble.server;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.managers.BukkitManager;
import fr.pederobien.mumble.server.impl.MathHelper;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class PlayerLocationUpdater implements Runnable {
	private IMumbleServer mumbleServer;
	private AtomicBoolean isDisposed;
	private int taskId;

	public PlayerLocationUpdater(Plugin plugin, IMumbleServer mumbleServer) {
		this.mumbleServer = mumbleServer;
		isDisposed = new AtomicBoolean(false);

		taskId = BukkitManager.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, 1);
	}

	@Override
	public void run() {
		checkIsDisposed();

		BukkitManager.getOnlinePlayers().parallelStream().forEach(player -> {
			Optional<IPlayer> optPlayer = mumbleServer.getPlayers().get(player.getName());
			if (!optPlayer.isPresent())
				return;

			updatePlayerLocation((Player) player, optPlayer.get());
		});
	}

	public void dispose() {
		if (!isDisposed.compareAndSet(false, true))
			return;

		BukkitManager.getScheduler().cancelTask(taskId);
	}

	private void checkIsDisposed() {
		if (isDisposed.get())
			throw new IllegalStateException("This object is disposed");
	}

	/**
	 * Update the location of the mumble player using the current location of the minecraft player.
	 * 
	 * @param player       The minecraft player.
	 * @param mumblePlayer The mumble player.
	 */
	private void updatePlayerLocation(Player player, IPlayer mumblePlayer) {
		Location loc = player.getLocation();
		mumblePlayer.getPosition().update(loc.getX(), -loc.getZ(), loc.getY(), MathHelper.inRange(Math.toRadians(-loc.getYaw() - 90)), Math.toRadians(loc.getPitch()));
	}
}
