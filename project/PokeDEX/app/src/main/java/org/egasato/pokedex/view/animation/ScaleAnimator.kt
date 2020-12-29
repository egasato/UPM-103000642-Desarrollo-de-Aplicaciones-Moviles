package org.egasato.pokedex.view.animation

import android.animation.Animator
import android.animation.ValueAnimator
//import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.egasato.pokedex.lib.util.Single
import org.egasato.pokedex.lib.view.animation.LoopValueAnimator
import org.egasato.pokedex.log.PokeLogger
import java.util.concurrent.TimeUnit

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class */
private val CLASS = ScaleAnimator::class.java.canonicalName

/** The duration of the scale animation. */
private val ANIM_MS_SCALE = TimeUnit.MILLISECONDS.convert(20L, TimeUnit.SECONDS)

/**
 * The animator of the background image pattern scale.
 *
 * @param scaleDraw The drawing scale.
 * @param scaleBmp  The bitmap scale.
 * @param minScale  The minimum scale.
 * @param maxScale  The maximum scale.
 * @param minDelay  The minimum delay.
 * @param maxDelay  The maximum delay.
 * @see LoopValueAnimator
 * @see ValueAnimator.AnimatorUpdateListener
 */
class ScaleAnimator(
	@JvmField private val scaleDraw: Single<Float>,
	@JvmField private val scaleBmp: Single<Float>,
	@JvmField private val minScale: Single<Float>,
	@JvmField private val maxScale: Single<Float>,
	@JvmField private val minDelay: Single<Long>,
	@JvmField private val maxDelay: Single<Long>
) : LoopValueAnimator(), ValueAnimator.AnimatorUpdateListener {

	/** The publish subject. */
	private val subject = PublishSubject.create<Float>()

//	/** The observable data. */
//	@JvmField
//	val observable = subject as Observable<Float>

	/** The minimum delay. */
	override val delayMin
		get() = minDelay

	/** The maximum delay. */
	override val delayMax
		get() = maxDelay

	/**
	 * Configures the instance and adds the listener.
	 *
	 * The listener updates the both offsets using the movement property.
	 */
	init {
		logger.cycle { "Creating an instance of $CLASS" }
		setFloatValues(*nextFloatValues())
		duration = ANIM_MS_SCALE
		startDelay = nextDelay()
		addUpdateListener(this)
		addListener(this)
	}

	/**
	 * Computes the next delay.
	 *
	 * @return The next delay.
	 */
	override fun nextDelay(): Long {
		return super.nextDelay().also {
			logger.event { "Running scheduled in $it ms" }
		}
	}

	/**
	 * Computes the next float values.
	 *
	 * @return The next float values.
	 */
	private fun nextFloatValues(): FloatArray {
		val next = minScale.first + (Math.random() * (maxScale.first - minScale.first)).toFloat()
		logger.event { "Next scale will be $next" }
		return floatArrayOf(scaleDraw.first * scaleBmp.first, next)
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
	 * Restarts the animator to animate to a new value.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationEnd(animation: Animator?) {
		logger.event("Ending animation")
		setFloatValues(*nextFloatValues())
		startDelay = nextDelay()
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
	 * Triggered when a new value is computed during the animation.
	 *
	 * Updates the mutable data.
	 *
	 * @param animation The animator.
	 */
	override fun onAnimationUpdate(animation: ValueAnimator?) {
		val scale = animatedValue as Float
		scaleDraw.first = scale / scaleBmp.first
		subject.onNext(scale)
	}

}
