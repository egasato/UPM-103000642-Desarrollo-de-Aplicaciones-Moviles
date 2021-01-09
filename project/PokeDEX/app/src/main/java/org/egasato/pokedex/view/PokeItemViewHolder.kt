package org.egasato.pokedex.view

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.egasato.pokedex.R
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeItemViewHolder::class.java.canonicalName

/**
 * The view holder of the Pokémon item layout.
 *
 * @param view The view.
 * @author Esaú García Sánchez-Torija
 */
class PokeItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

	/** The Pokémon id. */
	val id: TextView = view.findViewById(R.id.id)
		get() {
			logger.getter("Accessing the member \"id\"")
			return field
		}

	/** The Pokémon name. */
	val name: TextView = view.findViewById(R.id.name)
		get() {
			logger.getter("Accessing the member \"name\"")
			return field
		}

	/** The Pokémon icon. */
	val icon: ImageView = view.findViewById(R.id.icon)
		get() {
			logger.getter("Accessing the member \"icon\"")
			return field
		}

	/** The loading spinner. */
	val load: ProgressBar = view.findViewById(R.id.load)
		get() {
			logger.getter("Accessing the member \"load\"")
			return field
		}

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

}
