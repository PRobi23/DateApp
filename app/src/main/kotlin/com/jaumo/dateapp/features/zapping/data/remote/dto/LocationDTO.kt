package com.jaumo.dateapp.features.zapping.data.remote.dto

data class LocationDTO(
    val city: String,
    val coordinates: CoordinatesDTO,
    val country: String,
    val postcode: String,
    val state: String,
    val street: StreetDTO,
    val timezone: TimezoneDTO
)