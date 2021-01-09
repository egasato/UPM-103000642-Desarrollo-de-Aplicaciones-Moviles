package org.egasato.pokedex.app

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.egasato.pokedex.R
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.Pokemon
import org.egasato.pokedex.recyclerview.PokeListAdapter

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeListActivity::class.java.canonicalName

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

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/** Creates the recycler view adapter. */
	override fun onCreate(savedInstanceState: Bundle?) {
		logger.android { "Creating an instance of $CLASS" }
		super.onCreate(savedInstanceState)

		logger.android("Setting the content view")
		setContentView(R.layout.list_activity)

		logger.android("Configuring the action bar")
		supportActionBar?.apply {
			setCustomView(R.layout.toolbar)
			setDisplayShowTitleEnabled(false)
			setDisplayHomeAsUpEnabled(false)
			displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
		}

		logger.android("Configuring the adapter")
		findViewById<RecyclerView>(R.id.list).also {
			it.setHasFixedSize(true)
			it.itemAnimator?.changeDuration = 0
			it.adapter = PokeListAdapter(this, pokemons)
		}
	}

}
