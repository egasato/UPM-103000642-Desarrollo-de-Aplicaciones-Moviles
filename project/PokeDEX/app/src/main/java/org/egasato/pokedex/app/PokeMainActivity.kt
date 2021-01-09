package org.egasato.pokedex.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.egasato.pokedex.R
import org.egasato.pokedex.lib.security.CryptoException
import org.egasato.pokedex.log.PokeLogger
import java.io.IOException
import javax.crypto.IllegalBlockSizeException

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeMainActivity::class.java.canonicalName

/**
 * The [activity][AppCompatActivity] started by the application launcher.
 *
 * It behaves as a splash screen while deciding which other activity to launch.
 *
 * @see AppCompatActivity
 * @author Esaú García Sánchez-Torija
 */
class PokeMainActivity : AppCompatActivity(), PokeApplication.Aware {

	/**
	 * Starts the authentication activity right away.
	 *
	 * @param savedInstanceState The saved state of the activity.
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		logger.android { "Creating an instance of $CLASS" }
		super.onCreate(savedInstanceState)

		logger.android("Setting the content view")
		setContentView(R.layout.splash_background)

		if (app.keystore.isSupported && !app.keystore.keyPairExists()) {
			logger.android("Creating and encryption keypair")
			app.keystore.generateKeyPair()
		}

		logger.android("Changing activity based on token validation")
		val invalid = try {
			app.preferences.token.isBlank()
		} catch (ex: CryptoException) {
			if (ex.cause !is IOException) throw ex
			if ((ex.cause as IOException).cause !is IllegalBlockSizeException) throw ex
			true
		}
		val intent = if (invalid) {
			Intent(this, PokeAuthActivity::class.java)
		} else {
			Intent(this, PokeListActivity::class.java)
		}
		startActivity(intent.apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) })
		finish()
	}

}
