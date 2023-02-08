package utility

/**
 * OS utility functions.
 */
object OS {
    private val osName = System.getProperty("os.name")

    /** Type of operative system. */
    public enum class OSType { Linux, Mac, Windows }

    /** @return the string identifier of this operative system. */
    fun getString(): String = this.osName

    /** @return the type of operative system. */
    fun getType(): OSType = when {
        this.osName.startsWith("Linux") -> OSType.Linux
        this.osName.startsWith("Mac") -> OSType.Mac
        this.osName.startsWith("Windows") -> OSType.Windows
        else -> throw UnsupportedOSError("Could not infer the type of operative system.")
    }

    private data class UnsupportedOSError(override val message: String) : Error(message)
}
