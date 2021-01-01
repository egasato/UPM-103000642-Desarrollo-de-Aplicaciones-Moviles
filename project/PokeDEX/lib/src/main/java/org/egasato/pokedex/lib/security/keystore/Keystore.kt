package org.egasato.pokedex.lib.security.keystore

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import org.egasato.pokedex.lib.BuildConfig
import org.egasato.pokedex.lib.crypto.PRNG
import org.egasato.pokedex.lib.security.CryptoException
import org.egasato.pokedex.lib.security.KeystoreException
import org.egasato.pokedex.lib.security.KeystoreUnsupportedException
import org.egasato.pokedex.lib.security.SecureRuntimeException
import org.egasato.pokedex.lib.security.SecureStorageException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.UnrecoverableKeyException
import java.util.Calendar
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.security.auth.x500.X500Principal

/** The simple name of the class */
private val NAME = Keystore::class.java.simpleName

/** The alias of the key. */
private const val KEY_ALIAS = "PokeDEX"

/** The encryption algorithm of the key. */
private const val KEY_ENCRYPTION_ALGORITHM = "RSA"

/** The character set used when exchanging information. */
private const val KEY_CHARSET = "UTF-8"

/** The keystore name. */
private const val KEY_KEYSTORE_NAME = "AndroidKeyStore"

/** The cipher provider for Android 6.0 and above. */
private const val KEY_CIPHER_MARSHMALLOW_PROVIDER = "AndroidKeyStoreBCWorkaround"

/** The cipher provider for Android 5.0 and below. */
private const val KEY_CIPHER_JELLYBEAN_PROVIDER = "AndroidOpenSSL"

/** The key transformation algorithms. */
private const val KEY_TRANSFORMATION_ALGORITHM = "RSA/ECB/PKCS1Padding"

/** The x509 certificate information. */
private const val KEY_X509_PRINCIPAL = "CN=SecureDeviceStorage, O=PokeDEX, C=Spain"

/**
 * A keystore implementation to encrypt and decrypt data.
 *
 * @property context The context.
 * @author Esaú García Sánchez-Torija
 */
class Keystore(@JvmField val context: Context) {

	/** Checks if the keystore is supported based on the Android version. */
	val isSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2

	/**
	 * Encrypts a string.
	 *
	 * @param message The string.
	 * @throws SecureStorageException If an error occurs while accessing the keystore.
	 * @return The encrypted string.
	 */
	@Throws(SecureStorageException::class)
	fun encryptMessage(message: String): String {
		return try {
			val input = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				Cipher.getInstance(KEY_TRANSFORMATION_ALGORITHM, KEY_CIPHER_MARSHMALLOW_PROVIDER)
			} else {
				Cipher.getInstance(KEY_TRANSFORMATION_ALGORITHM, KEY_CIPHER_JELLYBEAN_PROVIDER)
			}
			input.init(Cipher.ENCRYPT_MODE, getPublicKey())
			val stream = ByteArrayOutputStream()
			val cipherStream = CipherOutputStream(stream, input)
			cipherStream.write(message.toByteArray(charset(KEY_CHARSET)))
			cipherStream.close()
			Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
		} catch (e: Exception) {
			throw KeystoreException(e.message!!, e)
		}
	}

	/**
	 * Decrypts a string.
	 *
	 * @param message The string.
	 * @throws SecureStorageException If an error occurs while decrypting the string.
	 * @return The decrypted string.
	 */
	@Throws(SecureStorageException::class)
	fun decryptMessage(message: String): String {
		return try {
			val output = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				Cipher.getInstance(KEY_TRANSFORMATION_ALGORITHM, KEY_CIPHER_MARSHMALLOW_PROVIDER)
			} else {
				Cipher.getInstance(KEY_TRANSFORMATION_ALGORITHM, KEY_CIPHER_JELLYBEAN_PROVIDER)
			}
			output.init(Cipher.DECRYPT_MODE, getPrivateKey())
			val stream = ByteArrayInputStream(Base64.decode(message, Base64.DEFAULT))
			val cipherInputStream = CipherInputStream(stream, output)
			val bytes = cipherInputStream.readBytes()
			String(bytes, 0, bytes.size, charset(KEY_CHARSET))
		} catch (e: Exception) {
			throw CryptoException(e.message!!, e)
		}
	}

	/**
	 * Checks if the keypair exists.
	 *
	 * @throws SecureStorageException If the keystore instance cannot be created.
	 * @return The existence check.
	 */
	@Throws(SecureStorageException::class)
	fun keyPairExists(): Boolean {
		return try {
			getKeyStoreInstance().getKey(KEY_ALIAS, null) != null
		} catch (e: NoSuchAlgorithmException) {
			throw KeystoreException(e.message!!, e)
		} catch (e: KeyStoreException) {
			false
		} catch (e: UnrecoverableKeyException) {
			false
		}
	}

	/** Generates a key pair if it does not exist. */
	@Throws(SecureStorageException::class)
	fun generateKeyPair() {
		if (!keyPairExists()) {
			when {
				Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
					generateKeyPairMarshmallow()
				Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 -> {
					if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) PRNG.fix()
					generateKeyPairJellyBean()
				}
				else -> throw KeystoreUnsupportedException("Secure storage is not available", null)
			}
		} else if (BuildConfig.DEBUG) {
			Log.e(NAME, "The key pair already exists")
		}
	}

	/**
	 * Returns the public key.
	 *
	 * @return The public key.
	 */
	@Throws(SecureStorageException::class)
	private fun getPublicKey(): PublicKey {
		try {
			if (keyPairExists()) {
				return getKeyStoreInstance().getCertificate(KEY_ALIAS).publicKey
			}
		} catch (e: Exception) {
			throw KeystoreException(e.message!!, e)
		}
		if (BuildConfig.DEBUG) Log.e(NAME, "The key pair does not exist")
		throw SecureRuntimeException("The key pair does not exist", null)
	}

	/**
	 * Returns the private key.
	 *
	 * @return The private key.
	 */
	@Throws(SecureStorageException::class)
	private fun getPrivateKey(): PrivateKey {
		try {
			val pair = getKeyStoreInstance().getKey(KEY_ALIAS, null)
			if (pair != null) return pair as PrivateKey
		} catch (e: Exception) {
			throw KeystoreException(e.message!!, e)
		}
		if (BuildConfig.DEBUG) Log.e(NAME, "They key pair does not exist")
		throw SecureRuntimeException("The key pair does not exist", null)
	}

	/** Generates the keypair using the API since Marshmallow. */
	@Throws(SecureStorageException::class)
	@RequiresApi(api = Build.VERSION_CODES.M)
	private fun generateKeyPairMarshmallow() {
		try {
			val generator = KeyPairGenerator.getInstance(KEY_ENCRYPTION_ALGORITHM, KEY_KEYSTORE_NAME)
			KeyGenParameterSpec
				.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
				.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
				.build().let {
					generator.initialize(it)
					generator.generateKeyPair()
				}
		} catch (e: Exception) {
			throw KeystoreException(e.message!!, e)
		}
	}

	/** Generates the keypair using the deprecated API available since Jelly Bean. */
	@Suppress("DEPRECATION")
	@Throws(SecureStorageException::class)
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
	private fun generateKeyPairJellyBean() {
		try {
			val start = Calendar.getInstance()
			val end = Calendar.getInstance().apply { add(Calendar.YEAR, 100) }
			val generator = KeyPairGenerator.getInstance(KEY_ENCRYPTION_ALGORITHM, KEY_KEYSTORE_NAME)
			android.security.KeyPairGeneratorSpec.Builder(context)
				.setAlias(KEY_ALIAS)
				.setSubject(X500Principal(KEY_X509_PRINCIPAL))
				.setSerialNumber(BigInteger.TEN)
				.setStartDate(start.time)
				.setEndDate(end.time)
				.build().let {
					generator.initialize(it)
					generator.generateKeyPair()
				}
		} catch (e: Exception) {
			throw KeystoreException(e.message!!, e)
		}
	}

	/**
	 * Creates a [keystore][KeyStore] instance.
	 *
	 * @throws SecureStorageException If the keystore instance cannot be created.
	 * @return The keystore.
	 */
	@Throws(SecureStorageException::class)
	private fun getKeyStoreInstance(): KeyStore {
		return try {
			KeyStore.getInstance(KEY_KEYSTORE_NAME).apply { load(null) }
		} catch (e: Exception) {
			throw KeystoreException(e.message!!, e)
		}
	}

}
