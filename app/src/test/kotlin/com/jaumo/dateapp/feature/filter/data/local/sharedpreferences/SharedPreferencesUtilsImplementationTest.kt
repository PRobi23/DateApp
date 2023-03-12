package com.jaumo.dateapp.feature.filter.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.common.truth.Truth
import com.jaumo.dateapp.features.filter.data.local.sharedpreferences.SharedPreferencesUtilsImplementation
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SharedPreferencesUtilsImplementationTest {

    private val context: Context = mockk()
    private val preferences: SharedPreferences = mockk()

    private fun createSharedPreferencesUtilsImplementation() = SharedPreferencesUtilsImplementation(
        context
    )

    @Test
    fun `when saved gender is called gender both is returned by default`() {
        // given
        every {
            context.getSharedPreferences(
                SharedPreferencesUtilsImplementation.USER_PREFERENCES,
                Context.MODE_PRIVATE
            )
        } returns preferences

        every {
            preferences.getString(
                SharedPreferencesUtilsImplementation.SAVED_GENDER_KEY,
                Gender.BOTH.toString()
            )
        } returns null

        // when
        val savedGender = createSharedPreferencesUtilsImplementation().getSavedGender()

        // then
        Truth.assertThat(savedGender).isEqualTo(Gender.BOTH)
    }

    @Test
    fun `when female gender is saved then female gender is returned`() {
        // given
        every {
            context.getSharedPreferences(
                SharedPreferencesUtilsImplementation.USER_PREFERENCES,
                Context.MODE_PRIVATE
            )
        } returns preferences

        every {
            preferences.getString(
                SharedPreferencesUtilsImplementation.SAVED_GENDER_KEY,
                Gender.BOTH.toString()
            )
        } returns Gender.FEMALE.toString()

        // when
        val savedGender = createSharedPreferencesUtilsImplementation().getSavedGender()

        // then
        Truth.assertThat(savedGender).isEqualTo(Gender.FEMALE)
    }

    @Test
    fun `when male gender is saved then male gender is returned`() {
        // given
        every {
            context.getSharedPreferences(
                SharedPreferencesUtilsImplementation.USER_PREFERENCES,
                Context.MODE_PRIVATE
            )
        } returns preferences

        every {
            preferences.getString(
                SharedPreferencesUtilsImplementation.SAVED_GENDER_KEY,
                Gender.BOTH.toString()
            )
        } returns Gender.MALE.toString()

        // when
        val savedGender = createSharedPreferencesUtilsImplementation().getSavedGender()

        // then
        Truth.assertThat(savedGender).isEqualTo(Gender.MALE)
    }

    @Test
    fun `when save gender is called it is saved to shared preferences`() {
        // given
        val sharedPreferencesEditor: SharedPreferences.Editor = mockk()
        val genderToSave = Gender.FEMALE
        every {
            context.getSharedPreferences(
                SharedPreferencesUtilsImplementation.USER_PREFERENCES,
                Context.MODE_PRIVATE
            )
        } returns preferences

        every {
            preferences.edit()
        } returns sharedPreferencesEditor

        every {
            sharedPreferencesEditor.putString(
                SharedPreferencesUtilsImplementation.SAVED_GENDER_KEY, genderToSave.toString()
            )
        } returns sharedPreferencesEditor

        every {
            sharedPreferencesEditor.apply()
        } returns Unit

        // when
        createSharedPreferencesUtilsImplementation().saveGender(genderToSave)

        // then
        verify {
            sharedPreferencesEditor.putString(
                SharedPreferencesUtilsImplementation.SAVED_GENDER_KEY, genderToSave.toString()
            )
        }
        verify {
            sharedPreferencesEditor.apply()
        }
    }
}