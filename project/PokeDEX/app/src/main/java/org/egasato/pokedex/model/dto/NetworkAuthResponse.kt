package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName

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

	/** Checks if the response has an error. */
	val hasError
		get() = code != 0

	/** Checks if the response has no error. */
	val hasSuccess
		get() = code == 0

}
