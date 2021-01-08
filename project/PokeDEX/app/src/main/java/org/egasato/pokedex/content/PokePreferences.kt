package org.egasato.pokedex.content

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import org.egasato.pokedex.BuildConfig
import org.egasato.pokedex.app.PokeApplication
import org.egasato.pokedex.lib.content.SecurePreferences

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
		get() = preferences.getString("username", "")!!
		set(value) {
			preferences.edit { putString("username", value) }
		}

	/** The token. */
	var token: String
		get() = preferences.getString("token", "")!!
		set(value) {
			preferences.edit { putString("token", value) }
		}

}
