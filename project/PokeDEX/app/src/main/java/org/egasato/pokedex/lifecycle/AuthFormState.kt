package org.egasato.pokedex.lifecycle

import androidx.lifecycle.MutableLiveData
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = AuthFormState::class.java.canonicalName

/**
 * Holds the state of the authentication form.
 *
 * It stores the input error messages associated to the input fields and allows checking easily if the data is valid.
 *
 * @author Esaú García Sánchez-Torija
 */
class AuthFormState {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/** The username error string. */
	val username = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"username\"")
			return field
		}

	/** The password error string. */
	val password = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"password\"")
			return field
		}

	/** The repeat password error string. */
	val repeat = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"repeat\"")
			return field
		}

	/** The email error string. */
	val email = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"email\"")
			return field
		}

	/** Checks if the login form state is valid. */
	val isLoginValid: Boolean
		get() {
			logger.getter("Accessing the member \"isLoginValid\"")
			return username.value == null && password.value == null
		}

	/** Checks if the sign up form state is valid. */
	val isSignupValid: Boolean
		get() {
			logger.getter("Accessing the member \"isSignupValid\"")
			return username.value == null && password.value == null && repeat.value == null && email.value == null
		}

}
