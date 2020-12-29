package org.egasato.pokedex.lib.crypto

import android.os.Build
import android.os.Process
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.NoSuchAlgorithmException
import java.security.Provider
import java.security.SecureRandom
import java.security.Security

/**
 * Fixes the pseudo-random number generator on Android 4.3 and below (API <= 18).
 *
 * On Android 4.3 and below, the default pseudo-random number generator uses a low entropy source, which could affect
 * negatively the security of any application that needs cryptographically secure pseudo-random numbers. This class
 * contains a fix that must be applied as soon as possible, ideally when the application is created.
 *
 * @author Esaú García Sánchez-Torija
 */
object PRNG {

	/**
	 * Applies all the security fixes.
	 *
	 * @throws SecurityException If a fix is needed but could not be applied.
	 */
	fun fix() {
		applyOpenSSLFix()
		applySecureRandom()
	}

	/**
	 * Applies the fix for the OpenSSL pseudo-random number generator having low entropy (if needed).
	 *
	 * Uses reflection to access the native crypto API, set a new seed and checks if it worked by reading 1024 bytes
	 * from the classic Linux entropy source `/dev/urandom`.
	 *
	 * @throws SecurityException If the fix is needed but could not be applied.
	 */
	@Throws(SecurityException::class)
	private fun applyOpenSSLFix() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) return
		try {
			val native = Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto")
			val seed = native.getMethod("RAND_seed", ByteArray::class.java)
			seed.invoke(null, generateSeed())
			val load = native.getMethod("RAND_load_file", String::class.java, Long::class.javaPrimitiveType)
			val read = load.invoke(null, "/dev/urandom", 1024) as Int
			if (read != 1024) throw IOException("Unexpected number of bytes read from Linux PRNG: $read")
		} catch (e: Exception) {
			throw SecurityException("Failed to seed OpenSSL PRNG", e)
		}
	}

	/**
	 * Installs a Linux-backed pseudo-random number generator `SecureRandom` implementation as the default.
	 *
	 * Does nothing if the implementation is already the default or if there is not need to install the
	 * implementation.
	 *
	 * @throws SecurityException If the fix is needed but could not be applied.
	 */
	@Throws(SecurityException::class)
	private fun applySecureRandom() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) return
		val providers: Array<Provider>? = Security.getProviders("SecureRandom.SHA1PRNG")
		if (providers == null || providers.isEmpty() || providers[0]::class.java != URandomProvider::class.java) {
			Security.insertProviderAt(URandomProvider(), 1)
		}
		val rng1 = SecureRandom()
		val kls1 = rng1.provider::class.java
		if (URandomProvider::class.java != kls1) {
			throw SecurityException("SecureRandom backed by wrong the provider: $kls1")
		}
		val rng2 = try {
			SecureRandom.getInstance("SHA1PRNG")
		} catch (e: NoSuchAlgorithmException) {
			throw SecurityException("SHA1PRNG not available", e)
		}
		val kls2 = rng2.provider::class.java
		if (URandomProvider::class.java != kls2::class.java) {
			throw SecurityException("SecureRandom SHA1PRNG backed by wrong the provider: $kls2")
		}
	}

	/**
	 * Generates a device-specific and invocation-specific seed to be mixed into the Linux PRNG.
	 *
	 * @return The seed.
	 */
	internal fun generateSeed(): ByteArray {
		try {
			val byteStream = ByteArrayOutputStream()
			val dataStream = DataOutputStream(byteStream)
			dataStream.writeLong(System.currentTimeMillis())
			dataStream.writeLong(System.nanoTime())
			dataStream.writeInt(Process.myPid())
			dataStream.writeInt(Process.myUid())
			dataStream.write(getFingerprintAndSerial())
			dataStream.close()
			return byteStream.toByteArray()
		} catch (e: IOException) {
			throw SecurityException("Failed to generate seed", e)
		}
	}

	/**
	 * Gets the hardware serial number of this device.
	 *
	 * @return The serial number.
	 */
	private fun getDeviceSerialNumber(): String? {
		return try {
			Build::class.java.getField("SERIAL")[null] as String
		} catch (_: Exception) {
			null
		}
	}

	/**
	 * Generates a string containing the fingerprint and device serial number.
	 *
	 * @return The fingerprint and serial number.
	 */
	private fun getFingerprintAndSerial(): ByteArray {
		val result = StringBuilder()
		Build.FINGERPRINT?.let { result.append(it) }
		getDeviceSerialNumber()?.let { result.append(it) }
		try {
			return result.toString().toByteArray(charset("UTF-8"))
		} catch (e: UnsupportedEncodingException) {
			throw RuntimeException("UTF-8 encoding not supported") //NOPMD
		}
	}

}
