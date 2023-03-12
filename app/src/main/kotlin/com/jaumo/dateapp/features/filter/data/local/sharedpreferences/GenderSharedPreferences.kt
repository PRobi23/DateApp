package com.jaumo.dateapp.features.filter.data.local.sharedpreferences

import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.zapping.domain.model.Gender

/***
 * Gender from shared preferences
 */
data class GenderSharedPreferences(val gender: Gender) {
    fun toGender() = Filter(gender = gender)
}