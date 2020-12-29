package org.egasato.pokedex.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.egasato.pokedex.R

/**
 * The [activity][AppCompatActivity] started by the application launcher.
 *
 * It behaves as a splash screen while deciding which other activity to launch.
 *
 * @see AppCompatActivity
 * @author Esaú García Sánchez-Torija
 */
class PokeMainActivity : AppCompatActivity() {

	/**
	 * Starts the authentication activity right away.
	 *
	 * @param savedInstanceState The saved state of the activity.
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.splash_background)
	}

}
