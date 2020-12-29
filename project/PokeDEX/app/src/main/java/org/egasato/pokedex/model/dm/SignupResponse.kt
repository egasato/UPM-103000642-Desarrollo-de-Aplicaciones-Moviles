package org.egasato.pokedex.model.dm

/**
 * The model of a sign-up response as used by the client business logic.
 *
 * @property code    The response code.
 * @property message The response message.
 * @author Esaú García Sánchez-Torija
 */
data class SignupResponse(
	@JvmField override val code: Int,
	@JvmField override val message: String
) : AuthResponse() {

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

		/**
		 * Builds a sign-up response instance.
		 *
		 * @return The sign-up response instance.
		 */
		fun build() = SignupResponse(code, message)

	}

}
