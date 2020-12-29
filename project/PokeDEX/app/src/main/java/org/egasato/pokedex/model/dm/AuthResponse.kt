package org.egasato.pokedex.model.dm

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
	val hasError
		get() = code != 0

	/** Checks if the response has no error. */
	val hasSuccess
		get() = code == 0

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
