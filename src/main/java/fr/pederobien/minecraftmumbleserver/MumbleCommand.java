package fr.pederobien.minecraftmumbleserver;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.minecraftgameplateform.commands.AbstractSimpleCommand;
import fr.pederobien.minecraftmumbleserver.commands.MumbleEdition;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleCommand extends AbstractSimpleCommand {

	protected MumbleCommand(JavaPlugin plugin, IMumbleServer mumbleServer) {
		super(plugin, new MumbleEdition(mumbleServer));
	}

}
