package org.egasato.pokedex.model.dm

import android.graphics.Bitmap
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = Pokemon::class.java.canonicalName

/**
 * The Pokémon shown in the list.
 *
 * It holds a reference to its stats, which will be populated once selected.
 *
 * @property id    The unique identifier.
 * @property name  The name.
 * @property image The image.
 * @property stats The stats.
 * @author Esaú García Sánchez-Torija
 */
data class Pokemon(
	val id: Int,
	val name: String,
	var image: Bitmap?,
	val stats: PokemonStats?
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

		/** The unique identifier. */
		var id = 0

		/** The name. */
		var name = ""

		/** The image. */
		var image: Bitmap? = null

		/** The stats builder. */
		var stats: PokemonStats? = null

		/**
		 * Builds the pokemon instance.
		 *
		 * @return The pokemon instance.
		 */
		fun build() = Pokemon(id, name, image, stats)

	}

}
