package fr.pederobien.minecraft.mumble.server;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.minecraft.mumble.server.commands.MumbleEdition;
import fr.pederobien.minecraftgameplateform.commands.AbstractSimpleCommand;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleCommand extends AbstractSimpleCommand {

	protected MumbleCommand(JavaPlugin plugin, IMumbleServer mumbleServer) {
		super(plugin, new MumbleEdition(mumbleServer));
	}

}
