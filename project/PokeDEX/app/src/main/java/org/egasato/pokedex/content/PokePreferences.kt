package org.egasato.pokedex.content

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import org.egasato.pokedex.BuildConfig
import org.egasato.pokedex.app.PokeApplication
import org.egasato.pokedex.lib.content.SecurePreferences
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokePreferences::class.java.canonicalName

/**
 * A simple class that backs its contents in a shared preferences file.
 *
 * If the Android version has keystore support, it will use one to secure the contents of the fields.
 *
 * @param context The context.
 * @author Esaú García Sánchez-Torija
 */
class PokePreferences(context: Context) : PokeApplication.Aware {

	/** The context. */
	override val ctx = context

	/** The shared preferences. */
	private val preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE).let {
		if (!app.keystore.isSupported) it
		else SecurePreferences(context, it, app.keystore, setOf("token"))
	}

	/** The username. */
	var username: String
		get() {
			logger.getter("Accessing the member \"username\"")
			return preferences.getString("username", "")!!
		}
		set(value) {
			logger.setter("Accessing the member \"username\"")
			preferences.edit { putString("username", value) }
		}

	/** The token. */
	var token: String
		get() {
			logger.getter("Accessing the member \"token\"")
			return preferences.getString("token", "")!!
		}
		set(value) {
			logger.setter("Accessing the member \"token\"")
			preferences.edit { putString("token", value) }
		}

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

}
