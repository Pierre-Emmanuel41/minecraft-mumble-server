package fr.pederobien.minecraftmumbleserver.commands;

import fr.pederobien.minecraftmumbleserver.EMumbleMessageCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class ChannelsEdition extends CommonMumbleEdition {

	public ChannelsEdition(IMumbleServer mumbleServer) {
		super(EMumbleLabel.CHANNELS, EMumbleMessageCode.CHANNELS__EXPLANATION, mumbleServer);
		addEdition(new AddChannelEdition(mumbleServer));
		addEdition(new RemoveChannelEdition(mumbleServer));
		addEdition(new RenameChannelEdition(mumbleServer));
		addEdition(new SoundModifierEdition(mumbleServer));
	}
}
