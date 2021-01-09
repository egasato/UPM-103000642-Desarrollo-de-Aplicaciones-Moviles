package org.egasato.pokedex.app

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import org.egasato.pokedex.content.PokePreferences
import org.egasato.pokedex.lib.crypto.PRNG
import org.egasato.pokedex.lib.security.keystore.Keystore
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeApplication::class.java.canonicalName

/**
 * A basic application implementation that caches objects.
 * 
 * @author Esaú García Sánchez-Torija
 */
class PokeApplication : Application() {

	/** An Android-compatible secure keystore. */
	val keystore = Keystore(this)
		get () {
			logger.getter("Accessing the member \"keystore\"")
			return field
		}

	/** A secure-if-possible shared preferences instance. */
	lateinit var preferences: PokePreferences

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/** Fixes the pseudo-random number generator on Android 4.3 and below (API <= 18). */
	override fun onCreate() {
		super.onCreate()
		PRNG.fix()
		preferences = PokePreferences(this)
	}

	/**
	 * A component that is aware of the application.
	 *
	 * @author Esaú García Sánchez-Torija
	 */
	interface Aware {

		/** The context. */
		val ctx: Context
			get() {
				logger.getter("Accessing the member \"ctx\"")
				return when (this) {
					is Activity -> application
					is AppCompatActivity -> application
					else -> throw RuntimeException("Context-incompatible class")
				}
			}

		/** The application instance used in this project. */
		val app: PokeApplication
			get() {
				logger.getter("Accessing the member \"app\"")
				return ctx as PokeApplication
			}

	}

}
