package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.PokeListResponse
import org.egasato.pokedex.model.dm.Pokemon
import org.egasato.pokedex.model.dto.NetworkPokeListResponse
import org.egasato.pokedex.model.dto.NetworkPokeResource
import java.util.Locale

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeListResponseMapper::class.java.canonicalName

/** The regular expression used with the dashed names. */
private val DASHES = Regex("-([^-]+)")

/**
 * Maps a login response as a DTO network model to a domain model.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeListResponseMapper : Mapper<NetworkPokeListResponse, PokeListResponse> {

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
	override fun map(input: NetworkPokeListResponse): PokeListResponse {
		logger.event("Mapping a NetworkPokeListResponse into a PokeListResponse")
		return PokeListResponse.builder().apply {
			offset = input.offset ?: 0
			list = input.results.mapIndexed { i: Int, v: NetworkPokeResource ->
				Pokemon.builder().apply {
					id = offset + i + 1
					name = v.name.capitalize(Locale.ROOT)
						.replace("Mr-", "Mr. ")
						.replace(DASHES) { " (${it.groupValues[1]})" }
						.replace(" (oh)", "-oh")
						.replace("(m)", " (male)")
						.replace("(f)", " (female)")
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
