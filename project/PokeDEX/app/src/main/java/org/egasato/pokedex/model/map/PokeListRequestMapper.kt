package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.PokeListRequest
import org.egasato.pokedex.model.dto.NetworkPokeListRequest

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeListRequestMapper::class.java.canonicalName

/**
 * Maps a Pokémon list request as a domain model to a DTO network model.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeListRequestMapper : Mapper<PokeListRequest, NetworkPokeListRequest> {

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
	override fun map(input: PokeListRequest): NetworkPokeListRequest {
		logger.event("Mapping a PokeListRequest into a NetworkPokeListRequest")
		return NetworkPokeListRequest.builder().apply {
			limit = input.limit
			offset = input.offset
		}.build()
	}

}

/**
 * Maps the domain model to a DTO network model.
 *
 * @return The DTO network model.
 */
fun PokeListRequest.mapToNetwork() = PokeListRequestMapper.map(this)
