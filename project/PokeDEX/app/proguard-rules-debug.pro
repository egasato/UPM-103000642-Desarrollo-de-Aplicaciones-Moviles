# To speed up build time disable all optimizations
-dontwarn **				                                                     # Don't warn about any file
-dontusemixedcaseclassnames		                                                 # Do not generate mixed-case class names (Windows friendly)
-target 1.8				                                                         # Target the JVM 1.8
-dontskipnonpubliclibraryclasses	                                             # Do not skip non-public classes from the libraries
-dontpreverify				                                                     # Do not pre-verify the processed class files
-verbose-optimizations !code/simplification/arithmetic,!code/allocation/variable # Do not simplify arithmetic operations nor the variables
-keep class **				                                                     # Keep all classes (do not modify them)
-keepclassmembers class *{*;}		                                             # Keep all class members (do not modify them)
-keepattributes *			                                                     # Keep all attributes (do not modify them)
