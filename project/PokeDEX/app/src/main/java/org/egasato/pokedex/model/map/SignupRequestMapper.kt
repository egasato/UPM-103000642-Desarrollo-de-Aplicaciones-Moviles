package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.SignupRequest
import org.egasato.pokedex.model.dto.NetworkSignupRequest

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = SignupRequestMapper::class.java.canonicalName

/**
 * Maps a sign-up request as a domain model to a DTO network model.
 *
 * @author Esaú García Sánchez-Torija
 */
object SignupRequestMapper : Mapper<SignupRequest, NetworkSignupRequest> {

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
	override fun map(input: SignupRequest): NetworkSignupRequest {
		logger.event("Mapping a SignupRequest into a NetworkSignupRequest")
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
