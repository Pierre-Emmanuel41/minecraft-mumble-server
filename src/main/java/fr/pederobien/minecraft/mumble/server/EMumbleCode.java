package fr.pederobien.minecraft.mumble.server;

import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;

public enum EMumbleCode implements IMinecraftCode {
	// Common codes --------------------------------------------------------------

	// Code for the name parameter
	SERVER_NAME,

	// Code for the opened parameter
	SERVER_IS_OPENED,

	// Code for the channel name parameter
	SERVER_CHANNEL_NAME,

	// Code for the channel sound modifier parameter
	SERVER_CHANNEL_SOUNDMODIFIER_NAME,

	// Code for the final diameter parameter
	SERVER_CHANNEL_PLAYERS,

	// Code for the "mumble" command ---------------------------------------------
	MUMBLE_EXPLANATION,

	// Code for the "mumble channels" command ------------------------------------
	MUMBLE__CHANNELS__EXPLANATION,

	// Code for the "mumble channels add" command --------------------------------
	MUMBLE__CHANNELS_ADD__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNELS_ADD__NAME_IS_MISSING,

	// Code when the channel name is already used
	MUMBLE__CHANNELS_ADD__CHANNEL_ALREADY_REGISTERED,

	// Code when a channel is added
	MUMBLE__CHANNELS_ADD__CHANNEL_ADDED,

	// Code for the "mumble channels remove" command -----------------------------
	MUMBLE__CHANNELS_REMOVE__EXPLANATION,

	// Code when all channels are removed
	MUMBLE__CHANNELS_REMOVE__ALL_CHANNELS_REMOVED,

	// Code when a channel does not exist
	MUMBLE__CHANNELS_REMOVE__CHANNEL_NOT_FOUND,

	// Code when no channel is removed
	MUMBLE__CHANNELS_REMOVE__NO_CHANNEL_REMOVED,

	// Code when one channel is removed
	MUMBLE__CHANNELS_REMOVE__ONE_CHANNEL_REMOVED,

	// Code when several channels are removed
	MUMBLE__CHANNELS_REMOVE__SEVERAL_CHANNELS_REMOVED,

	// Code for the "mumble channels rename" command -----------------------------
	MUMBLE__CHANNELS_RENAME__EXPLANATION,

	// code when the channel name is missing
	MUMBLE__CHANNELS_RENAME__CHANNEL_NAME_IS_MISSING,

	// Code when the new channel name is missing
	MUMBLE__CHANNELS_RENAME__NEW_CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MUMBLE__CHANNELS_RENAME__CHANNEL_DOES_NOT_EXIST,

	// Code when the channel name is already used
	MUMBLE__CHANNELS_RENAME__NAME_ALREADY_USED,

	// Code when a channel is renamed
	MUMBLE__CHANNELS_RENAME__CHANNEL_RENAMED,

	// Code for the "mumble channels soundModifier" command ----------------------
	MUMBLE__CHANNELS_SOUND_MODIFIER__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNELS_SOUND_MODIFIER__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MUMBLE__CHANNELS_SOUND_MODIFIER__CHANNEL_DOES_NOT_EXIST,

	// Code when the sound modifier name is missing
	MUMBLE__CHANNELS_SOUND_MODIFIER__MODIFIER_NAME_IS_MISSING,

	// Code when the sound modifier does not exist
	MUMBLE__CHANNELS_SOUND_MODIFIER__MODIFIER_DOES_NOT_EXIST,

	// Code when the channel's sound modifier is updated
	MUMBLE__CHANNELS_SOUND_MODIFIER__MODIFIER_UPDATED,

	// Code for the "mumble kick" command ----------------------------------------
	MUMBLE__KICK__EXPLANATION,

	// Code when all players are kicked from a mumble server
	MUMBLE__KICK__ALL_PLAYERS_KICKED,

	// Code when the player to kick does not exist
	MUMBLE__KICK__PLAYER_NOT_FOUND,

	// Code when the player to kick is not registered in a channel
	MUMBLE__KICK__PLAYER_NOT_REGISTERED_IN_CHANNEL,

	// Code when no player is kicked
	MUMBLE__KICK__NO_PLAYER_KICKED,

	// Code when one player is kicked
	MUMBLE__KICK__ONE_PLAYER_KICKED,

	// Code when several players are kicked
	MUMBLE__KICK__SEVERAL_PLAYERS_KICKED,

	// Code for the "mumble mute" command ----------------------------------------
	MUMBLE__MUTE__EXPLANATION,

	// Code when all players are muted
	MUMBLE__MUTE__ALL_PLAYERS_MUTED,

	// Code when the player to mute does not exist
	MUMBLE__MUTE__PLAYER_NOT_FOUND,

	// Code when the player is not registered in a channel
	MUMBLE__MUTE__PLAYER_NOT_REGISTERED_IN_CHANNEL,

	// Code when no players is mute
	MUMBLE__MUTE__NO_PLAYER_MUTED,

	// Code when one player is mute
	MUMBLE__MUTE__ONE_PLAYER_MUTED,

	// code when several players are mute
	MUMBLE__MUTE__SEVERAL_PLAYERS_MUTED,

	// Code for the "mumble deafen" command --------------------------------------
	MUMBLE__DEAFEN__EXPLANATION,

	// Code when all players are deafened
	MUMBLE__DEAFEN__ALL_PLAYERS_DEAFENED,

	// Code when to player to deafen does not exist
	MUMBLE__DEAFEN__PLAYER_NOT_FOUND,

	// Code when the player is not registered in a channel
	MUMBLE__DEAFEN__PLAYER_NOT_REGISTERED_IN_CHANNEL,

	// Code when no player is deafened
	MUMBLE__DEAFEN__NO_PLAYER_DEAFENED,

	// Code when one player is deafened
	MUMBLE__DEAFEN__ONE_PLAYER_DEAFENED,

	// Code when several players are deafened
	MUMBLE__DEAFEN__SEVERAL_PLAYERS_DEAFENED,

	// Code for the "mumble details" command -------------------------------------
	MUMBLE__DETAILS__EXPLANATION,

	// Code when displaying the server details
	MUMBLE__DETAILS__ON_DETAILS,

	;

	private IPlayerGroup group;

	private EMumbleCode() {
		this(PlayerGroup.OPERATORS);
	}

	private EMumbleCode(IPlayerGroup group) {
		this.group = group;
	}

	@Override
	public String value() {
		return name();
	}

	@Override
	public IPlayerGroup getGroup() {
		return group;
	}

	@Override
	public void setGroup(IPlayerGroup group) {
		this.group = group;
	}
}
