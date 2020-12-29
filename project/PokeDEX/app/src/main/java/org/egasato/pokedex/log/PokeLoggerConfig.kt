package org.egasato.pokedex.log

import org.egasato.pokedex.BuildConfig
import org.egasato.pokedex.lib.log.Logger
import org.egasato.pokedex.lib.log.LoggerConfig

/**
 * An Android-aware [logger][Logger] [configuration][LoggerConfig] object.
 *
 * Uses the [build configuration file/class][BuildConfig] to determine if the logging calls will be discarded or not; if
 * the application is debuggable and the log category is enabled, the call will succeed.
 *
 * @see Logger
 * @see LoggerConfig
 * @see BuildConfig
 * @author Esaú García Sánchez-Torija
 */
object PokeLoggerConfig : LoggerConfig {

	/** Decides if the [Logger.cycle] calls should log or not. */
	override val isCycleEnabled = BuildConfig.DEBUG && BuildConfig.LOG_CYCLE

	/** Decides if the [Logger.getter] calls should log or not. */
	override val isGetterEnabled = BuildConfig.DEBUG && BuildConfig.LOG_GETTER

	/** Decides if the [Logger.setter] calls should log or not. */
	override val isSetterEnabled = BuildConfig.DEBUG && BuildConfig.LOG_SETTER

	/** Decides if the [Logger.android] calls should log or not. */
	override val isAndroidEnabled = BuildConfig.DEBUG && BuildConfig.LOG_ANDROID

	/** Decides if the [Logger.event] calls should log or not. */
	override val isEventEnabled = BuildConfig.DEBUG && BuildConfig.LOG_EVENT

	/** Decides if the [Logger.draw] calls should log or not. */
	override val isDrawEnabled = BuildConfig.DEBUG && BuildConfig.LOG_DRAW

}
