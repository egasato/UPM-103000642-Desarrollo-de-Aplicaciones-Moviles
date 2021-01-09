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

/** The complete name of the class. */
private val CLASS = AuthDataSource::class.java.canonicalName

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

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/**
	 * Performs a synchronous login request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	fun login(request: NetworkLoginRequest): NetworkLoginResponse? {
		val req = AndroidNetworking.post("$base/login")
			.addJSONObjectBody(JSONObject(gson.toJson(request)))
			.setPriority(Priority.HIGH)
			.build()
		logger.event { "Requesting resource at ${req.url}" }
		val res = req.executeForString()
		return if (res.isSuccess) {
			logger.event { "Parsed response from ${req.url} as NetworkLoginResponse" }
			gson.fromJson(res.result as String, NetworkLoginResponse::class.java)
		} else {
			logger.error { "Could not parse response from ${req.url} as NetworkLoginResponse" }
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
		val req = AndroidNetworking.post("$base/signup")
			.addJSONObjectBody(JSONObject(gson.toJson(request)))
			.setPriority(Priority.HIGH)
			.build()
		logger.event { "Requesting resource at ${req.url}" }
		val res = req.executeForString()
		return if (res.isSuccess) {
			logger.event { "Parsed response from ${req.url} as NetworkSignupResponse" }
			gson.fromJson(res.result as String, NetworkSignupResponse::class.java)
		} else {
			logger.error { "Could not parse response from ${req.url} as NetworkSignupResponse" }
			null
		}
	}

}
