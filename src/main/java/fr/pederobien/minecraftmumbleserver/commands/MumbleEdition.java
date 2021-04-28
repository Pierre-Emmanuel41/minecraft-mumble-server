package fr.pederobien.minecraftmumbleserver.commands;

import fr.pederobien.minecraftmumbleserver.EMumbleMessageCode;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;

public class MumbleEdition extends CommonMumbleEdition {

	public MumbleEdition(IMumbleServer mumbleServer) {
		super(EMumbleLabel.MUMBLE, EMumbleMessageCode.MUMBLE_EXPLANATION, mumbleServer);
		addEdition(new ChannelsEdition(mumbleServer));
		addEdition(new MumbleDetails(mumbleServer));
		addEdition(new MutePlayerEdition(mumbleServer));
		addEdition(new DeafenPlayerEdition(mumbleServer));
	}
}
