package org.egasato.pokedex.lib.util

/**
 * An implementation of [Pair].
 *
 * @see Pair
 * @author Esaú García Sánchez-Torija
 */
internal data class PairImpl<A, B>(override var first: A, override var second: B): Pair<A, B>
