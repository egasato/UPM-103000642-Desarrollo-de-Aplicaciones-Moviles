package org.egasato.pokedex.model.dto

import com.google.gson.annotations.SerializedName
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = NetworkPokeResource::class.java.canonicalName

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
) {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

}
