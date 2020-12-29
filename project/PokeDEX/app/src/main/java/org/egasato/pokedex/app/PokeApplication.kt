package org.egasato.pokedex.app

import android.app.Application
import org.egasato.pokedex.lib.crypto.PRNG

/**
 * A basic application implementation that performs early fixes and caches objects.
 * 
 * @author Esaú García Sánchez-Torija
 */
class PokeApplication : Application() {

	/** Fixes the pseudo-random number generator on Android 4.3 and below (API <= 18). */
	override fun onCreate() {
		super.onCreate()
		PRNG.fix()
	}
	
}
