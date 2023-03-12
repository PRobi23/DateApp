package com.jaumo.dateapp.features.filter.data.local.sharedpreferences

import com.jaumo.dateapp.features.zapping.domain.model.Gender

/***
 * Initialize shared preferences
 */
interface SharedPreferencesUtils {

    /***
     * Returns the saved gender from the shared preferences.
     */
    fun getSavedGender(): Gender

    /***
     * Save the given gender to the shared preferences.
     */
    fun saveGender(gender: Gender)
}