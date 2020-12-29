package org.egasato.pokedex.model.dm

/**
 * The model of a login request as used by the client business logic.
 *
 * @property username The username.
 * @property password The password.
 * @author Esaú García Sánchez-Torija
 */
data class LoginRequest(
	@JvmField override val username: String,
	@JvmField override val password: String
) : AuthRequest() {

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
	class Builder : AuthRequest.Builder() {

		/**
		 * Builds a sign-up request instance.
		 *
		 * @return The sign-up request instance.
		 */
		fun build() = LoginRequest(username, password)

	}

}
