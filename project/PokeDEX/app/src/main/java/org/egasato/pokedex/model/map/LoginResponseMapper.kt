package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.LoginResponse
import org.egasato.pokedex.model.dto.NetworkLoginResponse

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = LoginResponseMapper::class.java.canonicalName

/**
 * Maps a login response as a DTO network model to a domain model.
 *
 * @author Esaú García Sánchez-Torija
 */
object LoginResponseMapper : Mapper<NetworkLoginResponse, LoginResponse> {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	override fun map(input: NetworkLoginResponse): LoginResponse {
		logger.event("Mapping a NetworkLoginResponse into a LoginResponse")
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
