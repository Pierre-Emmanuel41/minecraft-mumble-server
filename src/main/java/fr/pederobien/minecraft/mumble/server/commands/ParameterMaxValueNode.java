package fr.pederobien.minecraft.mumble.server.commands;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IChannel;
import fr.pederobien.mumble.server.interfaces.IParameter;
import fr.pederobien.mumble.server.interfaces.IRangeParameter;
import fr.pederobien.mumble.server.interfaces.ISoundModifier;

public class ParameterMaxValueNode extends ParameterNode {

	/**
	 * Creates a node in order to update the current value of a parameter.
	 * 
	 * @param parameter The parameter associated to this node.
	 */
	protected ParameterMaxValueNode(Supplier<IParameter<?>> parameter) {
		super(parameter, "maxValue", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__EXPLANATION, p -> p instanceof IRangeParameter<?>);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__MAX_VALUE__COMPLETION));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Object max;
		try {
			max = getParameter().getType().getValue(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__VALUE_IS_MISSING, getParameter().getName()));
			return false;
		} catch (Exception e) {
			send(eventBuilder(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__VALUE_BAD_FORMAT, getParameter().getName()));
			return false;
		}

		ISoundModifier soundModifier = getParameter().getSoundModifier();
		IChannel channel = soundModifier.getChannel();
		getRangeParameter().setMax(max);
		sendSuccessful(sender, EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__VALUE_SET, getParameter().getName(), soundModifier.getName(),
				channel.getName(), max);
		return true;
	}
}
