package fr.pederobien.minecraftmumbleserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.mumble.server.impl.MumbleServer;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleServerPlugin extends JavaPlugin {
	private IMumbleServer server;

	@Override
	public void onEnable() {
		try {
			server = new MumbleServer("Mumble-1.0-SNAPSHOT", InetAddress.getByName(Bukkit.getIp()), 28000, 32000);
			server.open();
			getServer().getPluginManager().registerEvents(new EventListener(server), this);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		server.close();
	}
}
