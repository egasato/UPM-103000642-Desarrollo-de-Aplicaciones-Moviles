package org.egasato.pokedex.repo

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.google.gson.Gson
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dto.NetworkLoginRequest
import org.egasato.pokedex.model.dto.NetworkLoginResponse
import org.egasato.pokedex.model.dto.NetworkSignupRequest
import org.egasato.pokedex.model.dto.NetworkSignupResponse
import org.json.JSONObject

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/**
 * The data source used for authentication.
 *
 * @author Esaú García Sánchez-Torija
 */
object AuthDataSource {

	/** The base URL of the authentication API. */
	private const val base = "https://pokedbex.herokuapp.com"

	/** The [GSON][Gson] object. */
	private val gson = Gson()

	/**
	 * Performs a synchronous login request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	fun login(request: NetworkLoginRequest): NetworkLoginResponse? {
		logger.event { "Requesting: $base/login" }
		val req = AndroidNetworking.post("$base/login")
			.addJSONObjectBody(JSONObject(gson.toJson(request)))
			.setPriority(Priority.HIGH)
			.build()
		val res = req.executeForString()
		return if (res.isSuccess) {
			logger.event("Parsing response as NetworkLoginResponse")
			gson.fromJson(res.result as String, NetworkLoginResponse::class.java)
		} else {
			logger.event("Parsing response failed")
			null
		}
	}

	/**
	 * Performs a synchronous sign-up request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	fun signup(request: NetworkSignupRequest): NetworkSignupResponse? {
		logger.event { "Requesting: $base/signup" }
		val req = AndroidNetworking.post("$base/signup")
			.addJSONObjectBody(JSONObject(gson.toJson(request)))
			.setPriority(Priority.HIGH)
			.build()
		val res = req.executeForString()
		return if (res.isSuccess) {
			logger.event("Parsing response as NetworkSignupResponse")
			gson.fromJson(res.result as String, NetworkSignupResponse::class.java)
		} else {
			logger.event("Parsing response failed")
			null
		}
	}

}
