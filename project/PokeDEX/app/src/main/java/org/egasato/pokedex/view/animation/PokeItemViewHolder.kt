package org.egasato.pokedex.view.animation

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.egasato.pokedex.R

/**
 * The view holder of the Pokémon item layout.
 *
 * @param view The view.
 * @author Esaú García Sánchez-Torija
 */
class PokeItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

	/** The Pokémon id. */
	val id: TextView = view.findViewById(R.id.id)

	/** The Pokémon name. */
	val name: TextView = view.findViewById(R.id.name)

	/** The Pokémon icon. */
	val icon: ImageView = view.findViewById(R.id.icon)

	/** The loading spinner. */
	val load: ProgressBar = view.findViewById(R.id.load)

}
