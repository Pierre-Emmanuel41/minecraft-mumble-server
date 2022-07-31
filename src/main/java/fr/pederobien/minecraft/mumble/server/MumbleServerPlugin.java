package fr.pederobien.minecraft.mumble.server;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.commandtree.events.NodeEvent;
import fr.pederobien.communication.event.ConnectionEvent;
import fr.pederobien.dictionary.event.DictionaryEvent;
import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.minecraft.mumble.server.commands.MumbleCommandTree;
import fr.pederobien.minecraft.mumble.server.soundmodifiers.TestModifier;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.mumble.server.event.MumblePlayerPositionChangePostEvent;
import fr.pederobien.mumble.server.event.MumblePlayerPositionChangePreEvent;
import fr.pederobien.mumble.server.impl.SimpleMumbleServer;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.utils.AsyncConsole;
import fr.pederobien.utils.event.EventLogger;
import fr.pederobien.vocal.server.event.VocalPlayerSpeakEvent;

public class MumbleServerPlugin extends JavaPlugin {
	private static final String DICTIONARY_FOLDER = "resources/dictionaries/";

	private static Plugin instance;
	private static MumbleCommandTree mumbleTree;
	private static MumbleEventListener listener;
	private static PlayerLocationUpdater updater;

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

		EventLogger.instance().newLine(false).register();
		EventLogger.instance().ignore(ConnectionEvent.class);
		EventLogger.instance().ignore(VocalPlayerSpeakEvent.class);
		EventLogger.instance().ignore(MumblePlayerPositionChangePreEvent.class);
		EventLogger.instance().ignore(MumblePlayerPositionChangePostEvent.class);
		EventLogger.instance().ignore(DictionaryEvent.class);
		EventLogger.instance().ignore(NodeEvent.class);

		mumbleTree = new MumbleCommandTree(new SimpleMumbleServer("Minecraft_2.0", Platform.ROOT.resolve("Mumble").toAbsolutePath().toString()));
		mumbleTree.getMumbleServer().open();
		getServer().getPluginManager().registerEvents(listener = new MumbleEventListener(mumbleTree.getMumbleServer()), this);

		updater = new PlayerLocationUpdater(this, mumbleTree.getMumbleServer());
		registerDictionaries();
		registerTabExecutor();
		registerSoundModifier();
	}

	@Override
	public void onDisable() {
		mumbleTree.getMumbleServer().close();
		updater.dispose();
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
