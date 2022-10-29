package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.impl.modifiers.RangeParameter;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IParameter;

public class ChannelSoundModifierDetailsNode extends MumbleServerNode {

	/**
	 * Creates a node in order to display the property of the sound modifier associated to a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelSoundModifierDetailsNode(Supplier<IMumbleServer> server) {
		super(server, "details", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		IChannel channel;
		try {
			Optional<IChannel> optChannel = getServer().getChannels().get(args[0]);
			if (!optChannel.isPresent()) {
				send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__CHANNEL_NOT_FOUND, args[0]));
				return false;
			}

			channel = optChannel.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__CHANNEL_NAME_IS_MISSING).build());
			return false;
		}

		String tabulations = "\t";
		StringJoiner modifierJoiner = new StringJoiner("\n");

		// Sound modifier's name
		String modifierName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__SOUND_MODIFIER_NAME,
				channel.getSoundModifier().getName());
		modifierJoiner.add(modifierName);

		// Sound modifier's parameters
		String parameters = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__SOUND_MODIFIER_PARAMETERS);
		modifierJoiner.add(parameters);

		int counter = 0;
		int size = channel.getSoundModifier().getParameters().toList().size();
		for (IParameter<?> parameter : channel.getSoundModifier().getParameters()) {
			// Parameter's name
			String parameterName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_NAME,
					parameter.getName());
			modifierJoiner.add(tabulations.concat(parameterName));

			// Parameter's value
			String parameterValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_VALUE,
					parameter.getValue());
			modifierJoiner.add(tabulations.concat(parameterValue));

			// Parameter's default value
			String parameterDefaultValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_DEFAULT_VALUE,
					parameter.getDefaultValue());
			modifierJoiner.add(tabulations.concat(parameterDefaultValue));

			if (parameter instanceof RangeParameter<?>) {
				// Parameter's minimum value
				String parameterMinValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_MINIMUM_VALUE,
						((RangeParameter<?>) parameter).getMin());
				modifierJoiner.add(tabulations.concat(parameterMinValue));

				// Parameter's maximum value
				String parameterMaxValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_MAXIMUM_VALUE,
						((RangeParameter<?>) parameter).getMax());
				modifierJoiner.add(tabulations.concat(parameterMaxValue));
			}

			counter++;
			if (counter < size)
				modifierJoiner.add("");
		}

		sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__SOUND_MODIFIER_DETAILS, channel.getName(),
				modifierJoiner);
		return true;
	}
}
