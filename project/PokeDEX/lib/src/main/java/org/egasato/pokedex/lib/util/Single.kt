package org.egasato.pokedex.lib.util

/**
 * A single value.
 *
 * @author Esaú García Sánchez-Torija
 */
interface Single<A> {

	/** The companion object used to expose the static constructors.  */
	companion object {

		/**
		 * Creates a [mutable single][Single].
		 *
		 * @param first The first component.
		 * @return A mutable single.
		 */
		@JvmStatic
		fun <A> create(first: A): Single<A> = SingleImpl(first)

	}

	/** The first value. */
	var first: A

	/**
	 * Obtains a component.
	 *
	 * @param i The index.
	 * @return The component value.
	 */
	operator fun get(i: Int) = when (i) {
		0 -> first as Any
		else -> throw IllegalArgumentException("The index MUST be 0")
	}

}
