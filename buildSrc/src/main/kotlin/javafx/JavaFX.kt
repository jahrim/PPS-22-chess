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

    /** @return the classifier for javafx artifacts. */
    fun getClassifier(): String = classifiers.getValue(OS.getType())
}
