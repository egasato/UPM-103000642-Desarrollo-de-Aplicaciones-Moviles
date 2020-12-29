package org.egasato.pokedex.lib.util

/**
 * An implementation of [Single].
 *
 * @see Single
 * @author Esaú García Sánchez-Torija
 */
internal data class SingleImpl<A>(override var first: A): Single<A>
