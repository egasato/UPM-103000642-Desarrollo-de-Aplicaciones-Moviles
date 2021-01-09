package org.egasato.pokedex.app

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.egasato.pokedex.R
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.model.dm.Pokemon
import org.egasato.pokedex.repo.PokeRepository
import org.egasato.pokedex.view.animation.PokeItemViewHolder
import org.egasato.pokedex.view.animation.PokeLoadingViewHolder

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The type of a loading view holder. */
private const val VIEW_TYPE_LOADING = 0

/** The type of a loaded view holder. */
private const val VIEW_TYPE_LOADED = 1

/**
 * The recycler view adapter used to list all the Pokémon.
 *
 * @property context The context.
 * @property list    The list of Pokémon.
 * @author Esaú García Sánchez-Torija
 */
class PokeListAdapter(
	@JvmField val context: Context,
	@JvmField val list: MutableList<Pokemon?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	/**
	 * Creates the view holder for an item.
	 *
	 * @param parent   The parent view group.
	 * @param viewType The view type.
	 * @return The view holder.
	 */
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
		VIEW_TYPE_LOADED -> {
			val id = R.layout.pokemon_item
			val view = LayoutInflater.from(parent.context).inflate(id, parent, false)
			PokeItemViewHolder(view)
		}
		VIEW_TYPE_LOADING -> {
			val id = R.layout.pokemon_loading
			val view = LayoutInflater.from(parent.context).inflate(id, parent, false)
			PokeLoadingViewHolder(view)
		}
		else -> throw IllegalStateException("Unknown view type")
	}

	/**
	 * Executed when a view holder is bound.
	 *
	 * @param holder   The recycler view holder.
	 * @param position The recycler item position.
	 */
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		logger.event { "Binding Pokémon @ $position" }
		if (holder.itemViewType == VIEW_TYPE_LOADING) {
			GlobalScope.launch(Dispatchers.IO) {
				val pokemons = PokeRepository.pokemon(1, position)
				if (pokemons != null) {
					val max = position + pokemons.size
					for (i in position until max) {
						if (list[i] == null) {
							list[i] = pokemons[i - position]
						}
					}
					(context as PokeListActivity).runOnUiThread {
						notifyItemRangeChanged(position, pokemons.size)
					}
					for (i in position until max) {
						val pokemon = list[i]!!
						val req = AndroidNetworking.get("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png")
							.setPriority(Priority.LOW)
							.setBitmapConfig(Bitmap.Config.ARGB_8888)
							.build()
						logger.event { "Requesting: ${req.url}" }
						val res = req.executeForBitmap()
						if (res.isSuccess) {
							logger.event("Parsing icon @ $i...")
							val img = res.result as Bitmap
							list[i] = Pokemon.builder().apply {
								id = pokemon.id
								name = pokemon.name
								image = img
							}.build()
							context.runOnUiThread { notifyItemChanged(i) }
						} else {
							logger.event("Error parsing icon @ $i...")
						}
					}
				}
			}
		} else {
			if (holder !is PokeItemViewHolder) throw IllegalStateException("Unknown view holder")
			holder.id.text = list[position]!!.id.toString()
			holder.name.text = list[position]!!.name
			if (list[position]!!.image == null) {
				holder.icon.visibility = View.GONE
				holder.load.visibility = View.VISIBLE
			} else {
				holder.icon.setImageBitmap(list[position]!!.image)
				holder.icon.invalidate()
				holder.icon.visibility = View.VISIBLE
				holder.load.visibility = View.GONE
			}
		}
	}

	/**
	 * Returns the total number of Pokémon.
	 *
	 * @return The number of Pokémon.
	 */
	override fun getItemCount() = list.size

	/**
	 * Returns the view holder type.
	 *
	 * @param position The index of the item.
	 * @return The view holder type.
	 */
	override fun getItemViewType(position: Int) = if (list[position] == null) {
		VIEW_TYPE_LOADING
	} else {
		VIEW_TYPE_LOADED
	}

}
