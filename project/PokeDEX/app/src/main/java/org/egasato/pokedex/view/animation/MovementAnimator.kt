package org.egasato.pokedex.view.animation

import android.animation.Animator
import android.animation.ValueAnimator
//import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.egasato.pokedex.lib.util.Pair
import org.egasato.pokedex.lib.util.Single
import org.egasato.pokedex.lib.view.animation.CompatValueAnimator
import org.egasato.pokedex.log.PokeLogger
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class */
private val CLASS = MovementAnimator::class.java.canonicalName

/** The duration of the scale animation. */
private val ANIM_MS_MOVE = TimeUnit.MILLISECONDS.convert(3L, TimeUnit.SECONDS)

/**
 * The animator of the background image pattern direction.
 *
 * @param movement The mutable direction.
 * @param minDelay The minimum delay.
 * @param maxDelay The maximum delay.
 * @see CompatValueAnimator
 * @see Animator.AnimatorListener
 * @see ValueAnimator.AnimatorUpdateListener
 */
class MovementAnimator(
	@JvmField private val movement: Pair<Float, Float>,
	@JvmField private val minDelay: Single<Long>,
	@JvmField private val maxDelay: Single<Long>
) : CompatValueAnimator(), Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

	/** The publish subject. */
	private val subject = PublishSubject.create<Pair<Float, Float>>()

//	/** The observable data. */
//	@JvmField
//	val observable = subject as Observable<Pair<Float, Float>>

	/** The original movement. */
	private val _from = Pair.create(movement.first, movement.second)

	/** The new movement. */
	private val _to = Pair.create(movement.first, movement.second)

	/** The delta of the movement. */
	private val _delta = Pair.create(0f, 0f)

	/** The original movement. */
	private var from: Pair<Float, Float>
		get() = _from
		set(value) {
			_from.first = value.first
			_from.second = value.second
			_delta.first = _to.first - value.first
			_delta.second = _to.second - value.second
		}

	/** The new movement. */
	private var to: Pair<Float, Float>
		get() = _to
		set(value) {
			_to.first = value.first
			_to.second = value.second
			_delta.first = value.first - _from.first
			_delta.second = value.second - _from.second
		}

	/** The delta of the movement. */
	private var delta: Pair<Float, Float>
		get() = _delta
		set(value) {
			_delta.first = value.first
			_delta.second = value.second
			_to.first = _from.first + value.first
			_to.second = _from.second + value.second
		}

	/**
	 * Configures the instance and adds the listener.
	 *
	 * The listener updates the both the direction of the movement.
	 */
	init {
		logger.cycle { "Creating an instance of $CLASS" }
		setFloatValues(0f, 1f)
		duration = ANIM_MS_MOVE
		nextDelay()
		nextRadians()
		addUpdateListener(this)
		addListener(this)
	}

	/** Computes the next delay. */
	private fun nextDelay() {
		startDelay = (minDelay.first + (maxDelay.first - minDelay.first) * Math.random()).toLong()
		logger.event { "Running scheduled in $startDelay ms" }
	}

	/** Computes the next angle. */
	private fun nextRadians() {
		val angle = Math.random() * 360.0
		val radians = Math.toRadians(angle)
		logger.event { "Next direction will be ${angle}° ($radians radians)" }
		val new = Pair.create(first = cos(radians).toFloat(), second = sin(radians).toFloat())
		from = movement
		to = new
	}

	/**
	 * Triggered when the animation starts.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationStart(animation: Animator?) {
		logger.event("Starting animation")
	}

	/**
	 * Triggered when the animation ends.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationEnd(animation: Animator?) {
		logger.event("Ending animation")
		nextDelay()
		nextRadians()
		play()
	}

	/**
	 * Triggered when the animation is cancelled.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationCancel(animation: Animator?) {
		logger.event("Cancelling animation")
	}

	/**
	 * Triggered when the animation repeats.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationRepeat(animation: Animator?) {
		logger.event("Repeating animation")
	}

	/**
	 * Updates the mutable data.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationUpdate(animation: ValueAnimator?) {
		movement.first = from.first + delta.first * animatedFraction
		movement.second = from.second + delta.second * animatedFraction
		subject.onNext(movement)
	}

}
