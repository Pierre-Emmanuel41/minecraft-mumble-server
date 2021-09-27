package fr.pederobien.minecraftmumbleserver;

import java.util.Optional;

import fr.pederobien.mumble.server.impl.modifiers.AbstractSoundModifier;

public abstract class AbstractMinecraftSoundModifier extends AbstractSoundModifier {

	public AbstractMinecraftSoundModifier(String name) {
		super(name);
	}

	/**
	 * Get the minecraft mumble player associated to the given name.
	 * 
	 * @param name The player name.
	 * 
	 * @return An optional that contains the player if it is registered, an empty optional otherwise.
	 */
	public Optional<MinecraftMumblePlayer> getPlayer(String name) {
		return Optional.ofNullable(MumbleServerPlugin.getPlayers().get(name));
	}
}
