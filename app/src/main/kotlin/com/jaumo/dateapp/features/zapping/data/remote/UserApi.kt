package com.jaumo.dateapp.features.zapping.data.remote

import com.jaumo.dateapp.features.zapping.data.remote.dto.UserResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api/")
    suspend fun getUser(
        @Query("gender") gender: String? = "",
    ): UserResultDTO

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}