package fr.pederobien.minecraftmumbleserver.soundmodifiers;

import java.util.Optional;

import fr.pederobien.minecraftmanagers.WorldManager;
import fr.pederobien.minecraftmumbleserver.AbstractMinecraftSoundModifier;
import fr.pederobien.minecraftmumbleserver.MinecraftMumblePlayer;
import fr.pederobien.mumble.server.impl.Position;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class TestModifier extends AbstractMinecraftSoundModifier {

	public TestModifier() {
		super("test");
	}

	@Override
	public VolumeResult calculate(IPlayer transmitter, IPlayer receiver) {
		Optional<MinecraftMumblePlayer> optTransmitter = getPlayer(transmitter.getName());
		if (!optTransmitter.isPresent())
			return new VolumeResult(0);

		Optional<MinecraftMumblePlayer> optReceiver = getPlayer(receiver.getName());
		if (!optReceiver.isPresent())
			return new VolumeResult(0);

		if (!optTransmitter.get().getMinecraftPlayer().getWorld().equals(optReceiver.get().getMinecraftPlayer().getWorld()))
			return new VolumeResult(0);

		double distance = getDistance3D(new Position(0, 62, 0, 0, 0), receiver.getPosition());

		double yaw = WorldManager.getYaw(optTransmitter.get().getMinecraftPlayer(), WorldManager.locationFromOverworld(0, 0, 0));
		double leftVolume = 1.0, rightVolume = 1.0;
		if (0 <= yaw && yaw <= 90)
			rightVolume = Math.cos(asRadian(yaw));
		else if (90 < yaw && yaw <= 180)
			rightVolume = Math.cos(asRadian(yaw - 90));
		else if (-90 <= yaw && yaw <= 0)
			leftVolume = Math.cos(asRadian(yaw));
		else
			leftVolume = Math.cos(asRadian(yaw + 90));
		return new VolumeResult(-1.0 / 50.0 * distance + 1, leftVolume, rightVolume);
	}
}
