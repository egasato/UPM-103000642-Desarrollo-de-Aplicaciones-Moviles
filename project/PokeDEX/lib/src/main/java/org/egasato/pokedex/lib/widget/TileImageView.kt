package org.egasato.pokedex.lib.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.withScale
import org.egasato.pokedex.lib.R
import org.egasato.pokedex.lib.graphics.drawable.TileDrawable

/** The simple name of the class. */
private val NAME = TileImageView::class.java.simpleName

/** The style of the element. */
private val STYLE = R.styleable.TileImageView

/** The style of the offset in the X axis. */
private val STYLE_OFFSET_X = R.styleable.TileImageView_offsetX

/** The style of the offset in the Y axis. */
private val STYLE_OFFSET_Y = R.styleable.TileImageView_offsetY

/** The style of the bitmap scale transformation. */
private val STYLE_SCALE_BITMAP = R.styleable.TileImageView_scaleBitmap

/** The style of the drawing scale transformation. */
private val STYLE_SCALE_DRAW = R.styleable.TileImageView_scaleDraw

/** The style of the tile mode in the X axis. */
private val STYLE_TILE_MODE_X = R.styleable.TileImageView_tileModeX

/** The style of the tile mode in the Y axis. */
private val STYLE_TILE_MODE_Y = R.styleable.TileImageView_tileModeY

/** The default offset in the X axis. */
private const val DEFAULT_OFFSET_X = 0f

/** The default offset in the Y axis. */
private const val DEFAULT_OFFSET_Y = 0f

/** The default bitmap scale transformation. */
private const val DEFAULT_SCALE_BITMAP = 1f

/** The default drawing scale transformation. */
private const val DEFAULT_SCALE_DRAW = 1f

/** The default tile mode in the X axis. */
private val DEFAULT_TILE_MODE_X = Shader.TileMode.REPEAT.ordinal

/** The default tile mode in the Y axis. */
private val DEFAULT_TILE_MODE_Y = Shader.TileMode.REPEAT.ordinal

/** The available tile modes. */
private val TILE_VALUES = Shader.TileMode.values()

/**
 * An [image view][AppCompatImageView] that forces the use of [tile drawables][TileDrawable].
 *
 * It overrides the [setImageDrawable] method to use convert the drawables into [tile drawables][TileDrawable].
 *
 * @param context      The context.
 * @param attrs        The attribute set.
 * @param defStyleAttr The default attributes.
 * @see AppCompatImageView
 * @author Esaú García Sánchez-Torija
 */
class TileImageView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

	/** A cached instance of the tile drawable object. */
	private var tileDrawableCached: TileDrawable? = null

	/** The [tile drawable][TileDrawable] instance. */
	private val tileDrawable: TileDrawable
		get() {
			if (tileDrawableCached == null) {
				tileDrawableCached = TileDrawable(
					scale = DEFAULT_SCALE_BITMAP,
					tileModeX = TILE_VALUES[DEFAULT_TILE_MODE_X],
					tileModeY = TILE_VALUES[DEFAULT_TILE_MODE_Y]
				)
			}
			return tileDrawableCached!!
		}

	/** The scale of the drawable. */
	var scaleBitmap: Float by tileDrawable::scale

	/** The canvas scale transformation. */
	var scaleDraw: Float = 1f

	/** The tile mode in the X axis. */
	var tileModeX: Shader.TileMode by tileDrawable::tileModeX

	/** The tile mode in the Y axis. */
	var tileModeY: Shader.TileMode by tileDrawable::tileModeY

	/** The offset in the X axis. */
	var offsetX: Float by tileDrawable::offsetX

	/** The offset in the Y axis. */
	var offsetY: Float by tileDrawable::offsetY

	/** Processes the view attributes. */
	init {
		val arr = context.obtainStyledAttributes(attrs, STYLE, defStyleAttr, 0)
		try {
			val dpi = context.resources.displayMetrics.density
			offsetX = arr.getDimension(STYLE_OFFSET_X, DEFAULT_OFFSET_X) / dpi
			offsetY = arr.getDimension(STYLE_OFFSET_Y, DEFAULT_OFFSET_Y) / dpi
			scaleDraw = arr.getFloat(STYLE_SCALE_DRAW, DEFAULT_SCALE_DRAW)
			scaleBitmap = arr.getFloat(STYLE_SCALE_BITMAP, DEFAULT_SCALE_BITMAP)
			scaleDraw = arr.getFloat(STYLE_SCALE_DRAW, DEFAULT_SCALE_DRAW)
			val modeX = arr.getInt(STYLE_TILE_MODE_X, DEFAULT_TILE_MODE_X)
			tileModeX = TILE_VALUES[modeX]
			val modeY = arr.getInt(STYLE_TILE_MODE_Y, DEFAULT_TILE_MODE_Y)
			tileModeY = TILE_VALUES[modeY]
		} finally {
			arr.recycle()
		}
	}

	////////////////////////////////////////////// OVERRIDDEN METHODS //////////////////////////////////////////////

	/**
	 * Draws the image with the specified scale.
	 *
	 * @param canvas The canvas to draw on.
	 */
	override fun onDraw(canvas: Canvas?) {
		canvas?.withScale(scaleDraw, scaleDraw, width / 2f, height / 2f) {
			super.onDraw(canvas)
		}
	}

	/**
	 * Sets the [drawable][Drawable] to be drawn as a [tile drawable][TileDrawable].
	 *
	 * @param drawable The [drawable][Drawable].
	 */
	override fun setImageDrawable(drawable: Drawable?) {
		tileDrawable.drawable = drawable
		super.setImageDrawable(tileDrawable)
	}

	/**
	 * Converts the instance into an identifiable string.
	 *
	 * @return The string representation.
	 */
	override fun toString() = "$NAME@%h".format(hashCode())

}
