package org.egasato.pokedex.lifecycle

import androidx.lifecycle.MutableLiveData

/**
 * Stores the input error strings associated to the input fields.
 *
 * @author Esaú García Sánchez-Torija
 */
class AuthFormState {

	/** The username error string. */
	val username = MutableLiveData<CharSequence>()

	/** The password error string. */
	val password = MutableLiveData<CharSequence>()

	/** The repeat password error string. */
	val repeat = MutableLiveData<CharSequence>()

	/** The email error string. */
	val email = MutableLiveData<CharSequence>()

	/** Checks if the login form state is valid. */
	val isLoginValid
		get() = username.value == null && password.value == null

	/** Checks if the sign up form state is valid. */
	val isSignupValid
		get() = isLoginValid && repeat.value == null && email.value == null

}
