package org.egasato.pokedex.model.dm

import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeStatsRequest::class.java.canonicalName

/**
 * The request used to query the stats of a Pokémon.
 *
 * @property id The id of the Pokémon.
 * @author Esaú García Sánchez-Torija
 */
class PokeStatsRequest(@JvmField val id: Int) {

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

		/**
		 * Builds a list request instance.
		 *
		 * @return The list request instance.
		 */
		fun build() = PokeStatsRequest(id)

	}

}
