package org.egasato.pokedex.model.map

import org.egasato.pokedex.model.dm.PokeListRequest
import org.egasato.pokedex.model.dto.NetworkPokeListRequest

/**
 * Maps a Pokémon list request as a domain model to a DTO network model.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeListRequestMapper : Mapper<PokeListRequest, NetworkPokeListRequest> {

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	override fun map(input: PokeListRequest): NetworkPokeListRequest {
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
