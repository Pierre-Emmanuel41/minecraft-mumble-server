package fr.pederobien.minecraft.mumble.server.soundmodifiers;

import java.util.Map;

import fr.pederobien.minecraft.mumble.server.MinecraftMumblePlayer;
import fr.pederobien.minecraft.mumble.server.MumbleServerPlugin;
import fr.pederobien.mumble.server.impl.MathHelper;
import fr.pederobien.mumble.server.impl.modifiers.RangeParameter;
import fr.pederobien.mumble.server.impl.modifiers.SoundModifier;
import fr.pederobien.mumble.server.interfaces.IParameter;
import fr.pederobien.mumble.server.interfaces.IPlayer;
import fr.pederobien.mumble.server.interfaces.IPosition;

public class TestModifier extends SoundModifier {
	private static final String X_PARAMETER_NAME = "X";
	private static final String Y_PARAMETER_NAME = "Y";
	private static final String Z_PARAMETER_NAME = "Z";
	private static final String RADIUS_PARAMETER_NAME = "Radius";
	private IParameter<Double> xParameter, yParameter, zParameter, radiusParameter;

	public TestModifier() {
		super("test");
		getParameters().add(xParameter = RangeParameter.of(this, X_PARAMETER_NAME, 0.0, -29999984.0, 29999984.0));
		getParameters().add(yParameter = RangeParameter.of(this, Y_PARAMETER_NAME, 0.0, -29999984.0, 29999984.0));
		getParameters().add(zParameter = RangeParameter.of(this, Z_PARAMETER_NAME, 70.0, 0.0, 256.0));
		getParameters().add(radiusParameter = RangeParameter.of(this, RADIUS_PARAMETER_NAME, 50.0, 0.0, Double.MAX_VALUE));
	}

	/**
	 * Private constructor for method clone.
	 * 
	 * @param original The original sound modifier to clone.
	 */
	private TestModifier(TestModifier original) {
		super(original);
		this.xParameter = getParameters().getParameter(X_PARAMETER_NAME);
		this.yParameter = getParameters().getParameter(Y_PARAMETER_NAME);
		this.zParameter = getParameters().getParameter(Z_PARAMETER_NAME);
		this.radiusParameter = getParameters().getParameter(RADIUS_PARAMETER_NAME);
	}

	@Override
	public VolumeResult dispatch(IPlayer transmitter, IPlayer receiver) {
		if (!transmitter.equals(receiver))
			return VolumeResult.NONE;

		Map<String, MinecraftMumblePlayer> players = MumbleServerPlugin.getListener().getPlayers();
		MinecraftMumblePlayer minecraftTransmitter = players.get(transmitter.getName());
		if (minecraftTransmitter == null)
			return new VolumeResult(0);

		MinecraftMumblePlayer minecraftReceiver = players.get(receiver.getName());
		if (minecraftReceiver == null)
			return new VolumeResult(0);

		if (minecraftTransmitter.getMinecraftPlayer().getWorld().equals(minecraftReceiver.getMinecraftPlayer().getWorld()))
			return new VolumeResult(0);

		double distance = MathHelper.getDistance3D(new TestPosition(), receiver.getPosition());
		double[] volumes = MathHelper.getDefaultLeftAndRightVolume(new TestPosition(), receiver.getPosition());
		VolumeResult result = new VolumeResult((-1.0 / radiusParameter.getValue()) * distance + 1, volumes[0], volumes[1]);
		return result;
	}

	@Override
	public TestModifier clone() {
		return new TestModifier(this);
	}

	private class TestPosition implements IPosition {

		@Override
		public IPlayer getPlayer() {
			return null;
		}

		@Override
		public double getX() {
			return xParameter.getValue();
		}

		@Override
		public double getY() {
			return yParameter.getValue();
		}

		@Override
		public double getZ() {
			return zParameter.getValue();
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
