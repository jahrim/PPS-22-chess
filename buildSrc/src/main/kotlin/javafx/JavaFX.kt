package javafx

import utility.OS
import utility.OS.OSType

/**
 * Extension for JavaFX.
 */
object JavaFX {
    private val classifiers: Map<OSType, String> = mapOf(
        OSType.Linux to "linux",
        OSType.Mac to "mac",
        OSType.Windows to "win",
    )

    /** @return all the possible classifiers for javafx artifacts. */
    fun getClassifiers(): Collection<String> = classifiers.values

    /** @return the classifier for javafx artifacts for this operative system. */
    fun getSpecificClassifier(): String = classifiers.getValue(OS.getType())
}
