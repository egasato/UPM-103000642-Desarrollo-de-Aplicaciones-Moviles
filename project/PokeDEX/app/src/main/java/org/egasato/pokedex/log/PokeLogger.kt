package org.egasato.pokedex.log

import android.os.Build
import mu.KotlinLogging
import org.egasato.pokedex.BuildConfig
import org.egasato.pokedex.lib.log.Logger
import org.slf4j.impl.HandroidLoggerAdapter

/**
 * An Android-aware [Logger] factory.
 *
 * @see Logger
 * @see BuildConfig
 * @author Esaú García Sánchez-Torija
 */
interface PokeLogger : Logger {

	/** The companion object used to expose the static constructors.  */
	companion object {

		/**
		 * A static constructor with the same signature as [KotlinLogging]'s.
		 *
		 * @param func The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(func: () -> Unit) = when (BuildConfig.BUILD_TYPE) {
			"debug" -> Logger.logger(func = func, config = PokeLoggerConfig)
			else -> Logger.logger(func)
		}

		/**
		 * A static constructor with the same signature as [KotlinLogging]'s.
		 *
		 * @param name The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(name: String) = when (BuildConfig.BUILD_TYPE) {
			"debug" -> Logger.logger(name = name, config = PokeLoggerConfig)
			else -> Logger.logger(name)
		}

		/**
		 * A static constructor with the same signature as [KotlinLogging]'s.
		 *
		 * @param underlyingLogger The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(underlyingLogger: Logger) = when (BuildConfig.BUILD_TYPE) {
			"debug" -> Logger.logger(underlyingLogger = underlyingLogger, config = PokeLoggerConfig)
			else -> Logger.logger(underlyingLogger)
		}

		/** Initializes the Android logger adapter. */
		init {
			HandroidLoggerAdapter.DEBUG             = BuildConfig.DEBUG
			HandroidLoggerAdapter.ANDROID_API_LEVEL = Build.VERSION.SDK_INT
			HandroidLoggerAdapter.APP_NAME          = BuildConfig.APP_NAME
		}

	}

}
