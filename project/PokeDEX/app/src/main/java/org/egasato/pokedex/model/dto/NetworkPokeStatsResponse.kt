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
class NetworkPokeStatsResponse(
	@JvmField @SerializedName("abilities")                val abilities: List<Ability>,
	@JvmField @SerializedName("base_experience")          val experience: Int,
	@JvmField @SerializedName("forms")                    val forms: List<NetworkPokeResource>,
	@JvmField @SerializedName("game_indices")             val games: List<Game>,
	@JvmField @SerializedName("height")                   val height: Int,
	@JvmField @SerializedName("held_items")               val items: List<Item>,
	@JvmField @SerializedName("id")                       val id: Int,
	@JvmField @SerializedName("is_default")               val default: Boolean,
	@JvmField @SerializedName("location_area_encounters") val encounters: String,
	@JvmField @SerializedName("moves")                    val moves: List<Move>,
	@JvmField @SerializedName("name")                     val name: String,
	@JvmField @SerializedName("order")                    val order: Int,
	@JvmField @SerializedName("species")                  val species: NetworkPokeResource,
	@JvmField @SerializedName("stats")                    val stats: List<Stat>,
	@JvmField @SerializedName("types")                    val types: List<Type>,
	@JvmField @SerializedName("weight")                   val weight: Int
) {

	/**
	 * Represents an ability.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class Ability(
		@JvmField @SerializedName("ability")   val ability: NetworkPokeResource,
		@JvmField @SerializedName("is_hidden") val hidden: Boolean,
		@JvmField @SerializedName("slot")      val slot: Int
	)

	/**
	 * Represents a game index.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class Game(
		@JvmField @SerializedName("game_index") val index: Int,
		@JvmField @SerializedName("version")    val version: NetworkPokeResource
	)

	/**
	 * Represents an item.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class Item(
		@JvmField @SerializedName("item")            val item: NetworkPokeResource,
		@JvmField @SerializedName("version_details") val details: List<ItemDetails>
	)

	/**
	 * Represents an item's details.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class ItemDetails(
		@JvmField @SerializedName("rarity")  val rarity: Int,
		@JvmField @SerializedName("version") val version: NetworkPokeResource
	)

	/**
	 * Represents a move.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class Move(
		@JvmField @SerializedName("move")                  val move: NetworkPokeResource,
		@JvmField @SerializedName("version_group_details") val details: List<MoveDetails>
	)

	/**
	 * Represents a move's details.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class MoveDetails(
		@JvmField @SerializedName("level_learned_at")  val move: Int,
		@JvmField @SerializedName("move_learn_method") val method: NetworkPokeResource,
		@JvmField @SerializedName("version_group")     val group: NetworkPokeResource
	)

	/**
	 * Represents a sprite.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class Sprite(
		@JvmField @SerializedName("back_default")       val back: String?,
		@JvmField @SerializedName("back_female")        val backFemale: String?,
		@JvmField @SerializedName("back_shiny")         val backShiny: String?,
		@JvmField @SerializedName("back_shiny_female")  val backShinyFemale: String?,
		@JvmField @SerializedName("front_default")      val front: String?,
		@JvmField @SerializedName("front_female")       val frontFemale: String?,
		@JvmField @SerializedName("front_shiny")        val frontShiny: String?,
		@JvmField @SerializedName("front_shiny_female") val frontShinyFemale: String?
	)

	/**
	 * Represents a stat.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class Stat(
		@JvmField @SerializedName("base_stat") val base: Int,
		@JvmField @SerializedName("effort")    val effort: Int,
		@JvmField @SerializedName("stat")      val stat: NetworkPokeResource
	)

	/**
	 * Represents a type.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	inner class Type(
		@JvmField @SerializedName("slot") val slot: Int,
		@JvmField @SerializedName("type") val type: NetworkPokeResource
	)

}
