package com.jaumo.dateapp

import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.zapping.domain.model.Gender

object FilterGenerator {
    fun generateFilter() = Filter(gender = Gender.values().random())
}