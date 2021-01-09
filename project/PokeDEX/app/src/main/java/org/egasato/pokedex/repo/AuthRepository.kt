package org.egasato.pokedex.repo

import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.LoginRequest
import org.egasato.pokedex.model.dm.LoginResponse
import org.egasato.pokedex.model.dm.SignupRequest
import org.egasato.pokedex.model.dm.SignupResponse
import org.egasato.pokedex.model.map.mapToDomain
import org.egasato.pokedex.model.map.mapToNetwork

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = AuthRepository::class.java.canonicalName

/**
 * The repository that performs authentication operations.
 *
 * @author Esaú García Sánchez-Torija
 */
object AuthRepository {

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
	private fun login(request: LoginRequest): LoginResponse? {
		logger.event("Performing login operation")
		val dto = request.mapToNetwork()
		val response = AuthDataSource.login(dto)
		return response?.mapToDomain()
	}

	/**
	 * Performs a synchronous login request.
	 *
	 * @param username The username.
	 * @param password The password.
	 * @return The response.
	 */
	fun login(username: String, password: String): LoginResponse? {
		val domain = LoginRequest.builder().apply {
			this.username = username
			this.password = password
		}.build()
		return login(domain)
	}

	/**
	 * Performs a synchronous sign-up request.
	 *
	 * @param request The request object.
	 * @return The response.
	 */
	private fun signup(request: SignupRequest): SignupResponse? {
		logger.event("Performing sign-up operation")
		val dto = request.mapToNetwork()
		val response = AuthDataSource.signup(dto)
		return response?.mapToDomain()
	}

	/**
	 * Performs a synchronous login request.
	 *
	 * @param username The username.
	 * @param email    The email.
	 * @param password The password.
	 * @return The response.
	 */
	fun signup(username: String, email: String, password: String): SignupResponse? {
		val domain = SignupRequest.builder().apply {
			this.username = username
			this.email = email
			this.password = password
		}.build()
		return signup(domain)
	}

}
