package org.egasato.pokedex.model.dto

/**
 * The model of a login request as used by the repository.
 *
 * @property username The username.
 * @property password The password.
 * @author Esaú García Sánchez-Torija
 */
class NetworkLoginRequest(
	username: String,
	password: String
) : NetworkAuthRequest(username, password) {

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
	class Builder : NetworkAuthRequest.Builder() {

		/**
		 * Builds a sign-up request instance.
		 *
		 * @return The sign-up request instance.
		 */
		fun build() = NetworkLoginRequest(username, password)

	}

}
