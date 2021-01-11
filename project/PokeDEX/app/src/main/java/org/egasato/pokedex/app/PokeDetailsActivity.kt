package org.egasato.pokedex.app

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import org.egasato.pokedex.R
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.Pokemon

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeDetailsActivity::class.java.canonicalName

/**
 * The details [activity][AppCompatActivity].
 *
 * Shows the details of a specific Pokémon.
 *
 * @author Esaú García Sánchez-Torija
 */
class PokeDetailsActivity : AppCompatActivity(), PokeApplication.Aware {

	/** The Pokémon. */
	private var pokemon: Pokemon? = null

	/** The Pokémon name. */
	private val name
		get() = pokemon?.name

	/** The Pokémon image. */
	private val image
		get() = pokemon?.image

	/** The Pokémon stats. */
	private val stats
		get() = pokemon?.stats

	// Logs the object creation
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/** Fills the views with the Pokémon data. */
	override fun onCreate(savedInstanceState: Bundle?) {
		logger.android { "Creating an instance of $CLASS" }
		super.onCreate(savedInstanceState)

		logger.android("Setting the content view")
		setContentView(R.layout.details_activity)

		logger.android("Configuring the action bar")
		setSupportActionBar(findViewById(R.id.toolbar))
		supportActionBar?.apply {
			setDisplayShowTitleEnabled(false)
			setDisplayHomeAsUpEnabled(true)
		}

		logger.android("Retrieving the Pokémon instance")
		val position = intent.getIntExtra("position", -1)
		if (position < 0 || position >= app.pokemons.size) {
			Toast.makeText(this, "Position not in [0-${app.pokemons.size - 1}]", Toast.LENGTH_SHORT).show()
			finish()
			return
		}
		val pokemon = app.pokemons[position]
		if (pokemon != null) {
			this.pokemon = pokemon
			updateUI()
		}
	}

	/** Updates the user interface. */
	private fun updateUI() {
		logger.draw("Setting the Pokémon icon")
		with(findViewById<ImageView>(R.id.icon)) {
			setImageBitmap(image)
			drawable.isFilterBitmap = false
			invalidate()
		}
		logger.draw("Setting the Pokémon name")
		with(findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)) {
			title = name
			invalidate()
		}
		if (stats != null) {
			logger.draw("Updating the stats")
			findViewById<MaterialButton>(R.id.stat_health_value).text = stats?.health.toString()
			findViewById<MaterialButton>(R.id.stat_attack_value).text = stats?.attack.toString()
			findViewById<MaterialButton>(R.id.stat_defense_value).text = stats?.defense.toString()
			findViewById<MaterialButton>(R.id.stat_special_attack_value).text = stats?.specialAttack.toString()
			findViewById<MaterialButton>(R.id.stat_special_defense_value).text = stats?.specialDefense.toString()
			findViewById<MaterialButton>(R.id.stat_speed_value).text = stats?.speed.toString()
			findViewById<MaterialButton>(R.id.stat_height_value).text = stats?.height.toString()
			findViewById<MaterialButton>(R.id.stat_weight_value).text = stats?.weight.toString()
			findViewById<MaterialButton>(R.id.stat_types_value).text = TextUtils.join(", ", stats?.types!!)
			findViewById<MaterialButton>(R.id.stat_abilities_value).text = TextUtils.join(", ", stats?.abilities!!)
			findViewById<MaterialButton>(R.id.stat_items_value).text = TextUtils.join(", ", stats?.items!!)
			findViewById<MaterialButton>(R.id.stat_movements_value).text = TextUtils.join(", ", stats?.movements!!)
		}
	}

	/**
	 * Triggered when an option is selected.
	 *
	 * @param item The selected menu item.
	 * @return Whether the event has been processed or not.
	 */
	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
		android.R.id.home -> {
			onBackPressed()
			true
		}
		else -> super.onOptionsItemSelected(item)
	}

}
