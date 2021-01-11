package org.egasato.pokedex.model.dm

import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeStatsResponse::class.java.canonicalName

/**
 * The response of a Pokémon stats query.
 *
 * @property id    The id.
 * @property stats The stats.
 * @author Esaú García Sánchez-Torija
 */
class PokeStatsResponse(
	val id: Int,
	val stats: PokemonStats
) {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/** Unnamed companion object exposing the static methods. */
	companion object {

		/**
		 * Creates an instance builder.
		 *
		 * @return The builder.
		 */
		@JvmStatic
		fun builder() = Builder()

	}

	/**
	 * An instance builder.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	class Builder {

		/** The id. */
		@JvmField var id = -1

		/** The stats. */
		@JvmField var stats: PokemonStats? = null

		/**
		 * Builds a stats response instance.
		 *
		 * @return The stats response instance.
		 */
		fun build() = PokeStatsResponse(id, stats!!)

	}

}
