package fr.pederobien.minecraftmumbleserver;

import java.util.Optional;

import fr.pederobien.mumble.server.impl.AbstractSoundModifier;
import fr.pederobien.mumble.server.interfaces.IPosition;

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

	/**
	 * Get the distance between the two position.
	 * 
	 * @param transmitter The transmitter position.
	 * @param receiver    The receiver position.
	 * 
	 * @return The distance calculated with 3 dimensions.
	 */
	protected double getDistance3D(IPosition transmitter, IPosition receiver) {
		double xDistance = Math.pow(transmitter.getX() - receiver.getX(), 2);
		double yDistance = Math.pow(transmitter.getY() - receiver.getY(), 2);
		double zDistance = Math.pow(transmitter.getZ() - receiver.getZ(), 2);
		return Math.sqrt(xDistance + yDistance + zDistance);
	}

	/**
	 * Transforms the given double that represent an angle in degree as an angle in radian.
	 * 
	 * @param angle The angle to transform.
	 * 
	 * @return The angle in radian.
	 */
	protected double asRadian(double angle) {
		return Math.PI * angle / 180;
	}
}
