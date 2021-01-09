package org.egasato.pokedex.repo

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.PokeListRequest
import org.egasato.pokedex.model.dm.PokeListResponse
import org.egasato.pokedex.model.map.mapToDomain
import org.egasato.pokedex.model.map.mapToNetwork

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeRepository::class.java.canonicalName

/**
 * The repository that performs Pokémon operations.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeRepository {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/**
	 * Performs a synchronous Pokémon list request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	private fun pokemon(request: PokeListRequest): PokeListResponse? {
		logger.event("Performing listing operation")
		val dto = request.mapToNetwork()
		val response = PokeDataSource.pokemon(dto)
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
		val domain = PokeListRequest.builder().apply {
			this.limit = limit
			this.offset = offset
		}.build()
		return pokemon(domain)
	}

}
