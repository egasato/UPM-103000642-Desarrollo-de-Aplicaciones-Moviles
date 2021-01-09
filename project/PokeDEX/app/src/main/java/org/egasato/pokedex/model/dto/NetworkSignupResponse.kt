package org.egasato.pokedex.model.dto

import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = NetworkSignupResponse::class.java.canonicalName

/**
 * The model of a sign-up response as used by the repository.
 *
 * @property code    The code that identifies the response type.
 * @property message The message status.
 * @author Esaú García Sánchez-Torija
 */
class NetworkSignupResponse(code: Int, message: String) : NetworkAuthResponse(code, message) {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

}
