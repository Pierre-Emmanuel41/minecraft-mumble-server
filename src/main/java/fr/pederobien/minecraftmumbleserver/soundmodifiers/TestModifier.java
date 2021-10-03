package fr.pederobien.minecraftmumbleserver.soundmodifiers;

import java.util.Optional;

import fr.pederobien.minecraftmumbleserver.AbstractMinecraftSoundModifier;
import fr.pederobien.minecraftmumbleserver.MinecraftMumblePlayer;
import fr.pederobien.mumble.server.event.PlayerPositionChangeEvent;
import fr.pederobien.mumble.server.impl.MathHelper;
import fr.pederobien.mumble.server.interfaces.IPlayer;
import fr.pederobien.mumble.server.interfaces.IPosition;
import fr.pederobien.utils.event.EventHandler;

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

		double distance = MathHelper.getDistance3D(new TestPosition(), receiver.getPosition());
		double[] volumes = MathHelper.getDefaultLeftAndRightVolume(new TestPosition(), receiver.getPosition());
		return new VolumeResult(-1.0 / 50.0 * distance + 1, volumes[0], volumes[1]);
	}

	@EventHandler
	private void onPlayerPositionChanged(PlayerPositionChangeEvent event) {

	}

	private class TestPosition implements IPosition {

		@Override
		public IPlayer getPlayer() {
			return null;
		}

		@Override
		public double getX() {
			return 0;
		}

		@Override
		public double getY() {
			return 0;
		}

		@Override
		public double getZ() {
			return 70;
		}

		@Override
		public double getYaw() {
			return 0;
		}

		@Override
		public double getPitch() {
			return 0;
		}

		@Override
		public void update(double x, double y, double z, double yaw, double pitch) {

		}
	}
}
