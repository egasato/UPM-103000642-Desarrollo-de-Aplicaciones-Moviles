package org.egasato.pokedex.lib.crypto

import android.util.Log
import java.io.DataInputStream
import java.io.File
import java.io.IOException
import java.security.SecureRandomSpi

/** The simple name of the class */
private val NAME = URandomSpi::class.java.simpleName

/**
 * A custom Linux-based [Secure Random Service Provider Interface][SecureRandomSpi].
 *
 * Passes all the requests to the Linux pseudo-random number generator `/dev/urandom`.
 *
 * @author Esaú García Sánchez-Torija
 */
class URandomSpi : SecureRandomSpi() {

	/** A companion object holding all the expensive static instances. */
	companion object {

		/** An object used as synchronization locking mechanism. */
		@JvmStatic
		private val lock = Any()

		/** The file descriptor of the `/dev/urandom` pseudo-random number generator. */
		@JvmStatic
		private val urandomFile by lazy { File("/dev/urandom") }

		/** The output stream of the [urandom][urandomFile] file descriptor. */
		@JvmStatic
		@get:Throws(IOException::class)
		private val urandomOutput by lazy { urandomFile.outputStream() }

		/** The input stream of the [urandom][urandomFile] file descriptor. */
		@JvmStatic
		@get:Throws(IOException::class)
		private val urandomInput by lazy { DataInputStream(urandomFile.inputStream()) }

	}

	/**
	 * Sets the seed of the generator.
	 *
	 * @param bytes The seed.
	 */
	override fun engineSetSeed(bytes: ByteArray?) {
		try {
			val stream = synchronized(lock) { urandomOutput }
			stream.write(bytes)
			stream.flush()
		} catch (e: IOException) {
			Log.w(NAME, "Failed to set the seed of $urandomFile", e)
		}
	}

	/**
	 * Generates random bytes.
	 *
	 * @param bytes The array where the bytes will be written into.
	 */
	override fun engineNextBytes(bytes: ByteArray?) {
		try {
			val stream = synchronized(lock) { urandomInput }
			synchronized(stream) { stream.readFully(bytes) }
		} catch (e: IOException) {
			throw SecurityException("Failed to read from $urandomFile", e)
		}
	}

	/**
	 * Generates a seed using the generator.
	 *
	 * @param size The size of the seed.
	 */
	override fun engineGenerateSeed(size: Int): ByteArray {
		return ByteArray(size).also { engineNextBytes(it) }
	}

}
