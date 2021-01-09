package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = NetworkAuthRequest::class.java.canonicalName

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

		/** The username. */
		@JvmField var username = ""

		/** The password. */
		@JvmField var password = ""

	}

}
