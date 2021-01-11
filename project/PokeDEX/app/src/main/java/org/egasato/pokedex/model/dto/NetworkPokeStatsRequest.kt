package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = NetworkPokeStatsRequest::class.java.canonicalName

/**
 * The request used to query stats of Pokémon.
 *
 * @property id The id.
 * @author Esaú García Sánchez-Torija
 */
class NetworkPokeStatsRequest(
	@JvmField @SerializedName("id") val id: Int
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

		/**
		 * Builds a Pokémon stats request instance.
		 *
		 * @return The Pokémon stats request instance.
		 */
		fun build() = NetworkPokeStatsRequest(id)

	}

}
