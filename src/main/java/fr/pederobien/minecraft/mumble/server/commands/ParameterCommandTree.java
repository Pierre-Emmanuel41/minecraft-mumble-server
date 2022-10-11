package fr.pederobien.minecraft.mumble.server.commands;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;
import fr.pederobien.minecraft.mumble.server.EMumbleServerCode;
import fr.pederobien.mumble.server.interfaces.IParameter;

public class ParameterCommandTree {
	private IParameter<?> parameter;
	private IMinecraftCodeRootNode root;
	private ParameterValueNode valueNode;
	private ParameterMinValueNode minNode;
	private ParameterMaxValueNode maxNode;

	protected ParameterCommandTree() {
		root = new MinecraftCodeRootNode("mumble", EMumbleServerCode.MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__ROOT__EXPLANATION, () -> true);

		root.add(valueNode = new ParameterValueNode(() -> getParameter()));
		root.add(minNode = new ParameterMinValueNode(() -> getParameter()));
		root.add(maxNode = new ParameterMaxValueNode(() -> getParameter()));
	}

	/**
	 * @return The underlying parameter managed by this command tree.
	 */
	public IParameter<?> getParameter() {
		return parameter;
	}

	/**
	 * Set the parameter managed by this command tree.
	 * 
	 * @param parameter The new parameter managed by this command tree.
	 */
	public void setParameter(IParameter<?> parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return The root of this command tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node that modifies the current value of a parameter.
	 */
	public ParameterValueNode getValueNode() {
		return valueNode;
	}

	/**
	 * @return The node that modifies the minimum value of a range parameter.
	 */
	public ParameterMinValueNode getMinNode() {
		return minNode;
	}

	/**
	 * @return The node that modifies the maximum value of a range parameter.
	 */
	public ParameterMaxValueNode getMaxNode() {
		return maxNode;
	}
}
