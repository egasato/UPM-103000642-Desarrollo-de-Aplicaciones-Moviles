package org.egasato.pokedex.repo

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.google.gson.Gson
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dto.NetworkPokeListRequest
import org.egasato.pokedex.model.dto.NetworkPokeListResponse
import org.egasato.pokedex.model.dto.NetworkPokeStatsRequest
import org.egasato.pokedex.model.dto.NetworkPokeStatsResponse

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeDataSource::class.java.canonicalName

/**
 * The data source used for the Pokédex.
 *
 * @author Esaú García Sánchez-Torija
 */
object PokeDataSource {

	/** The base URL of the Pokédex API. */
	private const val base = "https://pokeapi.co/api/v2"

	/** The [GSON][Gson] object. */
	private val gson = Gson()

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/**
	 * Performs a synchronous Pokémon list request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	fun pokemon(request: NetworkPokeListRequest): NetworkPokeListResponse? {
		val req = AndroidNetworking.get("$base/pokemon")
			.addQueryParameter("limit", request.limit.toString())
			.addQueryParameter("offset", request.offset.toString())
			.setPriority(Priority.MEDIUM)
			.build()
		logger.event { "Requesting resource at ${req.url}" }
		val res = req.executeForString()
		return if (res.isSuccess) {
			logger.event { "Parsed response from ${req.url} as NetworkPokeListResponse" }
			val tmp = gson.fromJson(res.result as String, NetworkPokeListResponse::class.java)
			NetworkPokeListResponse(tmp.count, tmp.next, tmp.previous, request.offset, tmp.results)
		} else {
			logger.error { "Could not parse response from ${req.url} as NetworkPokeListResponse" }
			null
		}
	}

	/**
	 * Performs a synchronous Pokémon stats request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	fun stats(request: NetworkPokeStatsRequest): NetworkPokeStatsResponse? {
		val req = AndroidNetworking.get("$base/pokemon/{id}")
			.addPathParameter("id", request.id.toString())
			.setPriority(Priority.HIGH)
			.build()
		logger.event { "Requesting resource at ${req.url}" }
		val res = req.executeForString()
		return if (res.isSuccess) {
			logger.event { "Parsed response from ${req.url} as NetworkPokeStatsResponse" }
			gson.fromJson(res.result as String, NetworkPokeStatsResponse::class.java)
		} else {
			logger.error { "Could not parse response from ${req.url} as NetworkPokeStatsResponse" }
			null
		}
	}

}
