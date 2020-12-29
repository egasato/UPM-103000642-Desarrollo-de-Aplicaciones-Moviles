package org.egasato.pokedex.model.dm

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
