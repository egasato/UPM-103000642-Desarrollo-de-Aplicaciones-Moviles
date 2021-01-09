package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = NetworkAuthResponse::class.java.canonicalName

/**
 * The model of an authentication response as used by the repository network logic.
 *
 * @property code    The code that identifies the response type.
 * @property message The message status.
 * @author Esaú García Sánchez-Torija
 */
abstract class NetworkAuthResponse(
	@JvmField @SerializedName("code") val code: Int,
	@JvmField @SerializedName("message") val message: String
) {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

}
