package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName

/**
 * The model of a login response as used by the repository.
 *
 * @property code    The code that identifies the response type.
 * @property message The message status.
 * @property token   The authentication token.
 * @author Esaú García Sánchez-Torija
 */
class NetworkLoginResponse(
	code: Int,
	message: String,
	@JvmField @SerializedName("token") val token: String?
) : NetworkAuthResponse(code, message)
