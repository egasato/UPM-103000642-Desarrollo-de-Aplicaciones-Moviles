package org.egasato.pokedex.lib.graphics.drawable

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.withTranslation

/**
 * A [drawable][Drawable] using [tiles][Shader.TileMode].
 *
 * @param drawable  The drawable.
 * @param scale     The scale.
 * @param tileModeX The tiling mode of the X axis.
 * @param tileModeY The tiling mode of the Y axis.
 * @see Drawable
 * @author Esaú García Sánchez-Torija
 */
class TileDrawable(
	drawable: Drawable? = null,
	scale: Float = 1f,
	tileModeX: Shader.TileMode = Shader.TileMode.REPEAT,
	tileModeY: Shader.TileMode = Shader.TileMode.REPEAT
) : Drawable() {

	/** The maximum offset in the X axis. */
	private var limitX = 0f
		set(value) { field = value.coerceAtLeast(0f) }

	/** The maximum offset in the Y axis. */
	private var limitY = 0f
		set(value) { field = value.coerceAtLeast(0f) }

	/** The drawable. */
	var drawable = drawable
		set(value) {
			field = value
			if (value == null) {
				limitX = 0f
				limitY = 0f
				bitmap = null
			} else {
				limitX = value.intrinsicWidth * scale
				limitY = value.intrinsicHeight * scale
				bitmap = value.toScaledBitmap(scale)
			}
		}

	/** The scale. */
	var scale = scale
		set(value) {
			field = value
			val tmp = drawable
			if (tmp == null) {
				limitX = 0f
				limitY = 0f
				bitmap = null
			} else {
				limitX = tmp.intrinsicWidth * value
				limitY = tmp.intrinsicHeight * value
				bitmap = tmp.toScaledBitmap(value)
			}
		}

	/** The tile mode in the X axis. */
	var tileModeX = tileModeX
		set(value) {
			field = value
			bitmap = bitmap
		}

	/** The tile mode in the Y axis. */
	var tileModeY = tileModeY
		set(value) {
			field = value
			bitmap = bitmap
		}

	/** The bitmap. */
	internal var bitmap: Bitmap? = null
		set(value) {
			field = value
			shader = value?.let { BitmapShader(value, tileModeX, tileModeY) }
		}

	/** The shader. */
	private var shader: Shader? = null
		set(value) {
			field = value
			paint.shader = value
		}

	/** The paint object used to draw. */
	private val paint = Paint()

	/** The offset in the X axis. */
	var offsetX = 0f
		set(value) {
			field = value % limitX
		}

	/** The offset in the Y axis. */
	var offsetY = 0f
		set(value) {
			field = value % limitY
		}

	/**
	 * Draws the tiled drawable.
	 *
	 * @param canvas The canvas to draw on.
	 */
	override fun draw(canvas: Canvas) {
		canvas.withTranslation(offsetX, offsetY) {
			canvas.drawPaint(paint)
		}
	}

	/**
	 * Sets the alpha value.
	 *
	 * @param alpha The alpha value.
	 */
	override fun setAlpha(alpha: Int) {
		paint.alpha = alpha
	}

	/**
	 * Sets the color filter.
	 *
	 * @param colorFilter The color filter.
	 */
	override fun setColorFilter(colorFilter: ColorFilter?) {
		paint.colorFilter = colorFilter
	}

	/**
	 * Returns a [pixel format][PixelFormat] that supports translucency.
	 *
	 * @return The opacity support.
	 */
	override fun getOpacity() = PixelFormat.TRANSLUCENT

}

////////////////////////////////////////////////////// EXTENSIONS //////////////////////////////////////////////////////

/**
 * Converts a [drawable][Drawable] into a [bitmap][Bitmap] of the given [scale].
 *
 * @param scale The scale.
 * @return The scaled bitmap.
 */
private fun Drawable.toScaledBitmap(scale: Float): Bitmap {
	if (this is TileDrawable) {
		val drw = drawable
		if (drw != null) {
			return drw.toScaledBitmap(scale)
		} else {
			val bmp = bitmap
			if (bmp != null) {
				val width = (intrinsicWidth * scale).toInt()
				val height = (intrinsicHeight * scale).toInt()
				Bitmap.createScaledBitmap(bmp, width, height, true)
			}
		}
	}
	return if (this is BitmapDrawable) {
		if (scale == 1f) {
			bitmap
		} else {
			val width = (intrinsicWidth * scale).toInt()
			val height = (intrinsicHeight * scale).toInt()
			Bitmap.createScaledBitmap(bitmap, width, height, true)
		}
	} else {
		val width = (intrinsicWidth * scale).toInt()
		val height = (intrinsicHeight * scale).toInt()
		val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)
		setBounds(0, 0, width, height)
		draw(canvas)
		bitmap
	}
}
