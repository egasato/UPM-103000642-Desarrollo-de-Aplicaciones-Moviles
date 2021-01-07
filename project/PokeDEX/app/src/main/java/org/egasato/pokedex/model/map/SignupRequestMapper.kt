package org.egasato.pokedex.model.map

import org.egasato.pokedex.model.dm.SignupRequest
import org.egasato.pokedex.model.dto.NetworkSignupRequest

/**
 * Maps a sign-up request as a domain model to a DTO network model.
 *
 * @author Esaú García Sánchez-Torija
 */
object SignupRequestMapper : Mapper<SignupRequest, NetworkSignupRequest> {

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	override fun map(input: SignupRequest): NetworkSignupRequest {
		return NetworkSignupRequest.builder().apply {
			username = input.username
			password = input.password
			email = input.email
		}.build()
	}

}

/**
 * Maps the domain model to a DTO network model.
 *
 * @return The DTO network model.
 */
fun SignupRequest.mapToNetwork() = SignupRequestMapper.map(this)
