# Removes all the "getter" logging calls
-assumenosideeffects class org.egasato.pokedex.lib.log.Logger {
	getter(...);
}
