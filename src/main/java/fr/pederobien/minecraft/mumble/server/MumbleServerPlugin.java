package fr.pederobien.minecraft.mumble.server;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.communication.event.DataEvent;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.minecraft.mumble.server.soundmodifiers.TestModifier;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.mumble.server.event.PlayerPositionChangeEvent;
import fr.pederobien.mumble.server.event.PlayerSpeakPostEvent;
import fr.pederobien.mumble.server.event.PlayerSpeakPreEvent;
import fr.pederobien.mumble.server.impl.MumbleServer;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.utils.event.EventLogger;

public class MumbleServerPlugin extends JavaPlugin {
	private static Map<String, MinecraftMumblePlayer> players;
	private IMumbleServer mumbleServer;
	private static Object mutex;

	protected static Map<String, MinecraftMumblePlayer> getPlayers() {
		synchronized (mutex) {
			return players;
		}
	}

	@Override
	public void onEnable() {
		EventLogger.instance().displayNewLine(false).ignore(DataEvent.class).ignore(PlayerPositionChangeEvent.class);
		EventLogger.instance().ignore(PlayerSpeakPreEvent.class).ignore(PlayerSpeakPostEvent.class).register();
		mutex = new Object();
		players = new HashMap<String, MinecraftMumblePlayer>();

		mumbleServer = new MumbleServer("Mumble-1.0-SNAPSHOT", Plateform.ROOT.resolve("Mumble"));
		mumbleServer.open();
		getServer().getPluginManager().registerEvents(new EventListener(mumbleServer), this);
		new MumbleCommand(this, mumbleServer);
		registerDictionaries();
		registerSoundModifier();
	}

	@Override
	public void onDisable() {
		mumbleServer.close();
		players.clear();
		EventLogger.instance().register();
	}

	/**
	 * @return A map that contains an association of a minecraft player and a mumble player.
	 */
	public static Map<String, MinecraftMumblePlayer> getMinecraftMumblePlayers() {
		return Collections.unmodifiableMap(players);
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

	private void registerSoundModifier() {
		SoundManager.add(new TestModifier());
	}
}
