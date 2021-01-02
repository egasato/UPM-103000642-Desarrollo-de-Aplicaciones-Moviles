package org.egasato.pokedex.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.egasato.pokedex.R
import org.egasato.pokedex.lifecycle.PokeAuthViewModel
import org.egasato.pokedex.log.PokeLogger

/** The Kotlin logger object. */
private val logger = PokeLogger.logger {}

/** The complete name of the class */
private val CLASS = PokeLoginFragment::class.java.canonicalName

/**
 * The [fragment][Fragment] that manages the login form.
 *
 * @author Esaú García Sánchez-Torija
 */
class PokeLoginFragment : Fragment() {

	/** The view model of the activity. */
	private val model: PokeAuthViewModel by activityViewModels()

	/** Logs the creation of the instance. */
	init {
		logger.cycle { "Creating an instance of $CLASS" }
	}

	/**
	 * Triggered when the fragment is attached to a [context][Context].
	 *
	 * Updates the [active fragment][PokeAuthViewModel.fragment] from the [view model][PokeAuthViewModel].
	 */
	override fun onAttach(context: Context) {
		logger.android("[PokeLoginFragment] Attaching the fragment")
		super.onAttach(context)
	}

	/**
	 * Inflates the view with the fragment layout.
	 *
	 * @param inflater           The layout inflater.
	 * @param container          The view group where the view is inflated.
	 * @param savedInstanceState The saved state.
	 * @return The fragment view.
	 */
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		logger.android("[PokeLoginFragment] Inflating the view")
		model.fragment.value = R.id.login_fragment
		val view = inflater.inflate(R.layout.login_fragment, container, false)
		view.findViewById<EditText>(R.id.username).apply {
			setText(model.username.value)
			error = model.state.username.value
			model.state.username.observe(viewLifecycleOwner, { err -> error = err })
			doOnTextChanged { text, _, _, _ ->
				logger.event { "Username changed to: $text" }
				model.username.value = text
				model.state.username.value = null
			}
		}
		view.findViewById<EditText>(R.id.password).apply {
			setText(model.password.value)
			error = model.state.password.value
			model.state.password.observe(viewLifecycleOwner, { err -> error = err })
			doOnTextChanged { text, _, _, _ ->
				logger.event { "Password changed to: $text" }
				model.password.value = text
				model.state.password.value = null
			}
		}
		return view
	}

}
