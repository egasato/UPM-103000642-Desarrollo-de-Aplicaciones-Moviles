package org.egasato.pokedex.view.animation

import android.animation.Animator
import android.animation.ValueAnimator
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.egasato.pokedex.lib.util.Pair
import org.egasato.pokedex.lib.util.Single
import org.egasato.pokedex.lib.view.animation.CompatValueAnimator
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class */
private val CLASS = OffsetAnimator::class.java.canonicalName

/**
 * The animator of the background image pattern scale.
 *
 * @param offset   The offset.
 * @param movement The movement.
 * @param scale    The mutable scale.
 * @see CompatValueAnimator
 * @see ValueAnimator.AnimatorUpdateListener
 */
class OffsetAnimator(
	@JvmField private val offset: Pair<Float, Float>,
	@JvmField private val movement: Pair<Float, Float>,
	@JvmField private val scale: Single<Float>
) : CompatValueAnimator(), Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

	/** The publish subject. */
	private val subject = PublishSubject.create<Pair<Float, Float>>()

	/** The observable data. */
	@JvmField
	val observable = subject as Observable<Pair<Float, Float>>

	/**
	 * Configures the instance and adds the listener.
	 *
	 * The listener updates the both offsets using the movement property.
	 */
	init {
		logger.cycle { "Creating an instance of $CLASS" }
		setFloatValues(0f, 1f)
		repeatMode = RESTART
		repeatCount = INFINITE
		duration = Long.MAX_VALUE
		addUpdateListener(this)
		addListener(this)
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
	 * Updates the [offset] with the [movement].
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationUpdate(animation: ValueAnimator?) {
		offset.first = movement.first * scale.first
		offset.second = movement.second * scale.first
		subject.onNext(offset)
	}

}
