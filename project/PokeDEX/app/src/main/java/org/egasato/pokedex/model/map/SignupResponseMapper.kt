package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.SignupResponse
import org.egasato.pokedex.model.dto.NetworkSignupResponse

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = SignupResponseMapper::class.java.canonicalName

/**
 * Maps a sign-up response as a DTO network model to a domain model.
 *
 * @author Esaú García Sánchez-Torija
 */
object SignupResponseMapper : Mapper<NetworkSignupResponse, SignupResponse> {

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
	override fun map(input: NetworkSignupResponse): SignupResponse {
		logger.event("Mapping a NetworkSignupResponse into a SignupResponse")
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
