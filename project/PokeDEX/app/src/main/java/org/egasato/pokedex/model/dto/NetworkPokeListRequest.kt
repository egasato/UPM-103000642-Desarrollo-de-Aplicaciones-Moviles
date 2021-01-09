package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName

/**
 * The request used to query list of Pokémon.
 *
 * @property limit  The maximum number of results.
 * @property offset The offset to skip.
 * @author Esaú García Sánchez-Torija
 */
class NetworkPokeListRequest(
	@JvmField @SerializedName("limit") val limit: Int,
	@JvmField @SerializedName("offset") val offset: Int
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

		/** The limit. */
		@JvmField var limit = 1

		/** The offset. */
		@JvmField var offset = 0

		/**
		 * Builds a Pokémon list request instance.
		 *
		 * @return The Pokémon list request instance.
		 */
		fun build() = NetworkPokeListRequest(limit, offset)

	}

}
