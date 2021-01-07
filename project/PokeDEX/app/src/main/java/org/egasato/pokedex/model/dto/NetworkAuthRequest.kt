package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName

/**
 * The model of an authentication request as used by the repository network logic.
 *
 * @property username The username.
 * @property password The password.
 * @author Esaú García Sánchez-Torija
 */
abstract class NetworkAuthRequest(
	@JvmField @SerializedName("username") val username: String,
	@JvmField @SerializedName("password") val password: String
) {

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
