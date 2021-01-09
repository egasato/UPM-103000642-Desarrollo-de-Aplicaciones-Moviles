package org.egasato.pokedex.model.dm

import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = AuthRequest::class.java.canonicalName

/**
 * The model of an authentication request as used by the client business logic.
 *
 * @author Esaú García Sánchez-Torija
 */
abstract class AuthRequest {

	/** The username. */
	abstract val username: String

	/** The password. */
	abstract val password: String

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/**
	 * An abstract instance builder.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	abstract class Builder {

		/** The username. */
		@JvmField var username = ""

		/** The password. */
		@JvmField var password = ""

	}

}
