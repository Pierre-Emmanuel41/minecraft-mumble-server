package fr.pederobien.minecraft.mumble.server;

import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;

public enum EMumbleServerCode implements IMinecraftCode {

	// Code for the name completion
	MINECRAFT__MUMBLE_SERVER_CL__NAME__COMPLETION,

	// Code for the configuration port number completion
	MINECRAFT__MUMBLE_SERVER_CL__CONFIGURATION_PORT__COMPLETION,

	// Code for the vocal port number completion
	MINECRAFT__MUMBLE_SERVER_CL__VOCAL_PORT__COMPLETION,

	// Code for the game port number completion
	MINECRAFT__MUMBLE_SERVER_CL__GAME_PORT__COMPLETION,

	// Code for the parameter value completion
	MINECRAFT__MUMBLE_SERVER_CL__VALUE__COMPLETION,

	// Code for the parameter minimum value completion
	MINECRAFT__MUMBLE_SERVER_CL__MIN_VALUE__COMPLETION,

	// Code for the parameter maximum value completion
	MINECRAFT__MUMBLE_SERVER_CL__MAX_VALUE__COMPLETION,

	// Code for the "mumble" command --------------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__ROOT__EXPLANATION,

	// Code for the "open" command ----------------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__OPEN__EXPLANATION,

	// Code when the server name is missing
	MINECRAFT__MUMBLE_SERVER_CL__OPEN__NAME_IS_MISSING,

	// Code when the vocal port equals the configuration port
	MINECRAFT__MUMBLE_SERVER_CL__OPEN__SERVER_TYPE_DISMATCH,

	// Code when the vocal port equals the configuration port
	MINECRAFT__MUMBLE_SERVER_CL__OPEN__SIMPLE_SERVER_OPENED,

	// Code for the "close" command ---------------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CLOSE__EXPLANATION,

	// Code when the server has been closed
	MINECRAFT__MUMBLE_SERVER_CL__CLOSE__SERVER_CLOSED,

	// Code for the "channel" command -------------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__EXPLANATION,

	// Code for the "channel add" command ---------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__EXPLANATION,

	// Code for the "channel add channel" command -------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__EXPLANATION,

	// Code when the name of the channel to add is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__NAME_IS_MISSING,

	// Code when the channel name is already registered
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__CHANNEL_ALREADY_REGISTERED,

	// Code when the sound modifier of the channel to add is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_IS_MISSING,

	// Code when the sound modifier does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_NOT_FOUND,

	// Code when a channel has been added to a mumble server
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__CHANNEL__CHANNEL_ADDED,

	// Code for the "channel add player" command --------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__EXPLANATION,

	// Code when the channel name is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__CHANNEL_NOT_FOUND,

	// Code when the player does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__PLAYER_NOT_FOUND,

	// Code when the player is already registered in a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__PLAYER_ALREADY_REGISTERED,

	// Code when the player has not joined the mumble server
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__PLAYER_NOT_JOINED,

	// Code when no player has been added to a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__NO_PLAYER_ADDED,

	// Code when one player has been added to a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__ONE_PLAYER_ADDED,

	// Code when several players have been added to a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__ADD__PLAYER__SEVERAL_PLAYERS_ADDED,

	// Code for the "channel remove" command ------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__EXPLANATION,

	// Code for the "channel remove channel" command ----------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__CHANNEL__EXPLANATION,

	// Code when the channel to remove does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__CHANNEL__CHANNEL_NOT_FOUND,

	// Code when no channel has been removed
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__CHANNEL__NO_CHANNEL_REMOVED,

	// Code when one channel has been removed
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__CHANNEL__ONE_CHANNEL_REMOVED,

	// Code when several channels have been removed
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__CHANNEL__SEVERAL_CHANNELS_REMOVED,

	// Code for the "channel remove player" command --------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__PLAYER__EXPLANATION,

	// Code when the channel name is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__PLAYER__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__PLAYER__CHANNEL_NOT_FOUND,

	// Code when the player does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__PLAYER__PLAYER_NOT_FOUND,

	// Code when no player has been removed to a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__PLAYER__NO_PLAYER_REMOVED,

	// Code when one player has been removed to a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__PLAYER__ONE_PLAYER_REMOVED,

	// Code when several players have been removed to a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__REMOVE__PLAYER__SEVERAL_PLAYERS_REMOVED,

	// Code for the "channel rename" command ------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__RENAME__EXPLANATION,

	// Code when the name of the channel to rename is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__RENAME__NAME_IS_MISSING,

	// Code when the channel to rename does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__RENAME__CHANNEL_NOT_FOUND,

	// Code when the channel to rename does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__RENAME__NEW_NAME_IS_MISSING,

	// Code when a channel is already registered
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__RENAME__CHANNEL_ALREADY_REGISTERED,

	// Code when a channel has been renamed
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__RENAME__CHANNEL_RENAMED,

	// Code for the "channel soundModifier" command -----------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUNDMODIFIER__EXPLANATION,

	// Code for the "channel soundModifier details" command ---------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__EXPLANATION,

	// Code the name of the channel is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__CHANNEL_NOT_FOUND,

	// Code to display the name of the sound modifier
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__SOUND_MODIFIER_NAME,

	// Code to display the parameters of a sound modifier
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__SOUND_MODIFIER_PARAMETERS,

	// Code to display the name of a parameter
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_NAME,

	// Code to display the value of a parameter
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_VALUE,

	// Code to display the default value of a parameter
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_DEFAULT_VALUE,

	// Code to display the minimum value of a parameter
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_MINIMUM_VALUE,

	// Code to display the maximum value of a parameter
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__PARAMETER_MAXIMUM_VALUE,

	// Code to display the details of a sound modifier associated to a channel
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__DETAILS__SOUND_MODIFIER_DETAILS,

	// Code for the "channel soundModifier set" command -------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__EXPLANATION,

	// Code when the channel name is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__CHANNEL_NOT_FOUND,

	// Code when the sound modifier name is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__SOUND_MODIFIER_NAME_IS_MISSING,

	// Code when the sound modifier does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__SOUND_MODIFIER_NOT_FOUND,

	// Code when the sound modifier has been set
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__SET__SOUND_MODIFIER_SET,

	// Code for the "channel soundModifier modify" command ----------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__EXPLANATION,

	// Code when the name of the channel is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__CHANNEL_NOT_FOUND,

	// Code when the name of the parameter is missing
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__PARAMETER_NAME_IS_MISSING,

	// Code when the parameter does not exist
	MINECRAFT__MUMBLE_SERVER_CL__CHANNEL__SOUND_MODIFIER__MODIFY__PARAMETER_NOT_FOUND,

	// Code for the "parameter" command -----------------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__ROOT__EXPLANATION,

	// Code for the "parameter value" command -----------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__VALUE__EXPLANATION,

	// Code when the value is missing
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__VALUE__VALUE_IS_MISSING,

	// Code when the value has a bad format
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__VALUE__VALUE_BAD_FORMAT,

	// Code when the value is out of range
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__VALUE__VALUE_OUT_OF_RANGE,

	// Code when the parameter has been set
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__VALUE__VALUE_SET,

	// Code for the "parameter minValue" command -----------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MIN_VALUE__EXPLANATION,

	// Code when the minimum value is missing
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MIN_VALUE__VALUE_IS_MISSING,

	// Code when the minimum value has a bad format
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MIN_VALUE__VALUE_BAD_FORMAT,

	// Code when the minimum value has been set
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MIN_VALUE__VALUE_SET,

	// Code for the "parameter maxValue" command -----------------------------------------------------
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__EXPLANATION,

	// Code when the maximum value is missing
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__VALUE_IS_MISSING,

	// Code when the maximum value has a bad format
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__VALUE_BAD_FORMAT,

	// Code when the maximum value has been set
	MINECRAFT__MUMBLE_SERVER_CL__PARAMETER__MAX_VALUE__VALUE_SET,

	;

	private IPlayerGroup group;

	private EMumbleServerCode() {
		this(PlayerGroup.OPERATORS);
	}

	private EMumbleServerCode(IPlayerGroup group) {
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
