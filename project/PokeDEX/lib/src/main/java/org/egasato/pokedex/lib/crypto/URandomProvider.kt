package org.egasato.pokedex.lib.crypto

import java.security.Provider

/**
 * A [provider][Provider] that uses [/dev/urandom][URandomSpi].
 *
 * @see URandomSpi
 * @author Esaú García Sánchez-Torija
 */
internal class URandomProvider: Provider("LinuxPRNG", 1.0, "An RNG provider that uses /dev/urandom") {

	/** Sets the provider properties. */
	init {
		put("SecureRandom.SHA1PRNG", URandomSpi::class.java.name)
		put("SecureRandom.SHA1PRNG ImplementedIn", "Software")
	}

}
