package org.egasato.pokedex.lib.view.animation

import android.animation.Animator
import org.egasato.pokedex.lib.util.Single

/**
 * A [backward-compatible value animator][CompatValueAnimator] that [plays][play] again after [ending][onAnimationEnd].
 *
 * @see CompatValueAnimator
 * @see Animator.AnimatorListener
 * @author Esaú García Sánchez-Torija
 */
abstract class LoopValueAnimator : CompatValueAnimator(), Animator.AnimatorListener {

	/** The minimum start delay. */
	protected abstract val delayMin: Single<Long>

	/** The maximum start delay. */
	protected abstract val delayMax: Single<Long>

	/**
	 * Computes the next delay.
	 *
	 * @return The next delay.
	 */
	protected open fun nextDelay() = (delayMin.first + (delayMax.first - delayMin.first) * Math.random()).toLong()

	/**
	 * Triggered when the animation starts.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationStart(animation: Animator?) {}

	/**
	 * Triggered when the animation ends.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationEnd(animation: Animator?) {
		startDelay = nextDelay()
		play()
	}

	/**
	 * Triggered when the animation is cancelled.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationCancel(animation: Animator?) {}

	/**
	 * Triggered when the animation repeats.
	 *
	 * @param animation The [animator][Animator] instance.
	 */
	override fun onAnimationRepeat(animation: Animator?) {}

}
