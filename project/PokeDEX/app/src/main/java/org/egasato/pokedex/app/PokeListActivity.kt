package org.egasato.pokedex.app

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.egasato.pokedex.R
import org.egasato.pokedex.model.dm.Pokemon

/**
 * The list [activity][AppCompatActivity].
 *
 * Shows the list with all the Pokémon.
 *
 * @author Esaú García Sánchez-Torija
 */
class PokeListActivity : AppCompatActivity() {

	/** The list of Pokémon. */
	private val pokemons = arrayOfNulls<Pokemon>(1118).toMutableList()

	/** Creates the recycler view adapter. */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.list_activity)
		supportActionBar?.apply {
			setCustomView(R.layout.toolbar)
			setDisplayShowTitleEnabled(false)
			setDisplayHomeAsUpEnabled(false)
			displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
		}
		findViewById<RecyclerView>(R.id.list).also {
			it.adapter = PokeListAdapter(this, pokemons)
		}
	}

}
