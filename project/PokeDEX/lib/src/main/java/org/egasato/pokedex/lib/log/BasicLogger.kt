package org.egasato.pokedex.lib.log

import mu.KLogger

/**
 * A [logger][Logger] that delegates to a [KLogger] instance.
 *
 * @param logger The underlying logger.
 * @see Logger
 * @see KLogger
 * @author Esaú García Sánchez-Torija
 */
internal class BasicLogger(@JvmField private val logger: KLogger) : KLogger by logger, Logger
