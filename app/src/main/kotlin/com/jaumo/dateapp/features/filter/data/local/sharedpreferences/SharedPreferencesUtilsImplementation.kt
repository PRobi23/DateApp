package com.jaumo.dateapp.features.filter.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import dagger.hilt.android.qualifiers.ApplicationContext
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

/**
 * {@inheritDoc}
 */
class SharedPreferencesUtilsImplementation(context: Context) :
    SharedPreferencesUtils {

    companion object {
        @get:TestOnly
        const val USER_PREFERENCES = "user_preferences"

        @get:TestOnly
        const val SAVED_GENDER_KEY = "key_saved_gender"
    }

    var preferences: SharedPreferences =
        context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)

    /**
     * {@inheritDoc}
     */
    override fun getSavedGender(): Gender {
        return preferences.getString(SAVED_GENDER_KEY, Gender.BOTH.toString())?.let {
            Gender.valueOf(it)
        } ?: Gender.BOTH
    }

    /**
     * {@inheritDoc}
     */
    override fun saveGender(gender: Gender) {
        preferences.edit()
            .putString(SAVED_GENDER_KEY, gender.toString())
            .apply()
    }
}