package org.egasato.pokedex.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeLoadingViewHolder::class.java.canonicalName

/**
 * The view holder of the Pokémon loading layout.
 *
 * @param view The view.
 * @author Esaú García Sánchez-Torija
 */
class PokeLoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

}
