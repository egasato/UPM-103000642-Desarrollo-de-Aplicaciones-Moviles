package org.egasato.pokedex.model.map

/**
 * An object-oriented mapper interface.
 *
 * @author Esaú García Sánchez-Torija
 */
interface Mapper<in I, out O> {

	/**
	 * Maps an input model to an output model.
	 *
	 * @param input The input model.
	 * @return The output model.
	 */
	fun map(input: I): O

}
