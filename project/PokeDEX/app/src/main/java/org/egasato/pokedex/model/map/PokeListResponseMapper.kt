package org.egasato.pokedex.model.map

import org.egasato.pokedex.model.dm.PokeListResponse
import org.egasato.pokedex.model.dm.Pokemon
import org.egasato.pokedex.model.dto.NetworkPokeListResponse
import org.egasato.pokedex.model.dto.NetworkPokeResource

/**
 * Maps a login response as a DTO network model to a domain model.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeListResponseMapper : Mapper<NetworkPokeListResponse, PokeListResponse> {

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	override fun map(input: NetworkPokeListResponse): PokeListResponse {
		return PokeListResponse.builder().apply {
			offset = input.offset ?: 0
			list = input.results.mapIndexed { i: Int, v: NetworkPokeResource ->
				Pokemon.builder().apply {
					id = offset + i + 1
					name = v.name
					image = null
					stats = null
				}.build()
			}
		}.build()
	}

}

/**
 * Maps the DTO network model to a domain model.
 *
 * @return The domain model.
 */
fun NetworkPokeListResponse.mapToDomain() = PokeListResponseMapper.map(this)
