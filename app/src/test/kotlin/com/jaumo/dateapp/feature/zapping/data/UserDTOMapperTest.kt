package com.jaumo.dateapp.feature.zapping.data

import com.google.common.truth.Truth
import com.jaumo.dateapp.UserGenerator
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.User
import org.junit.Test

class UserDTOMapperTest {

    @Test
    fun `userDTO mapping works properly`() {
        // given
        val userDTO = UserGenerator.generateUserResponseDTO()

        Truth.assertThat(userDTO.results.map {
            it.toUser()
        }).isEqualTo(
            listOf(
                User(
                    lastName = "Nichols",
                    userPicture = "https://randomuser.me/api/portraits/men/75.jpg",
                    age = 30,
                    thumbnail = "https://randomuser.me/api/portraits/thumb/men/75.jpg",
                    gender = Gender.FEMALE
                )
            )
        )
    }
}