package fr.pederobien.minecraftmumbleserver.commands;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.commands.common.ECommonLabel;
import fr.pederobien.minecraftmumbleserver.EMumbleMessageCode;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleDetails extends CommonMumbleEdition {

	public MumbleDetails(IMumbleServer mumbleServer) {
		super(ECommonLabel.DETAILS, EMumbleMessageCode.MUMBLE_DETAILS__EXPLANATION, mumbleServer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		Map<String, IChannel> channels = get().getChannels();
		if (channels.isEmpty())
			sendSynchro(EMumbleMessageCode.MUMBLE_DETAILS__NO_CHANNEL_REGISTERED);
		else {
			StringJoiner joiner = new StringJoiner("\n");
			Iterator<Map.Entry<String, IChannel>> iterator = channels.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, IChannel> entry = iterator.next();
				List<String> players = entry.getValue().getPlayers().stream().map(player -> player.getName()).collect(Collectors.toList());
				String playerNames = players.isEmpty() ? "None" : concat(players);
				joiner.add(String.format("Channel %s : Players : %s, Modifier : %s", entry.getKey(), playerNames, entry.getValue().getSoundModifier().getName()));
			}
			sendSynchro(sender, EMumbleMessageCode.MUMBLE_DETAILS__CHANNELS_DETAILS, joiner.toString());
		}
		return true;
	}
}
