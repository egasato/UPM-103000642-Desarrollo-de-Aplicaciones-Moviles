package org.egasato.pokedex.model.dto

/**
 * The model of a sign-up response as used by the repository.
 *
 * @property code    The code that identifies the response type.
 * @property message The message status.
 * @author Esaú García Sánchez-Torija
 */
class NetworkSignupResponse(code: Int, message: String) : NetworkAuthResponse(code, message)
