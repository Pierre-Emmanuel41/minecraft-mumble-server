package fr.pederobien.minecraft.mumble.server.commands;

import java.text.DecimalFormat;
import java.util.StringJoiner;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.impl.SoundManager;
import fr.pederobien.mumble.server.impl.modifiers.RangeParameter;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IParameter;
import fr.pederobien.mumble.server.interfaces.IPlayer;
import fr.pederobien.mumble.server.interfaces.IRangeParameter;
import fr.pederobien.mumble.server.interfaces.ISoundModifier;

public class DetailsNode extends MumbleServerNode {

	/**
	 * Creates a node in order to display the server configuration.
	 * 
	 * @param server The server associated to this node.
	 */
	protected DetailsNode(Supplier<IMumbleServer> server) {
		super(server, "details", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__EXPLANATION, s -> s != null);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String tabulations = "\t";
		StringJoiner serverJoiner = new StringJoiner("\n");
		int counter, size;

		// Server's name
		String serverName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__SERVER_NAME, getServer().getName());
		serverJoiner.add(serverName);

		serverJoiner.add("");

		// Server's sound modifiers
		String soundModifiers = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__SOUND_MODIFIERS);
		serverJoiner.add(soundModifiers);

		counter = 0;
		size = SoundManager.toList().size();
		for (ISoundModifier soundModifier : SoundManager.toList()) {
			// Sound modifier's name
			String modifierName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__SOUND_MODIFIER_NAME, soundModifier.getName());
			serverJoiner.add(tabulations.concat(modifierName));

			// Sound modifier's parameters
			String parameters = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__SOUND_MODIFIER_PARAMETERS);
			serverJoiner.add(tabulations.concat(parameters));

			String parameterTabulations = tabulations.concat(tabulations);
			int parameterCounter = 0;
			int parameterSize = soundModifier.getParameters().toList().size();
			for (IParameter<?> parameter : soundModifier.getParameters()) {
				// Parameter's name
				String parameterName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_NAME, parameter.getName());
				serverJoiner.add(parameterTabulations.concat(parameterName));

				// Parameter's value
				String parameterValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_VALUE, parameter.getValue());
				serverJoiner.add(parameterTabulations.concat(parameterValue));

				// Parameter's default value
				String parameterDefaultValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_DEFAULT_VALUE,
						parameter.getDefaultValue());
				serverJoiner.add(parameterTabulations.concat(parameterDefaultValue));

				if (parameter instanceof IRangeParameter<?>) {
					// Parameter's minimum value
					String parameterMinValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_MINIMUM_VALUE,
							((RangeParameter<?>) parameter).getMin());
					serverJoiner.add(parameterTabulations.concat(parameterMinValue));

					// Parameter's maximum value
					String parameterMaxValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_MAXIMUM_VALUE,
							((RangeParameter<?>) parameter).getMax());
					serverJoiner.add(parameterTabulations.concat(parameterMaxValue));
				}

				parameterCounter++;
				if (parameterCounter < parameterSize)
					serverJoiner.add("");
			}

			counter++;
			if (counter < size)
				serverJoiner.add("");
		}

		serverJoiner.add("");

		// Server's players
		String players = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYERS);
		serverJoiner.add(players);

		counter = 0;
		size = getServer().getPlayers().toList().size();
		for (IPlayer player : getServer().getPlayers()) {
			// Player's name
			String playerName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_NAME, player.getName());
			serverJoiner.add(tabulations.concat(playerName));

			String playerTabulations = tabulations.concat(tabulations);

			// Player's identifier
			String playerIdentifier = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_IDENTIFIER, player.getIdentifier());
			serverJoiner.add(playerTabulations.concat(playerIdentifier));

			// Player's online status
			EMumbleServerCode onlineCode;
			if (player.isOnline())
				onlineCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_ONLINE;
			else
				onlineCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_OFFLINE;

			String playerOnline = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_ONLINE_STATUS, getMessage(sender, onlineCode));
			serverJoiner.add(playerTabulations.concat(playerOnline));

			if (player.isOnline()) {
				// Player's game address
				String ipGameAddress = player.getGameAddress().getAddress().getHostAddress();
				int gamePort = player.getGameAddress().getPort();
				String playerGameAddress = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_GAME_ADDRESS, ipGameAddress, gamePort);
				serverJoiner.add(playerTabulations.concat(playerGameAddress));

				// Player's admin status
				EMumbleServerCode adminCode;
				if (player.isAdmin())
					adminCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_ADMIN;
				else
					adminCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_NOT_ADMIN;

				String playerAdmin = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_ADMIN_STATUS, getMessage(sender, adminCode));
				serverJoiner.add(playerTabulations.concat(playerAdmin));

				// Player's mute status
				EMumbleServerCode muteCode;
				if (player.isMute())
					muteCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_MUTE;
				else
					muteCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_NOT_MUTE;

				String playerMute = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_MUTE_STATUS, getMessage(sender, muteCode));
				serverJoiner.add(playerTabulations.concat(playerMute));

				// Player's mute by players
				String muteByPlayerNames = concat(player.getMuteByPlayers().map(p -> p.getName()).collect(Collectors.toList()));
				String muteByPlayers = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_MUTE_BY, muteByPlayerNames);
				serverJoiner.add(playerTabulations.concat(muteByPlayers));

				// Player's deafen status
				EMumbleServerCode deafenCode;
				if (player.isDeafen())
					deafenCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_DEAFEN;
				else
					deafenCode = EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_NOT_DEAFEN;

				String playerDeafen = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_DEAFEN_STATUS, getMessage(sender, deafenCode));
				serverJoiner.add(playerTabulations.concat(playerDeafen));

				// Player's position
				DecimalFormat format = new DecimalFormat("#.####");
				String x = format.format(player.getPosition().getX());
				String y = format.format(player.getPosition().getY());
				String z = format.format(player.getPosition().getZ());
				String yaw = format.format(player.getPosition().getYaw());
				String pitch = format.format(player.getPosition().getPitch());
				String playerPosition = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_POSITION, x, y, z, yaw, pitch);
				serverJoiner.add(playerTabulations.concat(playerPosition));
			}

			counter++;
			if (counter < size)
				serverJoiner.add("");
		}

		serverJoiner.add("");

		// Server's channel
		String channels = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__CHANNELS);
		serverJoiner.add(channels);

		counter = 0;
		size = getServer().getChannels().toList().size();
		for (IChannel channel : getServer().getChannels()) {
			// Channel's name
			String channelName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__CHANNEL_NAME, channel.getName());
			serverJoiner.add(tabulations.concat(channelName));

			// Channel's sound modifier
			String soundModifier = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__CHANNEL_SOUND_MODIFIER);
			serverJoiner.add(tabulations.concat(soundModifier));

			String modifierTabulations = tabulations.concat(tabulations);

			// Sound modifier's name
			String modifierName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__SOUND_MODIFIER_NAME, channel.getSoundModifier().getName());
			serverJoiner.add(modifierTabulations.concat(modifierName));

			// Sound modifier's parameters
			String parameters = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__SOUND_MODIFIER_PARAMETERS);
			serverJoiner.add(modifierTabulations.concat(parameters));

			String parameterTabulations = tabulations.concat(modifierTabulations);
			int parameterCounter = 0;
			int parameterSize = channel.getSoundModifier().getParameters().toList().size();
			for (IParameter<?> parameter : channel.getSoundModifier().getParameters()) {
				// Parameter's name
				String parameterName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_NAME, parameter.getName());
				serverJoiner.add(parameterTabulations.concat(parameterName));

				// Parameter's value
				String parameterValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_VALUE, parameter.getValue());
				serverJoiner.add(parameterTabulations.concat(parameterValue));

				if (parameter instanceof IRangeParameter<?>) {
					// Parameter's minimum value
					String parameterMinValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_MINIMUM_VALUE,
							((RangeParameter<?>) parameter).getMin());
					serverJoiner.add(parameterTabulations.concat(parameterMinValue));

					// Parameter's maximum value
					String parameterMaxValue = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PARAMETER_MAXIMUM_VALUE,
							((RangeParameter<?>) parameter).getMax());
					serverJoiner.add(parameterTabulations.concat(parameterMaxValue));
				}

				parameterCounter++;
				if (parameterCounter < parameterSize)
					serverJoiner.add("");
			}

			// Server's players
			String channelPlayers = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYERS);
			serverJoiner.add(tabulations.concat(channelPlayers));

			String playerTabulations = tabulations.concat(tabulations);
			for (IPlayer player : channel.getPlayers()) {
				// Player's name
				String playerName = getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__PLAYER_NAME, player.getName());
				serverJoiner.add(playerTabulations.concat(playerName));
			}

			counter++;
			if (counter < size)
				serverJoiner.add("");
		}

		sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__DETAILS__SERVER, serverJoiner);
		return true;
	}
}
