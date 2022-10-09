package fr.pederobien.minecraft.mumble.server;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.commandtree.events.NodeEvent;
import fr.pederobien.communication.event.ConnectionEvent;
import fr.pederobien.dictionary.event.DictionaryEvent;
import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.minecraft.mumble.server.commands.MumbleServerCommandTree;
import fr.pederobien.minecraft.mumble.server.soundmodifiers.TestModifier;
import fr.pederobien.mumble.server.event.MumblePlayerPositionChangePostEvent;
import fr.pederobien.mumble.server.event.MumblePlayerPositionChangePreEvent;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.utils.AsyncConsole;
import fr.pederobien.utils.event.EventLogger;
import fr.pederobien.vocal.server.event.VocalPlayerSpeakEvent;

public class MumbleServerPlugin extends JavaPlugin {
	private static final String DICTIONARY_FOLDER = "resources/dictionaries/";

	private static Plugin instance;
	private static MumbleServerCommandTree mumbleTree;
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
	public static MumbleServerCommandTree getMumbleTree() {
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

		EventLogger.instance().register();
		EventLogger.instance().ignore(DictionaryEvent.class);
		EventLogger.instance().ignore(ConnectionEvent.class);
		EventLogger.instance().ignore(NodeEvent.class);
		EventLogger.instance().ignore(VocalPlayerSpeakEvent.class);
		EventLogger.instance().ignore(MumblePlayerPositionChangePreEvent.class);
		EventLogger.instance().ignore(MumblePlayerPositionChangePostEvent.class);

		mumbleTree = new MumbleServerCommandTree();

		registerDictionaries();
		registerTabExecutor();
		registerSoundModifier();
	}

	@Override
	public void onDisable() {
		if (mumbleTree.getServer() != null)
			mumbleTree.getServer().close();
		;
	}

	private void registerDictionaries() {
		JarXmlDictionaryParser dictionaryParser = new JarXmlDictionaryParser(getFile().toPath());

		MinecraftDictionaryContext context = MinecraftDictionaryContext.instance();
		String[] dictionaries = new String[] { "English.xml", "French.xml" };
		for (String dictionary : dictionaries)
			try {
				context.register(dictionaryParser.parse(DICTIONARY_FOLDER.concat(dictionary)));
			} catch (Exception e) {
				AsyncConsole.print(e);
				for (StackTraceElement element : e.getStackTrace())
					AsyncConsole.print(element);
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
