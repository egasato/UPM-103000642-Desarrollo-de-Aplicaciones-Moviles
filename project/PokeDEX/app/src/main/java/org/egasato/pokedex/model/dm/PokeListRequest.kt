package org.egasato.pokedex.model.dm

/**
 * The request used to query list of Pokémon.
 *
 * @property limit  The maximum number of results.
 * @property offset The offset to skip.
 * @author Esaú García Sánchez-Torija
 */
class PokeListRequest(
	@JvmField val limit: Int,
	@JvmField val offset: Int
) {

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

		/** The limit. */
		@JvmField var limit = 1

		/** The offset. */
		@JvmField var offset = 0

		/**
		 * Builds a list request instance.
		 *
		 * @return The list request instance.
		 */
		fun build() = PokeListRequest(limit, offset)

	}

}
