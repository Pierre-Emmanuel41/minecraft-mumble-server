package fr.pederobien.minecraft.mumble.server;

import org.bukkit.entity.Player;

import fr.pederobien.mumble.server.interfaces.IPlayer;

public class MinecraftMumblePlayer {
	private Player minecraftPlayer;
	private IPlayer mumblePlayer;

	public MinecraftMumblePlayer(Player minecraftPlayer, IPlayer mumblePlayer) {
		this.minecraftPlayer = minecraftPlayer;
		this.mumblePlayer = mumblePlayer;
	}

	/**
	 * @return The minecraft player instance.
	 */
	public Player getMinecraftPlayer() {
		return minecraftPlayer;
	}

	/**
	 * @return The mumble minecraft player instance.
	 */
	public IPlayer getMumblePlayer() {
		return mumblePlayer;
	}
}
