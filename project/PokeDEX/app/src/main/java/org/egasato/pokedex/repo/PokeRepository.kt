package org.egasato.pokedex.repo

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.PokeListRequest
import org.egasato.pokedex.model.dm.PokeListResponse
import org.egasato.pokedex.model.map.mapToDomain
import org.egasato.pokedex.model.map.mapToNetwork

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/**
 * The repository that performs Pokémon operations.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeRepository {

	/**
	 * Performs a synchronous Pokémon list request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	fun pokemon(request: PokeListRequest): PokeListResponse? {
		logger.event("Transforming domain model to DTO")
		val dto = request.mapToNetwork()
		val response = PokeDataSource.pokemon(dto)
		logger.event("Transforming DTO to domain model")
		return response?.mapToDomain()
	}

	/**
	 * Performs a synchronous Pokémon list request.
	 *
	 * @param limit  The limit.
	 * @param offset The offset.
	 * @return The response.
	 */
	fun pokemon(limit: Int, offset: Int): PokeListResponse? {
		logger.event("Creating list request domain model")
		val domain = PokeListRequest.builder().apply {
			this.limit = limit
			this.offset = offset
		}.build()
		return pokemon(domain)
	}

}
