package org.egasato.pokedex.lib.log

import mu.KLogger
import org.slf4j.Marker

/**
 * A [logger][Logger] that delegates to a [KLogger] and consumes a [configuration][LoggerConfig] object.
 *
 * @param logger The underlying logger.
 * @param config The logger configuration.
 * @see Logger
 * @see LoggerConfig
 * @see KLogger
 * @author Esaú García Sánchez-Torija
 */
internal class ConfigLogger(
	@JvmField private val logger: KLogger,
	@JvmField private val config: LoggerConfig
) : KLogger by logger, Logger {

	/////////////////////////////////////////////// OBJECT LIFECYCLE ///////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `CYCLE` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	override fun cycle(msg: () -> Any?) = if (config.isCycleEnabled) super.cycle(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `CYCLE` category.
	 *
	 * @param msg The message string to be logged.
	 */
	override fun cycle(msg: String?) = if (config.isCycleEnabled) super.cycle(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `CYCLE` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun cycle(format: String?, arg: Any?) = if (config.isCycleEnabled) super.cycle(format, arg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `CYCLE` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun cycle(format: String?, arg1: Any?, arg2: Any?) = if (config.isCycleEnabled) super.cycle(format, arg1, arg2) else Unit

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
	override fun cycle(format: String?, vararg arguments: Any?) = if (config.isCycleEnabled) super.cycle(format, *arguments) else Unit

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `CYCLE` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	override fun cycle(msg: String?, t: Throwable?) = if (config.isCycleEnabled) super.cycle(msg, t) else Unit

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if the logger is enabled for the `DEBUG` level and `CYCLE` category, `false` otherwise.
	 */
	override fun cycle(marker: Marker?) = if (config.isCycleEnabled) super.cycle(marker) else false

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `CYCLE` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	override fun cycle(marker: Marker?, msg: String?) = if (config.isCycleEnabled) super.cycle(marker, msg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun cycle(marker: Marker?, format: String?, arg: Any?) = if (config.isCycleEnabled) super.cycle(marker, format, arg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun cycle(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = if (config.isCycleEnabled) super.cycle(marker, format, arg1, arg2) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	override fun cycle(marker: Marker?, format: String?, vararg arguments: Any?) = if (config.isCycleEnabled) super.cycle(marker, format, *arguments) else Unit

	/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `GETTER` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	override fun getter(msg: () -> Any?) = if (config.isGetterEnabled) super.getter(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `GETTER` category.
	 *
	 * @param msg The message string to be logged.
	 */
	override fun getter(msg: String?) = if (config.isGetterEnabled) super.getter(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `GETTER` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun getter(format: String?, arg: Any?) = if (config.isGetterEnabled) super.getter(format, arg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `GETTER` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun getter(format: String?, arg1: Any?, arg2: Any?) = if (config.isGetterEnabled) super.getter(format, arg1, arg2) else Unit

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
	override fun getter(format: String?, vararg arguments: Any?) = if (config.isGetterEnabled) super.getter(format, *arguments) else Unit

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `GETTER` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	override fun getter(msg: String?, t: Throwable?) = if (config.isGetterEnabled) super.getter(msg, t) else Unit

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if the logger is enabled for the `DEBUG` level and `GETTER` category, `false` otherwise.
	 */
	override fun getter(marker: Marker?) = if (config.isGetterEnabled) super.getter(marker) else false

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `GETTER` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	override fun getter(marker: Marker?, msg: String?) = if (config.isGetterEnabled) super.getter(marker, msg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun getter(marker: Marker?, format: String?, arg: Any?) = if (config.isGetterEnabled) super.getter(marker, format, arg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun getter(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = if (config.isGetterEnabled) super.getter(marker, format, arg1, arg2) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	override fun getter(marker: Marker?, format: String?, vararg arguments: Any?) = if (config.isGetterEnabled) super.getter(marker, format, *arguments) else Unit

	/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `SETTER` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	override fun setter(msg: () -> Any?) = if (config.isSetterEnabled) super.setter(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `SETTER` category.
	 *
	 * @param msg The message string to be logged.
	 */
	override fun setter(msg: String?) = if (config.isSetterEnabled) super.setter(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `SETTER` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun setter(format: String?, arg: Any?) = if (config.isSetterEnabled) super.setter(format, arg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `SETTER` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun setter(format: String?, arg1: Any?, arg2: Any?) = if (config.isSetterEnabled) super.setter(format, arg1, arg2) else Unit

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
	override fun setter(format: String?, vararg arguments: Any?) = if (config.isSetterEnabled) super.setter(format, *arguments) else Unit

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `SETTER` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	override fun setter(msg: String?, t: Throwable?) = if (config.isSetterEnabled) super.setter(msg, t) else Unit

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if the logger is enabled for the `DEBUG` level and `SETTER` category, `false` otherwise.
	 */
	override fun setter(marker: Marker?) = if (config.isSetterEnabled) super.setter(marker) else false

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `SETTER` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	override fun setter(marker: Marker?, msg: String?) = if (config.isSetterEnabled) super.setter(marker, msg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun setter(marker: Marker?, format: String?, arg: Any?) = if (config.isSetterEnabled) super.setter(marker, format, arg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun setter(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = if (config.isSetterEnabled) super.setter(marker, format, arg1, arg2) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	override fun setter(marker: Marker?, format: String?, vararg arguments: Any?) = if (config.isSetterEnabled) super.setter(marker, format, *arguments) else Unit

	////////////////////////////////////////////// ANDROID LIFECYCLE ///////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `ANDROID` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	override fun android(msg: () -> Any?) = if (config.isAndroidEnabled) super.android(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `ANDROID` category.
	 *
	 * @param msg The message string to be logged.
	 */
	override fun android(msg: String?) = if (config.isAndroidEnabled) super.android(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `ANDROID` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun android(format: String?, arg: Any?) = if (config.isAndroidEnabled) super.android(format, arg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `ANDROID` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun android(format: String?, arg1: Any?, arg2: Any?) = if (config.isAndroidEnabled) super.android(format, arg1, arg2) else Unit

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
	override fun android(format: String?, vararg arguments: Any?) = if (config.isAndroidEnabled) super.android(format, *arguments) else Unit

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `ANDROID` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	override fun android(msg: String?, t: Throwable?) = if (config.isAndroidEnabled) super.android(msg, t) else Unit

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if the logger is enabled for the `DEBUG` level and `ANDROID` category, `false` otherwise.
	 */
	override fun android(marker: Marker?) = if (config.isAndroidEnabled) super.android(marker) else false

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `ANDROID` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	override fun android(marker: Marker?, msg: String?) = if (config.isAndroidEnabled) super.android(marker, msg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun android(marker: Marker?, format: String?, arg: Any?) = if (config.isAndroidEnabled) super.android(marker, format, arg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun android(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = if (config.isAndroidEnabled) super.android(marker, format, arg1, arg2) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	override fun android(marker: Marker?, format: String?, vararg arguments: Any?) = if (config.isAndroidEnabled) super.android(marker, format, *arguments) else Unit

	//////////////////////////////////////////////////// EVENTS ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `EVENT` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	override fun event(msg: () -> Any?) = if (config.isEventEnabled) super.event(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `EVENT` category.
	 *
	 * @param msg The message string to be logged.
	 */
	override fun event(msg: String?) = if (config.isEventEnabled) super.event(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `EVENT` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun event(format: String?, arg: Any?) = if (config.isEventEnabled) super.event(format, arg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `EVENT` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun event(format: String?, arg1: Any?, arg2: Any?) = if (config.isEventEnabled) super.event(format, arg1, arg2) else Unit

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
	override fun event(format: String?, vararg arguments: Any?) = if (config.isEventEnabled) super.event(format, *arguments) else Unit

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `EVENT` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	override fun event(msg: String?, t: Throwable?) = if (config.isEventEnabled) super.event(msg, t) else Unit

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if the logger is enabled for the `DEBUG` level and `EVENT` category, `false` otherwise.
	 */
	override fun event(marker: Marker?) = if (config.isEventEnabled) super.event(marker) else false

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `EVENT` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	override fun event(marker: Marker?, msg: String?) = if (config.isEventEnabled) super.event(marker, msg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun event(marker: Marker?, format: String?, arg: Any?) = if (config.isEventEnabled) super.event(marker, format, arg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun event(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = if (config.isEventEnabled) super.event(marker, format, arg1, arg2) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	override fun event(marker: Marker?, format: String?, vararg arguments: Any?) = if (config.isEventEnabled) super.event(marker, format, *arguments) else Unit

	/////////////////////////////////////////////////// DRAWING ////////////////////////////////////////////////////

	/**
	 * Log a lazily-created message at the `DEBUG` level and `DRAW` category.
	 *
	 * @param msg The lazily-created message string to be logged.
	 */
	override fun draw(msg: () -> Any?) = if (config.isDrawEnabled) super.draw(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `DRAW` category.
	 *
	 * @param msg The message string to be logged.
	 */
	override fun draw(msg: String?) = if (config.isDrawEnabled) super.draw(msg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `DRAW` category according to the specified format and argument.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun draw(format: String?, arg: Any?) = if (config.isDrawEnabled) super.draw(format, arg) else Unit

	/**
	 * Log a message at the `DEBUG` level and `DRAW` category according to the specified format and arguments.
	 *
	 * This form avoids superfluous object creation when the logger is disabled for the `DEBUG` level.
	 *
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun draw(format: String?, arg1: Any?, arg2: Any?) = if (config.isDrawEnabled) super.draw(format, arg1, arg2) else Unit

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
	override fun draw(format: String?, vararg arguments: Any?) = if (config.isDrawEnabled) super.draw(format, *arguments) else Unit

	/**
	 * Log an exception (throwable) at the `DEBUG` level and `DRAW` category with an accompanying message.
	 *
	 * @param msg The message accompanying the exception.
	 * @param t   The exception (throwable) to log.
	 */
	override fun draw(msg: String?, t: Throwable?) = if (config.isDrawEnabled) super.draw(msg, t) else Unit

	/**
	 * Similar to [isDebugEnabled] method except that the marker data is also taken into account.
	 *
	 * @param marker The marker data to take into consideration.
	 * @return `true` if the logger is enabled for the `DEBUG` level and `DRAW` category, `false` otherwise.
	 */
	override fun draw(marker: Marker?) = if (config.isDrawEnabled) super.draw(marker) else false

	/**
	 * Log a message with the specific [marker][Marker] at the `DEBUG` level and `DRAW` category.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param msg    The message string to be logged.
	 */
	override fun draw(marker: Marker?, msg: String?) = if (config.isDrawEnabled) super.draw(marker, msg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg    The argument.
	 */
	override fun draw(marker: Marker?, format: String?, arg: Any?) = if (config.isDrawEnabled) super.draw(marker, format, arg) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data specific to this log statement.
	 * @param format The format string.
	 * @param arg1   The first argument.
	 * @param arg2   The second argument.
	 */
	override fun draw(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = if (config.isDrawEnabled) super.draw(marker, format, arg1, arg2) else Unit

	/**
	 * This method is similar to [debug] method except that the marker data is also taken into consideration.
	 *
	 * @param marker    The marker data specific to this log statement.
	 * @param format    The format string.
	 * @param arguments A list of 3 or more arguments.
	 */
	override fun draw(marker: Marker?, format: String?, vararg arguments: Any?) = if (config.isDrawEnabled) super.draw(marker, format, *arguments) else Unit

}
