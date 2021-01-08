package org.egasato.pokedex.model.dm

/**
 * The details shown when a Pokémon is selected.
 *
 * @property health         The health points.
 * @property attack         The attack points.
 * @property defense        The defense points.
 * @property specialAttack  The special attack points.
 * @property specialDefense The special defense points.
 * @property speed          The speed points.
 * @property types          The types.
 * @property height         The height.
 * @property weight         The weight.
 * @property abilities      The abilities.
 * @property items          The items.
 * @property movements      The movements.
 * @author Esaú García Sánchez-Torija
 */
class PokemonStats(
	val health: Int,
	val attack: Int,
	val defense: Int,
	val specialAttack: Int,
	val specialDefense: Int,
	val speed: Int,
	val types: Array<String>,
	val height: Int,
	val weight: Int,
	val abilities: Array<String>,
	val items: Array<String>,
	val movements: Array<String>,
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

		/** The health points. */
		var health = 0

		/** The attack points. */
		var attack = 0

		/** The defense points. */
		var defense = 0

		/** The special attack points. */
		var specialAttack = 0

		/** The special defense points. */
		var specialDefense = 0

		/** The speed points. */
		var speed = 0

		/** The types. */
		var types: Array<String> = emptyArray()

		/** The height. */
		var height = 0

		/** The weight. */
		var weight = 0

		/** The abilities. */
		var abilities: Array<String> = emptyArray()

		/** The items. */
		var items: Array<String> = emptyArray()

		/** The movements. */
		var movements: Array<String> = emptyArray()

		/**
		 * Builds the pokemon instance.
		 *
		 * @return The pokemon instance.
		 */
		fun build() = PokemonStats(
			health = health, attack = attack, defense = defense,
			specialAttack = specialAttack, specialDefense = specialDefense,
			speed = speed, types = types, height = height, weight = weight,
			abilities = abilities, items = items, movements = movements
		)

	}

}
