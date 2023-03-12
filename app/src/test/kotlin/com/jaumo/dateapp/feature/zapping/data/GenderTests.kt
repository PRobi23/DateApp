package com.jaumo.dateapp.feature.zapping.data

import com.google.common.truth.Truth
import com.jaumo.dateapp.core.util.UiText
import org.junit.Test
import com.jaumo.dateapp.R
import com.jaumo.dateapp.features.zapping.domain.model.*


class GenderTests {

    @Test
    fun `when getPreposition is called with male the returned string is him`() {
        // given
        val gender = Gender.MALE

        // when
        val value = gender.getPreposition()

        // then
        Truth.assertThat(value).isEqualTo(UiText.StringResource(R.string.him))
    }

    @Test
    fun `when getPreposition is called with female the returned string is female`() {
        // given
        val gender = Gender.FEMALE

        // when
        val value = gender.getPreposition()

        // then
        Truth.assertThat(value).isEqualTo(UiText.StringResource(R.string.her))
    }

    @Test
    fun `when getPreposition is called with both the returned string is unkown`() {
        // given
        val gender = Gender.BOTH

        // when
        val value = gender.getPreposition()

        // then
        Truth.assertThat(value).isEqualTo(UiText.StringResource(R.string.unknown_gender))
    }

    @Test
    fun `when string is equal to female the gender is female`() {
        // given
        val gender = "female"

        // when
        val value = gender.toGender()

        // then
        Truth.assertThat(value).isEqualTo(Gender.FEMALE)
    }

    @Test
    fun `when string is equal to male the gender is male`() {
        // given
        val gender = "male"

        // when
        val value = gender.toGender()

        // then
        Truth.assertThat(value).isEqualTo(Gender.MALE)
    }

    @Test
    fun `when toCamelCase is called the gender is returned in camel case`() {
        // given
        val female = Gender.FEMALE
        val male = Gender.MALE
        val both = Gender.BOTH

        // when
        val femaleCamelCase = female.toCamelCase()
        val maleCamelCase = male.toCamelCase()
        val bothCamelCase = both.toCamelCase()

        // then
        Truth.assertThat(femaleCamelCase).isEqualTo("Female")
        Truth.assertThat(maleCamelCase).isEqualTo("Male")
        Truth.assertThat(bothCamelCase).isEqualTo("Both")
    }

    @Test
    fun `when toQueryPath is called on both value it returns empty string`() {
        // given
        val both = Gender.BOTH

        // when
       val queryPath = both.toQueryPath()

        // then
        Truth.assertThat(queryPath).isEqualTo("")
    }

    @Test
    fun `when toQueryPath is called on male value it returns male string`() {
        // given
        val male = Gender.MALE

        // when
        val queryPath = male.toQueryPath()

        // then
        Truth.assertThat(queryPath).isEqualTo("male")
    }


    @Test
    fun `when toQueryPath is called on female value it returns female string`() {
        // given
        val female = Gender.FEMALE

        // when
        val queryPath = female.toQueryPath()

        // then
        Truth.assertThat(queryPath).isEqualTo("female")
    }
}