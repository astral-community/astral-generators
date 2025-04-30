package dev.tobynguyen27.astralgenerators.utils

import com.google.common.base.CaseFormat
import java.util.Locale

object FormattingUtil {
    fun toLowerCaseUnder(string: String): String {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string)
    }

    fun toEnglishName(name: String): String {
        return name.lowercase(Locale.ROOT).split("_").joinToString(" ") {
            it.replaceFirstChar { char -> char.uppercaseChar() }
        }
    }
}