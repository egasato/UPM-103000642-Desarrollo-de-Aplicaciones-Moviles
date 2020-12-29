# Removes all the "cycle" logging calls
-assumenosideeffects class org.egasato.pokedex.lib.log.Logger {
	cycle(...);
}
