package org.egasato.pokedex.app

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.androidnetworking.AndroidNetworking
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.egasato.pokedex.R
import org.egasato.pokedex.lib.util.Pair
import org.egasato.pokedex.lib.util.Single
import org.egasato.pokedex.lib.view.animation.CompatValueAnimator
import org.egasato.pokedex.lib.widget.TileImageView
import org.egasato.pokedex.lifecycle.PokeAuthViewModel
import org.egasato.pokedex.log.PokeLogger
import org.egasato.pokedex.repo.AuthRepository
import org.egasato.pokedex.view.animation.MovementAnimator
import org.egasato.pokedex.view.animation.OffsetAnimator
import org.egasato.pokedex.view.animation.ScaleAnimator
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class */
private val CLASS = PokeMainActivity::class.java.canonicalName

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

/** The expression that validates the username. */
private val REGEX_USERNAME = Regex("[a-zA-Z0-9]+")

/** The expression that validates the password. */
private val REGEX_PASSWORD = Regex(".{4,}")

/** The expression that validates the email. */
private val REGEX_EMAIL = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")

/**
 * The authentication [activity][AppCompatActivity].
 *
 * It allows a user to authenticate and register.
 * @author Esaú García Sánchez-Torija
 */
class PokeAuthActivity : AppCompatActivity(), PokeApplication.Aware {

	/** The view model of the authentication activity. */
	private val model by viewModels<PokeAuthViewModel>()

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

	/** The navigation controller. */
	private lateinit var controller: NavController

	/** Done drawable. */
	private lateinit var doneDrw: Drawable

	/** Done bitmap. */
	private lateinit var doneBmp: Bitmap

	/** Done background color. */
	private var doneCol: Int = 0

	/** Error drawable. */
	private lateinit var errDrw: Drawable

	/** Error bitmap. */
	private lateinit var errBmp: Bitmap

	/** Error background color. */
	private var errCol: Int = 0

	/**
	 * Loads the UI and creates the view model.
	 *
	 * @param savedInstanceState The saved state of this instance.
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		logger.android("Creating an instance of $CLASS")
		super.onCreate(savedInstanceState)

		logger.android("Setting the content view")
		setContentView(R.layout.auth_activity)

		logger.android("Configuring the action bar")
		supportActionBar?.apply {
			setCustomView(R.layout.toolbar)
			setDisplayShowTitleEnabled(false)
			setDisplayHomeAsUpEnabled(false)
			displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
		}

		// Setup the navigation controller
		val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		controller = fragment.navController
		controller.setGraph(R.navigation.nav_graph)

		// Initialize the view model or update the activity if already initialized (orientation change)
		initViewModelOrUpdateActivity()

		// Update the buttons based on the active fragment
		model.fragment.observe(this) {
			when (it) {
				R.id.login_fragment -> {
					findViewById<CircularProgressButton>(R.id.action).setText(R.string.action_login)
					findViewById<MaterialButton>(R.id.nav).setText(R.string.nav_login)
				}
				R.id.signup_fragment -> {
					findViewById<CircularProgressButton>(R.id.action).setText(R.string.action_signup)
					findViewById<MaterialButton>(R.id.nav).setText(R.string.nav_signup)
				}
				else -> throw IllegalStateException("Application state is wrong, I don't know what to do")
			}
		}

		// Initialize networking
		AndroidNetworking.initialize(applicationContext)

		// Initialize icons
		doneDrw = ResourcesCompat.getDrawable(resources, R.drawable.done, null)!!
		doneBmp = doneDrw.toBitmap(24, 24, null)
		doneCol = ResourcesCompat.getColor(resources, R.color.green_900, null)
		errDrw = ResourcesCompat.getDrawable(resources, R.drawable.clear, null)!!
		errBmp = errDrw.toBitmap(24, 24, null)
		errCol = ResourcesCompat.getColor(resources, R.color.red_900, null)
	}

	/**
	 * Triggered when the action button is clicked.
	 *
	 * Checks the parameters.
	 *
	 * @param view The button view.
	 */
	@Suppress("UNUSED_PARAMETER")
	fun onActionClick(view: View) {
		val btn = view as CircularProgressButton
		btn.isEnabled = false
		if (checkData()) {
			app.preferences.username = model.username.value.toString()
			btn.startAnimation()
			val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
			when (controller.currentDestination!!.id) {
				R.id.login_fragment -> lifecycleScope.launch(Dispatchers.IO) {
					val result = login()
					when (result?.code) {
						0 -> {
							logger.event { "The token is: ${result.token}" }
							app.preferences.token = result.token
							runOnUiThread {
								toast.setText(R.string.ok_login)
								toast.show()
								switchToList()
								btn.doneLoadingAnimation(doneCol, doneBmp)
								btn.isEnabled = true
							}
						}
						-1 -> runOnUiThread {
							toast.setText(R.string.err_invalid)
							toast.show()
							btn.doneLoadingAnimation(errCol, errBmp)
							btn.isEnabled = true
						}
						-2 -> runOnUiThread {
							toast.setText(R.string.err_login)
							toast.show()
							btn.doneLoadingAnimation(errCol, errBmp)
							btn.isEnabled = true
						}
						else -> runOnUiThread {
							toast.setText(R.string.err_parse)
							toast.show()
							btn.doneLoadingAnimation(errCol, errBmp)
							btn.isEnabled = true
						}
					}
					delay(1_000)
					runOnUiThread {
						btn.revertAnimation()
					}
				}
				R.id.signup_fragment -> lifecycleScope.launch(Dispatchers.IO) {
					val result = signup()
					when (result?.code) {
						0 -> runOnUiThread {
							toast.setText(R.string.ok_signup)
							toast.show()
							switchToLogin()
							btn.doneLoadingAnimation(doneCol, doneBmp)
							btn.isEnabled = true
						}
						-1 -> runOnUiThread {
							toast.setText(R.string.err_invalid)
							toast.show()
							btn.doneLoadingAnimation(errCol, errBmp)
							btn.isEnabled = true
						}
						-2 -> runOnUiThread {
							toast.setText(R.string.err_signup)
							toast.show()
							switchToLogin()
							btn.doneLoadingAnimation(doneCol, doneBmp)
							btn.isEnabled = true
						}
						else -> runOnUiThread {
							toast.setText(R.string.err_parse)
							toast.show()
							btn.doneLoadingAnimation(errCol, errBmp)
							btn.isEnabled = true
						}
					}
					delay(1_000)
					runOnUiThread {
						btn.revertAnimation()
					}
				}
				else -> throw IllegalStateException("Application state is wrong, I don't know what to do")
			}
		} else {
			btn.isEnabled = true
		}
	}

	/**
	 * Triggered when the navigation button is clicked.
	 *
	 * Switches to the other fragment.
	 *
	 * @param view The button view.
	 */
	@Suppress("UNUSED_PARAMETER")
	fun onNavClick(view: View) {
		when (controller.currentDestination!!.id) {
			R.id.login_fragment -> switchToSignup()
			R.id.signup_fragment -> switchToLogin()
			else -> throw IllegalStateException("Application state is wrong, I don't know what to do")
		}
	}

	/** Switches to the sign-up fragment. */
	private fun switchToSignup() {
		val action = PokeLoginFragmentDirections.switchToSignup()
		controller.navigate(action)
	}

	/** Switches to the login fragment. */
	private fun switchToLogin() {
		val action = PokeSignupFragmentDirections.switchToLogin()
		controller.navigate(action)
	}

	/** Switches to the list activity. */
	private fun switchToList() {
		val intent = Intent(this, PokeListActivity::class.java)
		startActivity(intent)
		finish()
	}

	/** Initializes the view model or updates the activity state with it.  */
	private fun initViewModelOrUpdateActivity() {
		logger.android("Initializing view model or updating the activity state")
		logger.android("Synchronizing the background image state")
		findViewById<TileImageView>(R.id.background).also {
			if (!model.wasAlive) {
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
			if (!model.wasAlive) {
				logger.android("Initializing the view model (movement properties)")
				model.movement = Pair.create(0f, 0f).apply {
					val direction = Math.random() * Math.toRadians(360.0)
					first = cos(direction).toFloat()
					second = sin(direction).toFloat()
				}
			}
			movement = model.movement
		}.also {
			if (!model.wasAlive) {
				logger.android("Initializing the view model (offset properties)")
				model.offset = Pair.create(0f, 0f).apply {
					first = it.offsetX
					second = it.offsetY
				}
			}
			offset = model.offset
		}.also(::initAnimators)
		if (!model.wasAlive) {
			model.username.value = ""
			model.password.value = ""
			model.email.value = ""
			model.repeat.value = ""
		}
		model.wasAlive = true
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

	/**
	 * Checks the input data.
	 *
	 * @return Whether the data is valid or not.
	 */
	private fun checkData(): Boolean {
		var focused = 0
		val validUsername = model.username.value?.matches(REGEX_USERNAME)
		val validPassword = model.password.value?.matches(REGEX_PASSWORD)
		if (validUsername != true) {
			model.state.username.value = getString(R.string.err_username)
			findViewById<EditText>(R.id.username).requestFocus()
			focused = 1
		}
		if (validPassword != true) {
			model.state.password.value = getString(R.string.err_password)
			if (focused > 3 || focused == 0) {
				findViewById<EditText>(R.id.password).requestFocus()
				focused = 3
			}
		}
		if (controller.currentDestination!!.id == R.id.signup_fragment) {
			val validEmail = model.email.value?.matches(REGEX_EMAIL)
			val validRepeat = model.repeat.value?.matches(REGEX_PASSWORD)
			if (validEmail != true) {
				model.state.email.value = getString(R.string.err_email)
				if (focused > 2 || focused == 0) {
					findViewById<EditText>(R.id.email).requestFocus()
					focused = 2
				}
			}
			if (validRepeat != true) {
				model.state.repeat.value = getString(R.string.err_password)
				if (focused > 4 || focused == 0) {
					findViewById<EditText>(R.id.repeat_password).requestFocus()
				}
			} else if (!model.password.value!!.toString().contentEquals(model.repeat.value!!)) {
				model.state.repeat.value = getString(R.string.err_repeat)
				if (focused > 4 || focused == 0) {
					findViewById<EditText>(R.id.repeat_password).requestFocus()
				}
			}
		}
		return focused == 0
	}

	/**
	 * Signs up with the username, password and email stored in the view model.
	 *
	 * @return Whether the sign up succeeded or not.
	 */
	private fun signup() = with (model) {
		val result = AuthRepository.signup(
			username = username.value!!.toString(),
			password = password.value!!.toString(),
			email = email.value!!.toString()
		)
		logger.event { "Server message is: ${result?.message}" }
		result
	}

	/**
	 * Logs in with the username and password stored in the view model.
	 *
	 * @return Whether the login succeeded or not.
	 */
	private fun login() = with (model) {
		val result = AuthRepository.login(
			username = username.value!!.toString(),
			password = password.value!!.toString()
		)
		logger.event { "Server message is: ${result?.message}" }
		result
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
