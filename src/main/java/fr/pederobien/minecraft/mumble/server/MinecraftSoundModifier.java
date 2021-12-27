package fr.pederobien.minecraft.mumble.server;

import java.util.Optional;

import fr.pederobien.mumble.server.impl.modifiers.SoundModifier;

public class MinecraftSoundModifier extends SoundModifier {

	public MinecraftSoundModifier(String name) {
		super(name);
	}

	/**
	 * Protected constructor for method clone.
	 * 
	 * @param original The original sound modifier to clone.
	 */
	protected MinecraftSoundModifier(MinecraftSoundModifier original) {
		super(original);
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
