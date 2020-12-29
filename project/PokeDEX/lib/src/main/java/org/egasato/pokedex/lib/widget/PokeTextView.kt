package org.egasato.pokedex.lib.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.withTranslation
import org.egasato.pokedex.lib.R
import kotlin.math.ceil

/** The simple name of the class. */
private val NAME = PokeTextView::class.java.simpleName

/** The style of the element. */
private val STYLE = R.styleable.PokeTextView

/** The style of the stroke width. */
private val STYLE_STROKE_WIDTH = R.styleable.PokeTextView_strokeWidth

/** The style of the stroke color. */
private val STYLE_STROKE_COLOR = R.styleable.PokeTextView_strokeColor

/** The style of the typeface. */
private val STYLE_TYPEFACE = R.styleable.PokeTextView_typeface

/** The default stroke width. */
private const val DEF_STROKE_WIDTH = 15f

/** The default stroke color. */
private const val DEF_STROKE_COLOR = Color.BLACK

/** The default typeface. */
private const val DEFAULT_TYPEFACE = "fonts/pokemon-solid.ttf"

/** The width fix (because Android computes incorrectly the width of the Pokemon typeface). */
private const val WIDTH_FIX = 8f

/** The height fix (because Android computes incorrectly the height of the Pokemon typeface). */
private const val HEIGHT_FIX = 7f

/**
 * A [text view][AppCompatTextView] that uses the official Pokémon typeface.
 *
 * @param context      The context.
 * @param attrs        The attribute set.
 * @param defStyleAttr The default attributes.
 * @see AppCompatTextView
 * @author Esaú García Sánchez-Torija
 */
class PokeTextView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

	/** The companion object used to expose the static constructors.  */
	companion object {

		/** The cached typefaces. */
		@JvmStatic
		private val typefaces = mutableMapOf<String,Typeface>()

		/**
		 * Loads a [typeface][Typeface] from the given [path].
		 *
		 * @param context The context.
		 * @param path    The path to the [typeface][Typeface] [path].
		 * @return The [typeface][Typeface].
		 */
		fun getOrLoadTypeface(context: Context, path: String): Typeface {
			var typeface = typefaces[path]
			if (typeface == null) {
				typeface = Typeface.createFromAsset(context.assets, path)
				typefaces[path] = typeface
			}
			return typeface!!
		}

	}

	/** The stroke width of the text. */
	private var strokeWidth = DEF_STROKE_WIDTH
		set(value) {
			field = value
			tx = strokeWidth / 2f
			ty = tx
		}

	/** The stroke color of the text. */
	private var strokeColor = DEF_STROKE_COLOR

	/** The canvas X translation. */
	private var tx = strokeWidth / 2f

	/** The canvas X translation. */
	private var ty = tx

	/** The original text color. */
	private var c = textColors.defaultColor

	/** The original stroke width. */
	private var w = paint.strokeWidth

	/** The original painting style. */
	private var s = paint.style

	/** Tracks if the view is being drawn. */
	private var drawing = false

	/** Processes the view attributes. */
	init {
		val arr = context.obtainStyledAttributes(attrs, STYLE, defStyleAttr, 0)
		try {
			val dpi = context.resources.displayMetrics.density
			strokeWidth = arr.getDimension(STYLE_STROKE_WIDTH, DEF_STROKE_WIDTH) / dpi
			strokeColor = arr.getColor(STYLE_STROKE_COLOR, DEF_STROKE_COLOR)
			val path = arr.getString(STYLE_TYPEFACE) ?: DEFAULT_TYPEFACE
			typeface = getOrLoadTypeface(context = context, path = path)
		} finally {
			arr.recycle()
		}
	}

	/**
	 * Fixes the measurement of the typeface.
	 *
	 * The Pokémon typeface has a "wrong" letter width that makes some letters overlap. That visual effect is
	 * desired but the [text view][AppCompatTextView] implementation clips the drawn text to the dimensions set by
	 * this method, which is very ugly.
	 *
	 * This method overrides the default behaviour and applies some extra dimensions to both the width and height.
	 * The correction is fixed (based on experimentation) but it scales with the stroke width.
	 *
	 * @param widthMeasureSpec The measurement specification of the width.
	 * @param widthMeasureSpec The measurement specification of the height.
	 */
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		val w = when (MeasureSpec.getMode(widthMeasureSpec)) {
			MeasureSpec.EXACTLY -> widthMeasureSpec
			else -> {
				val fix = WIDTH_FIX + ceil(strokeWidth * 1.5).toInt()
				val wNew = measuredWidth + strokeWidth + fix
				MeasureSpec.makeMeasureSpec(wNew.toInt(), MeasureSpec.EXACTLY)
			}
		}
		val h = when (MeasureSpec.getMode(heightMeasureSpec)) {
			MeasureSpec.EXACTLY -> heightMeasureSpec
			else -> {
				val fix = HEIGHT_FIX
				val hNew = measuredHeight + strokeWidth + fix
				MeasureSpec.makeMeasureSpec(hNew.toInt(), MeasureSpec.EXACTLY)
			}
		}
		if (w != widthMeasureSpec || h != heightMeasureSpec) {
			super.onMeasure(w, h)
		}
	}

	/**
	 * Draws the [text view][AppCompatTextView] with a stroke.
	 *
	 * Because the canvas does not allow using different colors for stroke and fill, it is drawn in two phases:
	 * - First it draws the bottom layer, which is the the text with a stroke. It is drawn using the
	 * [stroke color][strokeColor] and the [stroke width][strokeWidth].
	 * - Last it draws the text as it is usually done, without any alteration.
	 *
	 * @param canvas The canvas to draw on.
	 */
	override fun onDraw(canvas: Canvas?) {
		drawing = true
		canvas?.withTranslation(tx, ty) {
			setTextColor(strokeColor)
			with(paint) {
				style = Paint.Style.STROKE
				strokeWidth = this@PokeTextView.strokeWidth
			}
			super.onDraw(canvas)
			setTextColor(c)
			with(paint) {
				style = s
				strokeWidth = w
			}
			super.onDraw(canvas)
		}
		drawing = false
	}

	/**
	 * Prevents the [invalidate] call from propagating if the field [drawing] is `true`.
	 *
	 * The method [setTextColor], used inside the [onDraw] method, makes the view to be redrawn, which causes the UI
	 * thread to never stop drawing the view. To prevent this, this method does not allow invalidating the view
	 * until the [onDraw] method restores its value to false.
	 */
	override fun invalidate() {
		if (!drawing) super.invalidate()
	}

	/**
	 * Converts the instance into an identifiable string.
	 *
	 * @return The string representation.
	 */
	override fun toString() = "$NAME@%h".format(hashCode())

}
