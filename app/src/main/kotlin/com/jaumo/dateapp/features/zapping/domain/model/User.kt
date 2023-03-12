package com.jaumo.dateapp.features.zapping.domain.model

/***
 * User model class
 */
data class User(
    val userPicture: String,
    val age: Int,
    val lastName: String,
    val thumbnail: String,
    val gender: Gender
)