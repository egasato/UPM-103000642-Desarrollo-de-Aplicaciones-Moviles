package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = NetworkPokeListResponse::class.java.canonicalName

/**
 * The response of a Pokémon list query.
 *
 * @property count    The number of results.
 * @property next     The URL of the next resource.
 * @property previous The URL of the previous resource.
 * @property offset   The offset.
 * @property results  The results.
 * @author Esaú García Sánchez-Torija
 */
class NetworkPokeListResponse(
	@JvmField @SerializedName("count") val count: Int,
	@JvmField @SerializedName("next") val next: String?,
	@JvmField @SerializedName("previous") val previous: String?,
	@JvmField @SerializedName("offset") val offset: Int?,
	@JvmField @SerializedName("results") val results: List<NetworkPokeResource>
) {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

}
