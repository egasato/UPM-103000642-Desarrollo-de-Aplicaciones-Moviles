package org.egasato.pokedex.lib.log

import mu.KLogger
import mu.KotlinLogging
import org.slf4j.Marker

/** The prefix used with the [Logger.cycle] messages. */
var LOGGER_PREFIX_CYCLE = "[cycle] "

/** The prefix used with the [Logger.getter] messages. */
var LOGGER_PREFIX_GETTER = "[getter] "

/** The prefix used with the [Logger.setter] messages. */
var LOGGER_PREFIX_SETTER = "[setter] "

/** The prefix used with the [Logger.android] messages. */
var LOGGER_PREFIX_ANDROID = "[android] "

/** The prefix used with the [Logger.event] messages. */
var LOGGER_PREFIX_EVENT = "[event] "

/** The prefix used with the [Logger.draw] messages. */
var LOGGER_PREFIX_DRAW = "[draw] "

/**
 * A more expressive [KLogger] interface.
 *
 * Exposes methods whose default implementation delegates to [debug] with the same method signature:
 * - [cycle]: for messages that show information about the lifecycle of an object.
 * - [getter]: for messages that show information of a getter method.
 * - [setter]: for messages that show information of a setter method.
 * - [android]: for messages that show information about the Android-related lifecycle of an object.
 * - [event]: for messages that show information about asynchronous (or reactive) events.
 * - [draw]: for messages that show information about the layout and drawing of the UI.
 *
 * To make these messages easier to find, it applies a prefix to the messages using the constants [LOGGER_PREFIX_CYCLE],
 * [LOGGER_PREFIX_GETTER], [LOGGER_PREFIX_SETTER], [LOGGER_PREFIX_ANDROID], [LOGGER_PREFIX_EVENT] and
 * [LOGGER_PREFIX_DRAW].
 *
 * @see KLogger
 * @author Esaú García Sánchez-Torija
 */
interface Logger : KLogger {

	///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////

	/** The companion object used to expose the static constructors.  */
	companion object {

		/**
		 * Creates an instance that delegates to a [KLogger].
		 *
		 * @param logger A [KLogger] instance.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(logger: KLogger): Logger = BasicLogger(logger)

		/**
		 * Creates a configured instance that delegates to a [KLogger].
		 *
		 * @param logger A [KLogger] instance.
		 * @param config  A [configuration][LoggerConfig] instance.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(logger: KLogger, config: LoggerConfig): Logger = ConfigLogger(logger, config)

		/**
		 * A static constructor with the same signature as [KotlinLogging.logger].
		 *
		 * @param func The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(func: () -> Unit): Logger = logger(KotlinLogging.logger(func))

		/**
		 * A static constructor with the same signature as [KotlinLogging.logger] but with a
		 * [configuration][LoggerConfig] object.
		 *
		 * @param config A [configuration][LoggerConfig] instance.
		 * @param func   The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(config: LoggerConfig, func: () -> Unit): Logger = logger(KotlinLogging.logger(func), config)

		/**
		 * A static constructor with the same signature as [KotlinLogging.logger].
		 *
		 * @param name The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(name: String): Logger = logger(KotlinLogging.logger(name))

		/**
		 * A static constructor with the same signature as [KotlinLogging.logger] but with a
		 * [configuration][LoggerConfig] object.
		 *
		 * @param config A [configuration][LoggerConfig] instance.
		 * @param name  The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(config: LoggerConfig, name: String): Logger = logger(KotlinLogging.logger(name), config)

		/**
		 * A static constructor with the same signature as [KotlinLogging.logger].
		 *
		 * @param underlyingLogger The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(underlyingLogger: Logger): Logger = logger(KotlinLogging.logger(underlyingLogger))

		/**
		 * A static constructor with the same signature as [KotlinLogging.logger] but with a
		 * [configuration][LoggerConfig] object.
		 *
		 * @param config           A [configuration][LoggerConfig] instance.
		 * @param underlyingLogger The first parameter.
		 * @see KotlinLogging.logger
		 */
		@JvmStatic
		fun logger(config: LoggerConfig, underlyingLogger: Logger): Logger = logger(KotlinLogging.logger(underlyingLogger), config)

	}

	/////////////////////////////////////////////// OBJECT LIFECYCLE ///////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `CYCLE` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	fun cycle(msg: () -> Any?) = debug { precycle(msg) }

	/**
	 * Log a message at the `DEBUG` level and `CYCLE` category.
	 *
	 * @param msg The message string to be logged.
	 */
	fun cycle(msg: String?) = debug(precycle(msg))

	/**
	 * Log a message at the `DEBUG` level and `CYCLE` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun cycle(format: String?, arg: Any?) = debug(precycle(format), arg)

	/**
	 * Log a message at the `DEBUG` level and `CYCLE` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun cycle(format: String?, arg1: Any?, arg2: Any?) = debug(precycle(format), arg1, arg2)

	/**
	 * Log a message at the `DEBUG` level and `CYCLE` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous string concatenation when the logger is disabled for the `DEBUG` level. However,
	 * this variant incurs the hidden (and relatively small) cost of creating an `Array<Any>?` before invoking the
	 * method, even if this logger is disabled for `DEBUG`. The variants taking one and two arguments exist solely
	 * in order to avoid this hidden cost.
	 *
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun cycle(format: String?, vararg arguments: Any?) = debug(precycle(format), *arguments)

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `CYCLE` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	fun cycle(msg: String?, t: Throwable?) = debug(precycle(msg), t)

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if the logger is enabled for the `DEBUG` level and `CYCLE` category, `false` otherwise.
	 */
	fun cycle(marker: Marker?) = isDebugEnabled(marker)

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `CYCLE` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	fun cycle(marker: Marker?, msg: String?) = debug(marker, precycle(msg))

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun cycle(marker: Marker?, format: String?, arg: Any?) = debug(marker, precycle(format), arg)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun cycle(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = debug(marker, precycle(format), arg1, arg2)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun cycle(marker: Marker?, format: String?, vararg arguments: Any?) = debug(marker, precycle(format), *arguments)

	/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `GETTER` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	fun getter(msg: () -> Any?) = debug(pregetter(msg))

	/**
	 * Log a message at the `DEBUG` level and `GETTER` category.
	 *
	 * @param msg The message string to be logged.
	 */
	fun getter(msg: String?) = debug(pregetter(msg))

	/**
	 * Log a message at the `DEBUG` level and `GETTER` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun getter(format: String?, arg: Any?) = debug(pregetter(format), arg)

	/**
	 * Log a message at the `DEBUG` level and `GETTER` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun getter(format: String?, arg1: Any?, arg2: Any?) = debug(pregetter(format), arg1, arg2)

	/**
	 * Log a message at the `DEBUG` level and `GETTER` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous string concatenation when the logger is disabled for the `DEBUG` level. However,
	 * this variant incurs the hidden (and relatively small) cost of creating an `Array<Any>?` before invoking the
	 * method, even if this logger is disabled for `DEBUG`. The variants taking one and two arguments exist solely
	 * in order to avoid this hidden cost.
	 *
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun getter(format: String?, vararg arguments: Any?) = debug(pregetter(format), *arguments)

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `GETTER` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	fun getter(msg: String?, t: Throwable?) = debug(pregetter(msg), t)

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration
	 * @return `true` if this logger is enabled for the `DEBUG` level and `GETTER` category, `false` otherwise.
	 */
	fun getter(marker: Marker?) = isDebugEnabled(marker)

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `GETTER` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	fun getter(marker: Marker?, msg: String?) = debug(marker, pregetter(msg))

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun getter(marker: Marker?, format: String?, arg: Any?) = debug(marker, pregetter(format), arg)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun getter(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = debug(marker, pregetter(format), arg1, arg2)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun getter(marker: Marker?, format: String?, vararg arguments: Any?) = debug(marker, pregetter(format), *arguments)

	/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `SETTER` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	fun setter(msg: () -> Any?) = debug(presetter(msg))

	/**
	 * Log a message at the `DEBUG` level and `SETTER` category.
	 *
	 * @param msg The message string to be logged.
	 */
	fun setter(msg: String?) = debug(presetter(msg))

	/**
	 * Log a message at the `DEBUG` level and `SETTER` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun setter(format: String?, arg: Any?) = debug(presetter(format), arg)

	/**
	 * Log a message at the `DEBUG` level and `SETTER` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun setter(format: String?, arg1: Any?, arg2: Any?) = debug(presetter(format), arg1, arg2)

	/**
	 * Log a message at the `DEBUG` level and `SETTER` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous string concatenation when the logger is disabled for the `DEBUG` level. However,
	 * this variant incurs the hidden (and relatively small) cost of creating an `Array<Any>?` before invoking the
	 * method, even if this logger is disabled for `DEBUG`. The variants taking one and two arguments exist solely
	 * in order to avoid this hidden cost.
	 *
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun setter(format: String?, vararg arguments: Any?) = debug(presetter(format), *arguments)

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `SETTER` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	fun setter(msg: String?, t: Throwable?) = debug(presetter(msg), t)

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if this logger is enabled for the `DEBUG` level and `SETTER` category, `false` otherwise.
	 */
	fun setter(marker: Marker?) = isDebugEnabled(marker)

	/**
	 * Log a message with the specific [Marker] at the `DEBUG` level and `SETTER` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	fun setter(marker: Marker?, msg: String?) = debug(marker, presetter(msg))

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun setter(marker: Marker?, format: String?, arg: Any?) = debug(marker, presetter(format), arg)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun setter(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = debug(marker, presetter(format), arg1, arg2)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun setter(marker: Marker?, format: String?, vararg arguments: Any?) = debug(marker, presetter(format), *arguments)

	////////////////////////////////////////////// ANDROID LIFECYCLE ///////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `ANDROID` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	fun android(msg: () -> Any?) = debug(preandroid(msg))

	/**
	 * Log a message at the `DEBUG` level and `ANDROID` category.
	 *
	 * @param msg The message string to be logged.
	 */
	fun android(msg: String?) = debug(preandroid(msg))

	/**
	 * Log a message at the `DEBUG` level and `ANDROID` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun android(format: String?, arg: Any?) = debug(preandroid(format), arg)

	/**
	 * Log a message at the `DEBUG` level and `ANDROID` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun android(format: String?, arg1: Any?, arg2: Any?) = debug(preandroid(format), arg1, arg2)

	/**
	 * Log a message at the `DEBUG` level and `ANDROID` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous string concatenation when the logger is disabled for the `DEBUG` level. However,
	 * this variant incurs the hidden (and relatively small) cost of creating an `Array<Any>?` before invoking the
	 * method, even if this logger is disabled for `DEBUG`. The variants taking one and two arguments exist solely
	 * in order to avoid this hidden cost.
	 *
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun android(format: String?, vararg arguments: Any?) = debug(preandroid(format), *arguments)

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `ANDROID` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	fun android(msg: String?, t: Throwable?) = debug(preandroid(msg), t)

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if this logger is enabled for the `DEBUG` level and `ANDROID` category, `false` otherwise.
	 */
	fun android(marker: Marker?) = isDebugEnabled(marker)

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `ANDROID` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	fun android(marker: Marker?, msg: String?) = debug(marker, preandroid(msg))

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun android(marker: Marker?, format: String?, arg: Any?) = debug(marker, preandroid(format), arg)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun android(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = debug(marker, preandroid(format), arg1, arg2)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun android(marker: Marker?, format: String?, vararg arguments: Any?) = debug(marker, preandroid(format), *arguments)

	//////////////////////////////////////////////////// EVENTS ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `EVENT` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	fun event(msg: () -> Any?) = debug(preevent(msg))

	/**
	 * Log a message at the `DEBUG` level and `EVENT` category.
	 *
	 * @param msg The message string to be logged.
	 */
	fun event(msg: String?) = debug(preevent(msg))

	/**
	 * Log a message at the `DEBUG` level and `EVENT` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun event(format: String?, arg: Any?) = debug(preevent(format), arg)

	/**
	 * Log a message at the `DEBUG` level and `EVENT` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun event(format: String?, arg1: Any?, arg2: Any?) = debug(preevent(format), arg1, arg2)

	/**
	 * Log a message at the `DEBUG` level and `EVENT` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous string concatenation when the logger is disabled for the `DEBUG` level. However,
	 * this variant incurs the hidden (and relatively small) cost of creating an `Array<Any>?` before invoking the
	 * method, even if this logger is disabled for `DEBUG`. The variants taking one and two arguments exist solely
	 * in order to avoid this hidden cost.
	 *
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun event(format: String?, vararg arguments: Any?) = debug(preevent(format), *arguments)

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `EVENT` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	fun event(msg: String?, t: Throwable?) = debug(preevent(msg), t)

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if this logger is enabled for the `DEBUG` level and `EVENT` category, `false` otherwise.
	 */
	fun event(marker: Marker?) = isDebugEnabled(marker)

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `EVENT` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	fun event(marker: Marker?, msg: String?) = debug(marker, preevent(msg))

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun event(marker: Marker?, format: String?, arg: Any?) = debug(marker, preevent(format), arg)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun event(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = debug(marker, preevent(format), arg1, arg2)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun event(marker: Marker?, format: String?, vararg arguments: Any?) = debug(marker, preevent(format), *arguments)

	/////////////////////////////////////////////////// DRAWING ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `DRAW` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	fun draw(msg: () -> Any?) = debug(predraw(msg))

	/**
	 * Log a message at the `DEBUG` level and `DRAW` category.
	 *
	 * @param msg The message string to be logged.
	 */
	fun draw(msg: String?) = debug(predraw(msg))

	/**
	 * Log a message at the `DEBUG` level and `DRAW` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun draw(format: String?, arg: Any?) = debug(predraw(format), arg)

	/**
	 * Log a message at the `DEBUG` level and `DRAW` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun draw(format: String?, arg1: Any?, arg2: Any?) = debug(predraw(format), arg1, arg2)

	/**
	 * Log a message at the `DEBUG` level and `DRAW` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous string concatenation when the logger is disabled for the `DEBUG` level. However,
	 * this variant incurs the hidden (and relatively small) cost of creating an `Array<Any>?` before invoking the
	 * method, even if this logger is disabled for `DEBUG`. The variants taking one and two arguments exist solely
	 * in order to avoid this hidden cost.
	 *
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun draw(format: String?, vararg arguments: Any?) = debug(predraw(format), *arguments)

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `DRAW` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	fun draw(msg: String?, t: Throwable?) = debug(predraw(msg), t)

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if this logger is enabled for the `DEBUG` level and `DRAW` category, `false` otherwise.
	 */
	fun draw(marker: Marker?) = isDebugEnabled(marker)

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `DRAW` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	fun draw(marker: Marker?, msg: String?) = debug(marker, predraw(msg))

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	fun draw(marker: Marker?, format: String?, arg: Any?) = debug(marker, predraw(format), arg)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	fun draw(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = debug(marker, predraw(format), arg1, arg2)

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	fun draw(marker: Marker?, format: String?, vararg arguments: Any?) = debug(marker, predraw(format), *arguments)

}

////////////////////////////////////////////////////// EXTENSIONS //////////////////////////////////////////////////////

/**
 * Converts a lambda function into a string.
 *
 * The stringification process is done safely by catching any thrown exceptions during the lambda function invocation.
 *
 * @param lazy The lambda function.
 * @return The safe string.
 * @author Esaú García Sánchez-Torija
 */
@Suppress("NOTHING_TO_INLINE")
private inline fun toStringSafe(lazy: () -> Any?): String {
	return try {
		lazy().toString()
	} catch (e: Throwable) {
		"Log message invocation failed: $e"
	}
}

////////////////////////////////////////////////////// FUNCTIONS ///////////////////////////////////////////////////////

/**
 * Applies a prefix to a string.
 *
 * @param prefix The prefix.
 * @param str    The string.
 * @return The prefixed string.
 * @author Esaú García Sánchez-Torija
 */
private fun pre(prefix: String, str: String?) = "%s%s".format(prefix, str)

/**
 * Applies a prefix to a lazily-created string.
 *
 * @param prefix The prefix.
 * @param str    The lazily-created string.
 * @return The prefixed string.
 * @author Esaú García Sánchez-Torija
 */
private fun pre(prefix: String, str: () -> Any?) = pre(prefix = prefix, str = toStringSafe(str))

/**
 * Applies a prefix to a message string using [LOGGER_PREFIX_CYCLE] as prefix.
 *
 * @param msg The message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun precycle(msg: String?) = pre(prefix = LOGGER_PREFIX_CYCLE, str = msg)

/**
 * Applies a prefix to a lazy message string using [LOGGER_PREFIX_CYCLE] as prefix.
 *
 * @param msg The lazy message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun precycle(msg: () -> Any?) = pre(prefix = LOGGER_PREFIX_CYCLE, str = msg)

/**
 * Applies a prefix to a message string using [LOGGER_PREFIX_GETTER] as prefix.
 *
 * @param msg The message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun pregetter(msg: String?) = pre(prefix = LOGGER_PREFIX_GETTER, msg)

/**
 * Applies a prefix to a lazy message string using [LOGGER_PREFIX_GETTER] as prefix.
 *
 * @param msg The lazy message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun pregetter(msg: () -> Any?) = pre(prefix = LOGGER_PREFIX_GETTER, str = msg)

/**
 * Applies a prefix to a message string using [LOGGER_PREFIX_SETTER] as prefix.
 *
 * @param msg The message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun presetter(msg: String?) = pre(prefix = LOGGER_PREFIX_SETTER, str = msg)

/**
 * Applies a prefix to a lazy message string using [LOGGER_PREFIX_SETTER] as prefix.
 *
 * @param msg The lazy message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun presetter(msg: () -> Any?) = pre(prefix = LOGGER_PREFIX_SETTER, str = msg)

/**
 * Applies a prefix to a message string using [LOGGER_PREFIX_ANDROID] as prefix.
 *
 * @param msg The message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun preandroid(msg: String?) = pre(prefix = LOGGER_PREFIX_ANDROID, str = msg)

/**
 * Applies a prefix to a lazy message string using [LOGGER_PREFIX_ANDROID] as prefix.
 *
 * @param msg The lazy message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun preandroid(msg: () -> Any?) = pre(prefix = LOGGER_PREFIX_ANDROID, str = msg)

/**
 * Applies a prefix to a message string using [LOGGER_PREFIX_EVENT] as prefix.
 *
 * @param msg The message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun preevent(msg: String?) = pre(prefix = LOGGER_PREFIX_EVENT, str = msg)

/**
 * Applies a prefix to a lazy message string using [LOGGER_PREFIX_EVENT] as prefix.
 *
 * @param msg The lazy message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun preevent(msg: () -> Any?) = pre(prefix = LOGGER_PREFIX_EVENT, str = msg)

/**
 * Applies a prefix to a message string using [LOGGER_PREFIX_DRAW] as prefix.
 *
 * @param msg The message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun predraw(msg: String?) = pre(prefix = LOGGER_PREFIX_DRAW, str = msg)

/**
 * Applies a prefix to a lazy message string using [LOGGER_PREFIX_DRAW] as prefix.
 *
 * @param msg The lazy message string.
 * @return The prefixed message string.
 * @author Esaú García Sánchez-Torija
 */
private fun predraw(msg: () -> Any?) = pre(prefix = LOGGER_PREFIX_DRAW, str = msg)
