package org.egasato.pokedex.model.map

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.PokeStatsResponse
import org.egasato.pokedex.model.dm.PokemonStats
import org.egasato.pokedex.model.dto.NetworkPokeStatsResponse
import java.util.*

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeStatsResponseMapper::class.java.canonicalName

/**
 * Maps a stats response as a DTO network model to a domain model.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeStatsResponseMapper : Mapper<NetworkPokeStatsResponse, PokeStatsResponse> {

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
	override fun map(input: NetworkPokeStatsResponse): PokeStatsResponse {
		logger.event("Mapping a NetworkPokeStatsResponse into a PokeStatsResponse")
		return PokeStatsResponse.builder().apply {
			this.id = input.id
			this.stats = PokemonStats.builder().apply {
				health         = input.stats.first { it.stat.name == "hp"              }.base
				attack         = input.stats.first { it.stat.name == "attack"          }.base
				defense        = input.stats.first { it.stat.name == "defense"         }.base
				specialAttack  = input.stats.first { it.stat.name == "special-attack"  }.base
				specialDefense = input.stats.first { it.stat.name == "special-defense" }.base
				speed          = input.stats.first { it.stat.name == "speed"           }.base
				height         = input.height
				weight         = input.weight
				types = input.types.map { it -> it.type.name
					.replace(Regex("-(\\w)")) { " " + it.groupValues[1].toUpperCase(Locale.getDefault()) }
					.capitalize(Locale.getDefault())
				}.toTypedArray()
				abilities = input.abilities.map { it -> it.ability.name
					.replace(Regex("-(\\w)")) { " " + it.groupValues[1].toUpperCase(Locale.getDefault()) }
					.capitalize(Locale.getDefault())
				}.toTypedArray()
				items = input.items.map { it -> it.item.name
					.replace(Regex("-(\\w)")) { " " + it.groupValues[1].toUpperCase(Locale.getDefault()) }
					.capitalize(Locale.getDefault())
				}.toTypedArray()
				movements = input.moves.map { it -> it.move.name
					.replace(Regex("-(\\w)")) { " " + it.groupValues[1].toUpperCase(Locale.getDefault()) }
					.capitalize(Locale.getDefault())
				}.toTypedArray()
			}.build()
		}.build()
	}

}

/**
 * Maps the DTO network model to a domain model.
 *
 * @return The domain model.
 */
fun NetworkPokeStatsResponse.mapToDomain() = PokeStatsResponseMapper.map(this)
