package org.egasato.pokedex.lib.view.animation

import android.animation.ValueAnimator
import android.os.Build
import android.view.animation.LinearInterpolator

/**
 * A [value animator][ValueAnimator] that exposes a retro-compatible API.
 *
 * It saves the time of checking the build version code of the Android running the animation and always tries to resume
 * the animation if it [is paused][isPaused].
 *
 * @see ValueAnimator
 * @author Esaú García Sánchez-Torija
 */
open class CompatValueAnimator : ValueAnimator() {

	/** Sets the repeat count to 0 and the interpolator to be linear. */
	init {
		repeatCount = 0
		interpolator = LinearInterpolator()
	}

	/////////////////////////////////////////////////// METHODS ////////////////////////////////////////////////////

	/** Calls [pause] if supported, [cancel] otherwise. */
	fun stop() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isStarted) {
			pause()
		} else {
			cancel()
		}
	}

	/** Calls [resume] if supported, [start] otherwise. */
	fun play() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isPaused) {
			resume()
		} else {
			start()
		}
	}

}
