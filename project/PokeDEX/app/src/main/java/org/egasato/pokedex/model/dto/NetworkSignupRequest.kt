package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName

/**
 * The model of a sign-up request as used by the repository.
 *
 * @property username The username.
 * @property password The password.
 * @property email    The email.
 * @author Esaú García Sánchez-Torija
 */
class NetworkSignupRequest(
	username: String,
	password: String,
	@JvmField @SerializedName("email") val email: String
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

		/** The email. */
		@JvmField var email = ""

		/**
		 * Builds a sign-up request instance.
		 *
		 * @return The sign-up request instance.
		 */
		fun build() = NetworkSignupRequest(username, password, email)

	}

}
