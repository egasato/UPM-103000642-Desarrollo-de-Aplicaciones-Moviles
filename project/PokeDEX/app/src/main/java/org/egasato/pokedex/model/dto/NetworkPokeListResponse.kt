package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName

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
)
