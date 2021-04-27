package fr.pederobien.minecraftmumbleserver;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EMumbleMessageCode implements IMinecraftMessageCode {
	MUMBLE_EXPLANATION,

	CHANNELS__EXPLANATION,

	// Code for command add
	ADD_CHANNEL__EXPLANATION, ADD_CHANNEL__ON_TAB_COMPLETE, ADD_CHANNEL__NAME_IS_MISSING, ADD_CHANNEL__CHANNEL_ALREADY_REGISTERED, ADD_CHANNEL__CHANNEL_ADDED,

	// Code for command remove
	REMOVE_CHANNEL__EXPLANATION, REMOVE_CHANNEL__ALL_CHANNELS_REMOVED, REMOVE_CHANNEL__CHANNEL_DOES_NOT_EXIST, REMOVE_CHANNEL__NO_CHANNEL_REMOVED,
	REMOVE_CHANNEL__ONE_CHANNEL_REMOVED, REMOVE_CHANNEL__SEVERAL_CHANNELS_REMOVED,

	// Code for command rename
	RENAME_CHANNEL__EXPLANATION, RENAME_CHANNEL__CHANNEL_NAME_IS_MISSING, RENAME_CHANNEL__NEW_CHANNEL_NAME_IS_MISSING, RENAME_CHANNEL__CHANNEL_ALREADY_EXIST,
	RENAME_CHANNEL__CHANNEL_DOES_NOT_EXIST, RENAME_CHANNEL__CHANNEL_RENAMED,

	// Code for command soundModifier
	SOUND_MODIFIER__EXPLANATION, SOUND_MODIFIER__CHANNEL_NAME_IS_MISSING, SOUND_MODIFIER__CHANNEL_DOES_NOT_EXIST, SOUND_MODIFIER__MODIFIER_NAME_IS_MISSING,
	SOUND_MODIFIER__MODIFIER_DOES_NOT_EXIST, SOUND_MODIFIER__MODIFIER_UPDATED,

	// Code for command mute
	MUTE__EXPLANATION, MUTE__ALL_PLAYERS_MUTE, MUTE__PLAYER_NOT_REGISTERED_IN_A_CHANNEL, MUTE__NO_PLAYER_MUTE, MUTE__ONE_PLAYER_MUTE, MUTE__SEVERAL_PLAYERS_MUTE,

	// Code for command details
	MUMBLE_DETAILS__EXPLANATION, MUMBLE_DETAILS__NO_CHANNEL_REGISTERED, MUMBLE_DETAILS__CHANNELS_DETAILS;

	private Permission permission;

	private EMumbleMessageCode() {
		this(Permission.OPERATORS);
	}

	private EMumbleMessageCode(Permission permission) {
		this.permission = permission;
	}

	@Override
	public String value() {
		return toString();
	}

	@Override
	public Permission getPermission() {
		return permission;
	}

	@Override
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}
