# Removes all the "android" logging calls
-assumenosideeffects class org.egasato.pokedex.lib.log.Logger {
	android(...);
}
