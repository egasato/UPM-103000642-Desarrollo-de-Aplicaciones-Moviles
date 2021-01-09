package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName

/**
 * The most basic object used by the PokéAPI.
 *
 * It represents an indirect resource.
 *
 * @property name The name of the resource.
 * @property url  The url of the resource.
 * @author Esaú García Sánchez-Torija
 */
class NetworkPokeResource(
	@JvmField @SerializedName("name") val name: String,
	@JvmField @SerializedName("url") val url: String
)
