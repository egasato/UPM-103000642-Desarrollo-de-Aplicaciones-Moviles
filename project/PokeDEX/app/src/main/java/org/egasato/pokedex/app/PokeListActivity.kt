package org.egasato.pokedex.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.egasato.pokedex.R
import org.egasato.pokedex.lib.util.Pair
import org.egasato.pokedex.lib.util.Single
import org.egasato.pokedex.lib.view.animation.CompatValueAnimator
import org.egasato.pokedex.lib.widget.TileImageView
import org.egasato.pokedex.lifecycle.PokeViewModel
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.recyclerview.PokeListAdapter
import org.egasato.pokedex.view.animation.MovementAnimator
import org.egasato.pokedex.view.animation.OffsetAnimator
import org.egasato.pokedex.view.animation.ScaleAnimator
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class. */
private val CLASS = PokeListActivity::class.java.canonicalName

/** The minimum scale. */
private const val MIN_SCALE = 2f

/** The maximum scale. */
private const val MAX_SCALE = 4f

/** The scale delays. */
private val DELAY_SCALE = arrayOf(40L, 50L).map {
	TimeUnit.MILLISECONDS.convert(it, TimeUnit.SECONDS)
}.map(Single.Companion::create)

/** The movement delays. */
private val DELAY_MOVEMENT = arrayOf(15L, 30L).map {
	TimeUnit.MILLISECONDS.convert(it, TimeUnit.SECONDS)
}.map(Single.Companion::create)

/**
 * The list [activity][AppCompatActivity].
 *
 * Shows the list with all the Pokémon.
 *
 * @author Esaú García Sánchez-Torija
 */
class PokeListActivity : AppCompatActivity(), PokeApplication.Aware {

	/** The view model of the authentication activity. */
	private val model by viewModels<PokeViewModel>()

	/** The bitmap scale value. */
	private lateinit var scaleBmp: Single<Float>

	/** The drawing scale value. */
	private lateinit var scaleDraw: Single<Float>

	/** The maximum scale value. */
	private lateinit var scaleMax: Single<Float>

	/** The minimum scale value. */
	private lateinit var scaleMin: Single<Float>

	/** The movement values. */
	private lateinit var movement: Pair<Float, Float>

	/** The offset values. */
	private lateinit var offset: Pair<Float, Float>

	/** The scale animator. */
	private lateinit var scaleAnimator: CompatValueAnimator

	/** The movement animator. */
	private lateinit var movementAnimator: CompatValueAnimator

	/** The scale animator. */
	private lateinit var offsetAnimator: CompatValueAnimator

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

		// Initialize the view model or update the activity if already initialized (orientation change)
		initViewModelOrUpdateActivity()

		logger.android("Configuring the adapter")
		findViewById<RecyclerView>(R.id.list).also {
			it.setHasFixedSize(true)
			it.itemAnimator?.changeDuration = 0
			it.adapter = PokeListAdapter(this, app.pokemons).apply {
				onClickListener = ::onPokemonClick
			}
		}
	}

	/**
	 * Executed when an item is clicked.
	 *
	 * @param view     The view.
	 * @param position The position.
	 */
	private fun onPokemonClick(view: View, position: Int) {
		logger.event { "Clicked Pokémon at position $position" }
		val intent = Intent(this, PokeDetailsActivity::class.java)
		intent.putExtra("position", position)
		startActivity(intent)
	}

	/** Initializes the view model or updates the activity state with it.  */
	private fun initViewModelOrUpdateActivity() {
		logger.android("Initializing view model or updating the activity state")
		logger.android("Synchronizing the background image state")
		findViewById<TileImageView>(R.id.background).also {
			if (!model.first) {
				logger.android("Initializing the view model (scale properties)")
				model.scaleBmp = Single.create(it.scaleBitmap.coerceAtLeast(MAX_SCALE))
				model.scaleDraw = Single.create(it.scaleDraw.coerceAtMost(1f))
				model.scaleMin = Single.create(MIN_SCALE)
				model.scaleMax = Single.create(MAX_SCALE)
			}
			scaleBmp = model.scaleBmp
			scaleDraw = model.scaleDraw
			scaleMax = model.scaleMax
			scaleMin = model.scaleMin
		}.also {
			if (!model.first) {
				logger.android("Initializing the view model (movement properties)")
				model.movement = Pair.create(0f, 0f).apply {
					val direction = Math.random() * Math.toRadians(360.0)
					first = cos(direction).toFloat()
					second = sin(direction).toFloat()
				}
			}
			movement = model.movement
		}.also {
			if (!model.first) {
				logger.android("Initializing the view model (offset properties)")
				model.offset = Pair.create(0f, 0f).apply {
					first = it.offsetX
					second = it.offsetY
				}
			}
			offset = model.offset
		}.also(::initAnimators)
		model.first = true
	}

	/**
	 * Initializes the animators.
	 *
	 * @param background The background image view.
	 */
	private fun initAnimators(background: TileImageView) {
		logger.android("Creating the animators")
		scaleAnimator = ScaleAnimator(
			scaleBmp = scaleBmp, scaleDraw = scaleDraw,
			maxScale = scaleMax, minScale = scaleMin,
			minDelay = DELAY_SCALE[0], maxDelay = DELAY_SCALE[1]
		)
		movementAnimator = MovementAnimator(
			movement = movement,
			minDelay = DELAY_MOVEMENT[0], maxDelay = DELAY_MOVEMENT[1]
		)
		offsetAnimator = OffsetAnimator(
			offset = offset,
			movement = movement,
			scale = object : Single<Float> {
				override var first: Float
					get() = scaleDraw.first * scaleBmp.first
					set(_) {}
			}
		).apply {
			observable.subscribe {
				background.scaleDraw = scaleDraw.first
				background.offsetX += offset.first
				background.offsetY += offset.second
				background.invalidate()
			}
		}
	}

	/** Starts the animators. */
	override fun onResume() {
		logger.android("Resuming the activity")
		super.onResume()
		logger.android("Resuming the animators")
		scaleAnimator.play()
		movementAnimator.play()
		offsetAnimator.play()
	}

	/** Stops the animators. */
	override fun onDestroy() {
		logger.android("Stopping the animators")
		scaleAnimator.stop()
		movementAnimator.stop()
		offsetAnimator.stop()
		logger.android("Destroying the activity")
		super.onDestroy()
	}

}
