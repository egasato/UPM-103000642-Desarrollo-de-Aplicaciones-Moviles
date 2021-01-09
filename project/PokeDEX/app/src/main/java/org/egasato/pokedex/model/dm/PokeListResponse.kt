package org.egasato.pokedex.model.dm

/**
 * The response of a Pokémon list query.
 *
 * @property offset The offset.
 * @property list   The list of Pokémon.
 * @author Esaú García Sánchez-Torija
 */
class PokeListResponse(
	private val offset: Int,
	private val list: List<Pokemon>
) : AbstractList<Pokemon>() {

	/** The size of the list. */
	override val size: Int
		get() = list.size

	/**
	 * Obtains a Pokémon from the list.
	 *
	 * @param index The index.
	 * @return The pokemon.
	 */
	override fun get(index: Int) = list[index]

	/**
	 * Obtains a Pokémon from the list.
	 *
	 * @param id The id.
	 * @return The pokemon.
	 */
	fun id(id: Int) = list[id - offset]

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

		/** The offset. */
		@JvmField var offset = 0

		/** The list. */
		@JvmField var list = emptyList<Pokemon>()

		/**
		 * Builds a list response instance.
		 *
		 * @return The list response instance.
		 */
		fun build() = PokeListResponse(offset, list)

	}

}
