package fr.pederobien.minecraftmumbleserver;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.mumble.server.impl.MumbleServer;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleServerPlugin extends JavaPlugin {
	private IMumbleServer mumbleServer;

	@Override
	public void onEnable() {
		try {
			mumbleServer = new MumbleServer("Mumble-1.0-SNAPSHOT", InetAddress.getByName(Bukkit.getIp()), 28000, 32000, Plateform.ROOT.resolve("Mumble"));
			mumbleServer.open();
			getServer().getPluginManager().registerEvents(new EventListener(mumbleServer), this);
			new MumbleCommand(this, mumbleServer);
			registerDictionaries();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		mumbleServer.close();
	}

	private void registerDictionaries() {
		String[] dictionaries = new String[] { "Mumble.xml" };
		// Registering French dictionaries
		registerDictionary("French", dictionaries);

		// Registering English dictionaries
		registerDictionary("English", dictionaries);
	}

	private void registerDictionary(String parent, String... dictionaryNames) {
		Path jarPath = Plateform.ROOT.getParent().resolve(getName().concat(".jar"));
		String dictionariesFolder = "resources/dictionaries/".concat(parent).concat("/");
		for (String name : dictionaryNames)
			registerDictionary(Plateform.getDefaultDictionaryParser(dictionariesFolder.concat(name)), jarPath);
	}

	private void registerDictionary(IDictionaryParser parser, Path jarPath) {
		try {
			Plateform.getNotificationCenter().getDictionaryContext().register(parser, jarPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
