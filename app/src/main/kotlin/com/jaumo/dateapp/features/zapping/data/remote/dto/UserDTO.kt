package com.jaumo.dateapp.features.zapping.data.remote.dto

import com.jaumo.dateapp.features.zapping.domain.model.User

data class UserDTO(
    val cell: String,
    val dob: DobDTO,
    val email: String,
    val gender: String,
    val id: IdDTO,
    val location: LocationDTO,
    val login: LoginDTO,
    val name: NameDTO,
    val nat: String,
    val phone: String,
    val picture: PictureDTO,
    val registered: RegisteredDTO
) {
    /**
     * Maps the DTO class to the domain class
     */
    fun toUser() = User(
        lastName = name.last,
        age = dob.age,
        userPicture = picture.large
    )
}