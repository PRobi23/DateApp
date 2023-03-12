package com.jaumo.dateapp.features.zapping.domain.model

import com.jaumo.dateapp.R
import com.jaumo.dateapp.core.util.UiText

/***
 * Enum class to represent the possible genders.
 */
enum class Gender {
    FEMALE, MALE, BOTH
}

/***
 * Returns string in camel case
 */
fun Gender.toCamelCase() = this.toString().lowercase().replaceFirstChar { it.uppercaseChar() }

/**
 * Returns query path based on the gender
 */
fun Gender.toQueryPath(): String =
    if (this == Gender.BOTH) "" else this.toString().lowercase()


/***
 * Returns UI text to get a proper preposition.
 */
fun Gender.getPreposition(): UiText {
    return if (this == Gender.MALE) {
        UiText.StringResource(R.string.him)
    } else if (this == Gender.FEMALE) {
        UiText.StringResource(R.string.her)
    } else {
        UiText.StringResource(R.string.unknown_gender)
    }
}

/***
 * Make gender from string.
 */
fun String.toGender(): Gender {
    return if (this == "female") {
        Gender.FEMALE
    } else Gender.MALE
}