package org.egasato.pokedex.model.map

import org.egasato.pokedex.model.dm.SignupResponse
import org.egasato.pokedex.model.dto.NetworkSignupResponse

/**
 * Maps a sign-up response as a DTO network model to a domain model.
 *
 * @author Esaú García Sánchez-Torija
 */
object SignupResponseMapper : Mapper<NetworkSignupResponse, SignupResponse> {

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	override fun map(input: NetworkSignupResponse): SignupResponse {
		return SignupResponse.builder().apply {
			code = input.code
			message = input.message
		}.build()
	}

}

/**
 * Maps the DTO network model to a domain model.
 *
 * @return The domain model.
 */
fun NetworkSignupResponse.mapToDomain() = SignupResponseMapper.map(this)
