package org.egasato.pokedex.lib.log

/**
 * The runtime configuration of a [logger][Logger].
 *
 * @see Logger
 * @author Esaú García Sánchez-Torija
 */
interface LoggerConfig {

	/**
	 * Indicates whether the logging messages of the `CYCLE` category should be discarded or not.
	 *
	 * @see Logger.cycle
	 */
	@Suppress("INAPPLICABLE_JVM_NAME")
	@get:JvmName("isCycleEnabled")
	val isCycleEnabled: Boolean

	/**
	 * Indicates whether the logging messages of the `GETTER` category should be discarded or not.
	 *
	 * @see Logger.getter
	 */
	@Suppress("INAPPLICABLE_JVM_NAME")
	@get:JvmName("isGetterEnabled")
	val isGetterEnabled: Boolean

	/**
	 * Indicates whether the logging messages of the `SETTER` category should be discarded or not.
	 *
	 * @see Logger.setter
	 */
	@Suppress("INAPPLICABLE_JVM_NAME")
	@get:JvmName("isSetterEnabled")
	val isSetterEnabled: Boolean

	/**
	 * Indicates whether the logging messages of the `ANDROID` category should be discarded or not.
	 *
	 * @see Logger.android
	 */
	@Suppress("INAPPLICABLE_JVM_NAME")
	@get:JvmName("isAndroidEnabled")
	val isAndroidEnabled: Boolean

	/**
	 * Indicates whether the logging messages of the `EVENT` category should be discarded or not.
	 *
	 * @see Logger.event
	 */
	@Suppress("INAPPLICABLE_JVM_NAME")
	@get:JvmName("isEventEnabled")
	val isEventEnabled: Boolean

	/**
	 * Indicates whether the logging messages of the `DRAW` category should be discarded or not.
	 *
	 * @see Logger.draw
	 */
	@Suppress("INAPPLICABLE_JVM_NAME")
	@get:JvmName("isDrawEnabled")
	val isDrawEnabled: Boolean

}
