# Removes all the "setter" logging calls
-assumenosideeffects class org.egasato.pokedex.lib.log.Logger {
	setter(...);
}
