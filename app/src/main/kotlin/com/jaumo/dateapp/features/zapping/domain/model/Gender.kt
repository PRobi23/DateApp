package com.jaumo.dateapp.features.zapping.domain.model

/***
 * Enum class to represent the possible genders.
 */
enum class Gender {
    FEMALE, MALE
}

fun Gender.toProperStringFormat(): String {
    return if (this == Gender.MALE) {
        "him"
    } else {
        "her"
    }
}

fun String.
        toGender(): Gender {
    return if (this == "female") {
        Gender.FEMALE
    } else Gender.MALE
}