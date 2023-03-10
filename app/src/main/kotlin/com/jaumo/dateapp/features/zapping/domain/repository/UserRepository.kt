package com.jaumo.dateapp.features.zapping.domain.repository

import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.zapping.domain.model.User
import kotlinx.coroutines.flow.Flow

/***
 * User repository interface. This interface is responsible for getting user related values.
 */
interface UserRepository {

    /**
     * Get user from network. First loading state is emitted and after that if the api calls succeeds the
     * user is emitted if it fails then error is propagated.
     * @return Single user from User API
     */
    fun getUser(): Flow<Response<User>>
}