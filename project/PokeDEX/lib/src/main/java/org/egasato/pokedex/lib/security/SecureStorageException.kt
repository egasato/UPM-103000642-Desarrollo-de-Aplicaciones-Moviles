package org.egasato.pokedex.lib.security

/**
 * Represents an exception that occurred while accessing the secure storage.
 *
 * @author Esaú García Sánchez-Torija
 */
sealed class SecureStorageException(message: String, cause: Throwable?) : Exception(message, cause)

/**
 * Represents an exception that occurred while accessing the keystore.
 *
 * @author Esaú García Sánchez-Torija
 */
class KeystoreException(message: String, cause: Throwable?) : SecureStorageException(message, cause)

/**
 * Represents an exception that occurred while performing a cryptographic operation.
 *
 * @author Esaú García Sánchez-Torija
 */
class CryptoException(message: String, cause: Throwable?) : SecureStorageException(message, cause)

/**
 * Represents an exception that occurred because a keystore is not supported.
 *
 * @author Esaú García Sánchez-Torija
 */
class KeystoreUnsupportedException(message: String, cause: Throwable?) : SecureStorageException(message, cause)

/**
 * Represents an exception that occurred internally.
 *
 * @author Esaú García Sánchez-Torija
 */
class SecureRuntimeException(message: String, cause: Throwable?) : SecureStorageException(message, cause)
