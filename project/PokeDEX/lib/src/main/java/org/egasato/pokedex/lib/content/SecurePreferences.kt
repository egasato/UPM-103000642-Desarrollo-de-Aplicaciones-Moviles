package org.egasato.pokedex.lib.content

import android.content.Context
import android.content.SharedPreferences
import org.egasato.pokedex.lib.security.keystore.Keystore

/**
 * A secure implementation of shared preferences.
 *
 * Reads transparently-encrypted content from a shared preferences instance.
 *
 * @param context     The context.
 * @param preferences The shared preferences to wrap.
 * @param keystore    The keystore.
 * @param keys        The key names of the values that should be secure.
 * @author Esaú García Sánchez-Torija
 */
class SecurePreferences(
	@JvmField private val context: Context,
	@JvmField private val preferences: SharedPreferences,
	@JvmField private val keystore: Keystore,
	@JvmField private val keys: Set<String>
) : SharedPreferences by preferences {

	/**
	 * Reads all the fields from the shared preferences.
	 *
	 * @return All the fields.
	 */
	override fun getAll(): Map<String, *> {
		val reg = preferences.all
		val res = mutableMapOf<String, Any?>()
		reg.forEach { (k, v) -> res[k] = if (k in keys) keystore.decryptMessage(v.toString()) else v }
		return res
	}

	/**
	 * Reads a string from the shared preferences and forces it to be decrypted.
	 *
	 * @param key      The key.
	 * @param defValue The default value.
	 * @return The decrypted string.
	 */
	private fun getSecureString(key: String?, defValue: String?): String? {
		val reg = preferences.getString(key, null) ?: return defValue
		return keystore.decryptMessage(reg)
	}

	/**
	 * Reads a string from the shared preferences.
	 *
	 * @param key      The key.
	 * @param defValue The default value.
	 * @return The string.
	 */
	override fun getString(key: String?, defValue: String?): String? {
		return if (key in keys) preferences.getString(key, defValue) else getSecureString(key, defValue)
	}

	/**
	 * Reads a string set from the shared preferences and forces it to be decrypted.
	 *
	 * @param key       The key.
	 * @param defValues The default values.
	 * @return The decrypted string set.
	 */
	private fun getSecureStringSet(key: String?, defValues: Set<String>?): Set<String>? {
		val reg = preferences.getStringSet(key, null) ?: return defValues
		val res = mutableSetOf<String>()
		reg.forEach { str -> res.add(keystore.decryptMessage(str)) }
		return res
	}

	/**
	 * Reads a set of strings from the shared preferences.
	 *
	 * @param key       The key.
	 * @param defValues The default values.
	 * @return The string set.
	 */
	override fun getStringSet(key: String?, defValues: Set<String>?): Set<String>? {
		return if (key in keys) preferences.getStringSet(key, defValues) else getSecureStringSet(key, defValues)
	}

	/**
	 * Reads an integer from the shared preferences.
	 *
	 * @param key      The key.
	 * @param defValue The default value.
	 * @return The integer.
	 */
	override fun getInt(key: String?, defValue: Int): Int {
		if (key in keys) return preferences.getInt(key, defValue)
		val reg = getSecureString(key, null) ?: return defValue
		return reg.toInt()
	}

	/**
	 * Reads a long from the shared preferences.
	 *
	 * @param key      The key.
	 * @param defValue The default value.
	 * @return The long.
	 */
	override fun getLong(key: String?, defValue: Long): Long {
		if (key in keys) return preferences.getLong(key, defValue)
		val reg = getSecureString(key, null) ?: return defValue
		return reg.toLong()
	}

	/**
	 * Reads a float from the shared preferences.
	 *
	 * @param key      The key.
	 * @param defValue The default value.
	 * @return The float.
	 */
	override fun getFloat(key: String?, defValue: Float): Float {
		if (key in keys) return preferences.getFloat(key, defValue)
		val reg = getSecureString(key, null) ?: return defValue
		return reg.toFloat()
	}

	/**
	 * Reads a boolean from the shared preferences.
	 *
	 * @param key      The key.
	 * @param defValue The default value.
	 * @return The boolean.
	 */
	override fun getBoolean(key: String?, defValue: Boolean): Boolean {
		if (key in keys) return preferences.getBoolean(key, defValue)
		val reg = getSecureString(key, null) ?: return defValue
		return reg.toBoolean()
	}

	/**
	 * Creates an editor capable of writing encrypted content to the shared preferences.
	 *
	 * @return A secure editor.
	 */
	override fun edit() = Editor(context, preferences.edit(), keystore, keys)

	/**
	 * A secure implementation of shared preferences editor.
	 *
	 * Writes transparently-encrypted content from a shared preferences editor instance.
	 *
	 * @param context  The context.
	 * @param editor   The shared preferences editor to wrap.
	 * @param keystore The keystore.
	 * @param keys     The key names of the values that should be secure.
	 * @author Esaú García Sánchez-Torija
	 */
	class Editor(
		@JvmField private val context: Context,
		@JvmField private val editor: SharedPreferences.Editor,
		@JvmField private val keystore: Keystore,
		@JvmField private val keys: Set<String>
	) : SharedPreferences.Editor by editor {

		/**
		 * Writes a string to the shared preferences and forces it to be encrypted.
		 *
		 * @param key   The key.
		 * @param value The value.
		 * @return The editor.
		 */
		private fun setSecureString(key: String?, value: String?): SharedPreferences.Editor {
			if (value == null) editor else editor.putString(key, keystore.encryptMessage(value))
			return this
		}

		/**
		 * Writes a string to the shared preferences.
		 *
		 * @param key   The key.
		 * @param value The value.
		 * @return The editor.
		 */
		override fun putString(key: String?, value: String?): SharedPreferences.Editor {
			val data = if (value != null && key in keys) keystore.encryptMessage(value) else value
			editor.putString(key, data)
			return this
		}

		/**
		 * Writes a string set to the shared preferences.
		 *
		 * @param key    The key.
		 * @param values The values.
		 * @return The editor.
		 */
		override fun putStringSet(key: String?, values: Set<String>?): SharedPreferences.Editor {
			if (values == null) return this
			val res = mutableSetOf<String>()
			values.forEach { str -> res.add(keystore.encryptMessage(str)) }
			editor.putStringSet(key, res)
			return this
		}

		/**
		 * Writes an integer to the shared preferences.
		 *
		 * @param key   The key.
		 * @param value The value.
		 * @return The editor.
		 */
		override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
			if (key in keys) return setSecureString(key, value.toString())
			editor.putInt(key, value)
			return this
		}

		/**
		 * Writes a long to the shared preferences.
		 *
		 * @param key   The key.
		 * @param value The value.
		 * @return The editor.
		 */
		override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
			if (key in keys) return setSecureString(key, value.toString())
			editor.putLong(key, value)
			return this
		}

		/**
		 * Writes a float to the shared preferences.
		 *
		 * @param key   The key.
		 * @param value The value.
		 * @return The editor.
		 */
		override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
			if (key in keys) return setSecureString(key, value.toString())
			editor.putFloat(key, value)
			return this
		}

		/**
		 * Writes a boolean to the shared preferences.
		 *
		 * @param key   The key.
		 * @param value The value.
		 * @return The editor.
		 */
		override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
			if (key in keys) return setSecureString(key, value.toString())
			editor.putBoolean(key, value)
			return this
		}

	}

}
