package org.egasato.pokedex.model.dm

import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = LoginResponse::class.java.canonicalName

/**
 * The model of a login response as used by the client business logic.
 *
 * @property code    The response code.
 * @property message The response message.
 * @property token   The authentication token.
 * @author Esaú García Sánchez-Torija
 */
data class LoginResponse(
	@JvmField override val code: Int,
	@JvmField override val message: String,
	@JvmField val token: String
) : AuthResponse() {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/** Unnamed companion object exposing the static methods. */
	companion object {

		/**
		 * Creates an instance builder.
		 *
		 * @return The builder.
		 */
		@JvmStatic
		fun builder() = Builder()

	}

	/**
	 * An instance builder.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	class Builder : AuthResponse.Builder() {

		/** The authentication token. */
		@JvmField var token = ""

		/**
		 * Builds a login response instance.
		 *
		 * @return The login response instance.
		 */
		fun build() = LoginResponse(code, message, token)

	}

}
