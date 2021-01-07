package org.egasato.pokedex.model.map

import org.egasato.pokedex.model.dm.LoginResponse
import org.egasato.pokedex.model.dto.NetworkLoginResponse

/**
 * Maps a login response as a DTO network model to a domain model.
 *
 * @author Esaú García Sánchez-Torija
 */
object LoginResponseMapper : Mapper<NetworkLoginResponse, LoginResponse> {

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	override fun map(input: NetworkLoginResponse): LoginResponse {
		return LoginResponse.builder().apply {
			code = input.code
			message = input.message
			if (input.token != null) token = input.token
		}.build()
	}

}

/**
 * Maps the DTO network model to a domain model.
 *
 * @return The domain model.
 */
fun NetworkLoginResponse.mapToDomain() = LoginResponseMapper.map(this)
