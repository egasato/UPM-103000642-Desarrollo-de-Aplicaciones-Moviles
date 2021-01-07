package org.egasato.pokedex.model.map

import org.egasato.pokedex.model.dm.LoginRequest
import org.egasato.pokedex.model.dto.NetworkLoginRequest

/**
 * Maps a login request as a domain model to a DTO network model.
 *
 * @author Esaú García Sánchez-Torija
 */
object LoginRequestMapper : Mapper<LoginRequest, NetworkLoginRequest> {

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	override fun map(input: LoginRequest): NetworkLoginRequest {
		return NetworkLoginRequest.builder().apply {
			username = input.username
			password = input.password
		}.build()
	}

}

/**
 * Maps the domain model to a DTO network model.
 *
 * @return The DTO network model.
 */
fun LoginRequest.mapToNetwork() = LoginRequestMapper.map(this)
