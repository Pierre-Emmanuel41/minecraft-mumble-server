package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.google.common.base.Predicate;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IMumbleServer;
import fr.pederobien.mumble.server.interfaces.IParameter;

public class ChannelSoundModifierModifyNode extends MumbleServerNode {
	private ParameterCommandTree parameterTree;

	/**
	 * Creates a node in order to modify the properties of a sound modifier.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelSoundModifierModifyNode(Supplier<IMumbleServer> server) {
		super(server, "modify", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__EXPLANATION, s -> s != null);

		parameterTree = new ParameterCommandTree();
		parameterTree.getRoot().export(this);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Optional<IChannel> optChannel;
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		case 2:
			optChannel = getServer().getChannels().get(args[0]);
			Predicate<String> isNameValid = name -> optChannel.isPresent();
			return check(args[0], isNameValid, filter(optChannel.get().getSoundModifier().getParameters().stream().map(modifier -> modifier.getName()), args));
		default:
			optChannel = getServer().getChannels().get(args[0]);
			if (!optChannel.isPresent())
				return emptyList();

			Optional<IParameter<?>> optParameter = optChannel.get().getSoundModifier().getParameters().get(args[1]);
			if (!optParameter.isPresent())
				return emptyList();

			parameterTree.setParameter(optParameter.get());
			return super.onTabComplete(sender, command, alias, extract(args, 2));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		IChannel channel;
		try {
			Optional<IChannel> optChannel = getServer().getChannels().get(args[0]);
			if (!optChannel.isPresent()) {
				send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__CHANNEL_NOT_FOUND, args[0]));
				return false;
			}

			channel = optChannel.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__CHANNEL_NAME_IS_MISSING).build());
			return false;
		}

		IParameter<?> parameter;
		try {
			Optional<IParameter<?>> optParameter = channel.getSoundModifier().getParameters().get(args[1]);
			if (!optParameter.isPresent()) {
				send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__PARAMETER_NOT_FOUND, args[1],
						channel.getName()));
				return false;
			}

			parameter = optParameter.get();
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__PARAMETER_NAME_IS_MISSING, channel.getName()));
			return false;
		}

		parameterTree.setParameter(parameter);
		return super.onCommand(sender, command, label, extract(args, 2));
	}
}
