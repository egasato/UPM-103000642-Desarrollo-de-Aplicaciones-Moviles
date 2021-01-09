package org.egasato.pokedex.model.dm

import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = AuthResponse::class.java.canonicalName

/**
 * The model of an authentication response as used by the client business logic.
 *
 * @author Esaú García Sánchez-Torija
 */
abstract class AuthResponse {

	/** The code that identifies the response type. */
	abstract val code: Int

	/** The message status. */
	abstract val message: String

	/** Checks if the response has an error. */
	val hasError: Boolean
		get() {
			logger.getter("Accessing the member \"hasError\"")
			return code != 0
		}

	/** Checks if the response has no error. */
	val hasSuccess: Boolean
		get() {
			logger.getter("Accessing the member \"hasSuccess\"")
			return code == 0
		}

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

		/** The response code. */
		@JvmField var code = 0

		/** The response message. */
		@JvmField var message = ""

	}

}
