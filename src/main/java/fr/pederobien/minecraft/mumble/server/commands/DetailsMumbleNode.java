package fr.pederobien.minecraft.mumble.server.commands;

import java.util.StringJoiner;
import java.util.function.BiConsumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.dictionary.impl.MinecraftMessageEvent.MinecraftMessageEventBuilder;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.mumble.server.EMumbleCode;
import fr.pederobien.minecraft.platform.commands.common.DetailsNode;
import fr.pederobien.minecraft.platform.commands.common.NodeBuilderFactory;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IPlayer;

public class DetailsMumbleNode implements ICodeSender {
	private static final String CROSS_UNICODE = "u\274C";
	private static final String CHECK_UNICODE = "u\2705";
	private DetailsNode<IMumbleServer> detailsNode;

	public DetailsMumbleNode(IMumbleServer server) {
		BiConsumer<CommandSender, IMumbleServer> onDetails = (sender, mumbleServer) -> {
			StringJoiner joiner = new StringJoiner("\n");
			joiner.add(getMessage(sender, EMumbleCode.SERVER_NAME, mumbleServer.getName()));
			joiner.add(getMessage(sender, EMumbleCode.SERVER_IS_OPENED, mumbleServer.isOpened() ? CHECK_UNICODE : CROSS_UNICODE));

			StringJoiner channelJoiner = new StringJoiner("-------------\n");
			for (IChannel channel : mumbleServer.getChannels()) {
				String channelName = getMessage(sender, EMumbleCode.SERVER_CHANNEL_NAME, channel.getName());
				String soundModifierName = getMessage(sender, EMumbleCode.SERVER_CHANNEL_SOUNDMODIFIER_NAME, channel.getSoundModifier().getName());

				StringJoiner playerJoiner = new StringJoiner(", ", "[", "]");
				for (IPlayer player : channel.getPlayers())
					playerJoiner.add(player.getName());

				String players = getMessage(sender, EMumbleCode.SERVER_CHANNEL_PLAYERS, playerJoiner);
				channelJoiner.add(channelName).add(soundModifierName).add(players);
				joiner.add(channelJoiner.toString());

				// Step3: Sending the result with green prefix and suffix.
				MinecraftMessageEventBuilder builder = eventBuilder(EMumbleCode.MUMBLE__DETAILS__ON_DETAILS);
				send(builder.withPrefix(DEFAULT_PREFIX, EColor.GREEN).withSuffix(DEFAULT_SUFFIX, EColor.GREEN).build(joiner));
			}
		};

		// Creating the node that displays the details of the current server.
		detailsNode = NodeBuilderFactory.detailsNode(() -> server, onDetails).build(EMumbleCode.MUMBLE__DETAILS__EXPLANATION);

		// Node available if and only if the current server is not null
		detailsNode.setAvailable(() -> server != null);
	}

	/**
	 * @return The node that displays the details of the mumble server.
	 */
	public DetailsNode<IMumbleServer> getDetailsNode() {
		return detailsNode;
	}
}
