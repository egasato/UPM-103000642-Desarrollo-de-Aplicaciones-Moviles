package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.LoginRequest
import org.egasato.pokedex.model.dto.NetworkLoginRequest

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = LoginRequestMapper::class.java.canonicalName

/**
 * Maps a login request as a domain model to a DTO network model.
 *
 * @author Esaú García Sánchez-Torija
 */
object LoginRequestMapper : Mapper<LoginRequest, NetworkLoginRequest> {

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
	override fun map(input: LoginRequest): NetworkLoginRequest {
		logger.event("Mapping a LoginRequest into a NetworkLoginRequest")
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
