package fr.pederobien.minecraftmumbleserver.commands;

import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public enum EMumbleLabel implements ILabel {
	MUMBLE("mumble"), CHANNELS("channels"), ADD_CHANNEL("add"), REMOVE_CHANNEL("remove"), SOUND_MODIFIER("soundModifier"), MUTE("mute"), DEAFEN("deafen"), KICK("kick");

	private String label;

	private EMumbleLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
