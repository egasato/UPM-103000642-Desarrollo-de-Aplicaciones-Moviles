package org.egasato.pokedex.lib.util

/**
 * A pair of values.
 *
 * @see Single
 * @author Esaú García Sánchez-Torija
 */
interface Pair<A, B> : Single<A> {

	/** The companion object used to expose the static constructors.  */
	companion object {

		/**
		 * Creates a [mutable pair][Pair].
		 *
		 * @param first  The first component.
		 * @param second The second component.
		 * @return A mutable pair.
		 */
		@JvmStatic
		fun <A, B> create(first: A, second: B): Pair<A, B> = PairImpl(first, second)

	}

	/** The second value. */
	var second: B

	/**
	 * Obtains a component.
	 *
	 * @param i The index.
	 * @return The component value.
	 */
	override operator fun get(i: Int) = when (i) {
		0 -> first as Any
		1 -> second as Any
		else -> throw IllegalArgumentException("The index MUST be 0 or 1")
	}

}
