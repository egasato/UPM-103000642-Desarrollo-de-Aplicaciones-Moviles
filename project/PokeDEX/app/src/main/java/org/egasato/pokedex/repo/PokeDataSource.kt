package org.egasato.pokedex.repo

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.google.gson.Gson
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dto.NetworkPokeListRequest
import org.egasato.pokedex.model.dto.NetworkPokeListResponse

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

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
			.setPriority(Priority.LOW)
			.build()
		logger.event { "Requesting: ${req.url}" }
		val res = req.executeForString()
		return if (res.isSuccess) {
			logger.event("Parsing response as NetworkPokeListResponse")
			val tmp = gson.fromJson(res.result as String, NetworkPokeListResponse::class.java)
			NetworkPokeListResponse(
				count = tmp.count,
				next = tmp.next,
				previous = tmp.previous,
				offset = request.offset,
				results = tmp.results
			)
		} else {
			logger.event("Parsing response failed")
			null
		}
	}

}
