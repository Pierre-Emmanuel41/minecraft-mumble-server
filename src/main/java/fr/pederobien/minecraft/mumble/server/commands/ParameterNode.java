package fr.pederobien.minecraft.mumble.server.commands;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.mumble.server.interfaces.IParameter;
import fr.pederobien.mumble.server.interfaces.IRangeParameter;

public class ParameterNode extends MinecraftCodeNode {
	private Supplier<IParameter<?>> parameter;

	/**
	 * Creates a node specified by the given parameters.
	 * 
	 * @param parameter   The parameter associated to this node.
	 * @param label       The primary node name.
	 * @param explanation The explanation associated to this node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected ParameterNode(Supplier<IParameter<?>> parameter, String label, IMinecraftCode explanation, Function<IParameter<?>, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(parameter == null ? null : parameter.get()));
		this.parameter = parameter;
	}

	/**
	 * @return The parameter associated to this node.
	 */
	public IParameter<?> getParameter() {
		return parameter.get();
	}

	/**
	 * @return The parameter associated to this node.
	 */
	public IRangeParameter<?> getRangeParameter() {
		return parameter.get() instanceof IRangeParameter<?> ? (IRangeParameter<?>) parameter.get() : null;
	}
}
