package org.egasato.pokedex.lifecycle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.egasato.pokedex.app.PokeMainActivity
import org.egasato.pokedex.lib.util.Pair
import org.egasato.pokedex.lib.util.Single
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeAuthViewModel::class.java.canonicalName

/**
 * The [view model][ViewModel] of the [authentication activity][PokeMainActivity].
 *
 * @author Esaú García Sánchez-Torija
 */
class PokeAuthViewModel : ViewModel() {

	/** Whether the model was alive or not. */
	var first = false
		get() {
			logger.getter("Accessing the member \"first\"")
			return field
		}

	/** The bitmap scale value. */
	lateinit var scaleBmp: Single<Float>

	/** The drawing scale value. */
	lateinit var scaleDraw: Single<Float>

	/** The maximum scale value. */
	lateinit var scaleMax: Single<Float>

	/** The minimum scale value. */
	lateinit var scaleMin: Single<Float>

	/** The movement value. */
	lateinit var movement: Pair<Float, Float>

	/** The offset value. */
	lateinit var offset: Pair<Float, Float>

	/** The active fragment. */
	val fragment = MutableLiveData<Int>()
		get() {
			logger.getter("Accessing the member \"fragment\"")
			return field
		}

	/** The username value. */
	val username = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"username\"")
			return field
		}

	/** The password value. */
	val password = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"password\"")
			return field
		}

	/** The email value. */
	val email = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"email\"")
			return field
		}

	/** The repeated password. */
	val repeat = MutableLiveData<CharSequence>()
		get() {
			logger.getter("Accessing the member \"repeat\"")
			return field
		}

	/** The form state. */
	val state = AuthFormState()
		get() {
			logger.getter("Accessing the member \"state\"")
			return field
		}

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/** Triggered when the [view model][ViewModel] is destroyed. */
	override fun onCleared() {
		super.onCleared()
		logger.android { "Clearing an instance of $CLASS" }
	}

}
