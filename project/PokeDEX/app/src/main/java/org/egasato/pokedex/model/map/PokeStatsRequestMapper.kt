package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.PokeStatsRequest
import org.egasato.pokedex.model.dto.NetworkPokeStatsRequest

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeStatsRequestMapper::class.java.canonicalName

/**
 * Maps a Pokémon stats request as a domain model to a DTO network model.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeStatsRequestMapper : Mapper<PokeStatsRequest, NetworkPokeStatsRequest> {

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
	override fun map(input: PokeStatsRequest): NetworkPokeStatsRequest {
		logger.event("Mapping a PokeStatsRequest into a NetworkPokeStatsRequest")
		return NetworkPokeStatsRequest.builder().apply { id = input.id }.build()
	}

}

/**
 * Maps the domain model to a DTO network model.
 *
 * @return The DTO network model.
 */
fun PokeStatsRequest.mapToNetwork() = PokeStatsRequestMapper.map(this)
