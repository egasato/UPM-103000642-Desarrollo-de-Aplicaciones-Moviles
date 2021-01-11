package org.egasato.pokedex.lifecycle

import androidx.lifecycle.ViewModel
import org.egasato.pokedex.lib.util.Pair
import org.egasato.pokedex.lib.util.Single
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.Pokemon

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeViewModel::class.java.canonicalName

/**
 * The view model used to cache the Pokémon.
 *
 * @author Esaú García Sánchez-Torija
 */
class PokeViewModel : ViewModel() {

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
