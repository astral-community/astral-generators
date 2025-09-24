package dev.tobynguyen27.astralgenerators.utils

import java.text.DecimalFormat
import java.util.Locale.getDefault

object StringHelper {
    /**
     * Capitalizes the first letter of a string.
     *
     * Example: `hello World` -> `Hello World`
     */
    fun capitalize(str: String): String = str.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() }

    /**
     * Convert string to readable English name
     *
     * Example: `steel_ingot` -> `Steel Ingot`
     */
    fun toEnglishName(str: String): String =
        str.lowercase(getDefault()).split("_").joinToString(" ") { it.replaceFirstChar { c -> c.uppercaseChar() } }

    /**
     * Converts a string to snake case. Handles acronyms and consecutive uppercase letters properly.
     *
     * Example:
     * - `HelloWorld` -> `hello_world`
     * - `PersonalID` -> `personal_id`
     */
    fun upperCamelToLowerUnderscore(string: String): String =
        string.replace(Regex("([a-z0-9])([A-Z])"), "$1_$2").replace(Regex("([A-Z])([A-Z][a-z])"), "$1_$2").lowercase()

    /** Converts an integer to a readable string with comma separators for thousands. */
    fun toReadableNumberString(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number)
    }
}