package com.jaumo.dateapp.features.zapping.domain.usecase

import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.zapping.domain.model.User
import com.jaumo.dateapp.features.zapping.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get user
 */
class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Response<User>> {
        return userRepository.getUser()
    }
}