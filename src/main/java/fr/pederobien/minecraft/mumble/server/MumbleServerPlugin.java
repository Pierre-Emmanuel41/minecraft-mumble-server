package fr.pederobien.minecraft.mumble.server;

import java.io.FileNotFoundException;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.communication.event.DataEvent;
import fr.pederobien.dictionary.exceptions.MessageRegisteredException;
import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.minecraft.mumble.server.commands.MumbleCommandTree;
import fr.pederobien.minecraft.mumble.server.soundmodifiers.TestModifier;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.mumble.server.event.PlayerPositionChangeEvent;
import fr.pederobien.mumble.server.event.PlayerSpeakPostEvent;
import fr.pederobien.mumble.server.event.PlayerSpeakPreEvent;
import fr.pederobien.mumble.server.impl.MumbleServer;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.utils.AsyncConsole;
import fr.pederobien.utils.event.EventLogger;

public class MumbleServerPlugin extends JavaPlugin {
	private static final String DICTIONARY_FOLDER = "resources/dictionaries/";

	private static Plugin instance;
	private static MumbleCommandTree mumbleTree;
	private static MumbleEventListener listener;

	/**
	 * @return The instance of this plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	/**
	 * @return The tree used to modify a mumble server.
	 */
	public static MumbleCommandTree getMumbleTree() {
		return mumbleTree;
	}

	/**
	 * @return The mumble event listener that contains the map of minecraft mumble server.
	 */
	public static MumbleEventListener getListener() {
		return listener;
	}

	@Override
	public void onEnable() {
		instance = this;

		EventLogger.instance().newLine(false).ignore(DataEvent.class).ignore(PlayerPositionChangeEvent.class);
		EventLogger.instance().ignore(PlayerSpeakPreEvent.class).ignore(PlayerSpeakPostEvent.class).register();

		mumbleTree = new MumbleCommandTree(new MumbleServer("Mumble-1.0-SNAPSHOT", Platform.ROOT.resolve("Mumble")));
		mumbleTree.getMumbleServer().open();
		getServer().getPluginManager().registerEvents(listener = new MumbleEventListener(mumbleTree.getMumbleServer()), this);

		registerDictionaries();
		registerTabExecutor();
		registerSoundModifier();
	}

	@Override
	public void onDisable() {
		mumbleTree.getMumbleServer().close();
	}

	private void registerDictionaries() {
		try {
			JarXmlDictionaryParser dictionaryParser = new JarXmlDictionaryParser(getFile().toPath());

			MinecraftDictionaryContext context = MinecraftDictionaryContext.instance();
			String[] dictionaries = new String[] { "English.xml", "French.xml" };
			for (String dictionary : dictionaries)
				try {
					context.register(dictionaryParser.parse(DICTIONARY_FOLDER.concat(dictionary)));
				} catch (MessageRegisteredException e) {
					AsyncConsole.print(e);
					for (StackTraceElement element : e.getStackTrace())
						AsyncConsole.print(element);
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void registerTabExecutor() {
		PluginCommand mumble = getCommand(mumbleTree.getRoot().getLabel());
		mumble.setTabCompleter(mumbleTree.getRoot());
		mumble.setExecutor(mumbleTree.getRoot());
	}

	private void registerSoundModifier() {
		SoundManager.add(new TestModifier());
	}
}
