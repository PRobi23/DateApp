package com.jaumo.dateapp

import com.jaumo.dateapp.features.zapping.data.remote.dto.*
import com.jaumo.dateapp.features.zapping.domain.model.User
import kotlin.random.Random

object UserGenerator {

    private val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private fun randomWord(): String = List((1..10).random()) { alphabet.random() }.joinToString("")
    private fun randomAge(): Int = Random.nextInt(18, 55)

    fun generateUser() = User(
        lastName = randomWord(),
        age = randomAge(),
        userPicture = "https://" + randomWord()
    )

    fun generateUserResponseDTO() = UserResultDTO(
        results = listOf(
            UserDTO(

                gender = "female",
                name = NameDTO(
                    title = "Miss",
                    first = "Jennie",
                    last = "Nichols"
                ),
                location = LocationDTO(
                    street = StreetDTO(
                        number = 8929,
                        name = "Valwood Pkwy"
                    ),
                    city = "Billings",
                    state = "Michigan",
                    country = "United States",
                    postcode = "63104",
                    coordinates = CoordinatesDTO(
                        latitude = "-69.8246",
                        longitude = "134.8719"
                    ),
                    timezone = TimezoneDTO(
                        offset = "+9:30",
                        description = "Adelaide, Darwin"
                    )
                ),
                email = "jennie.nichols@example.com",
                login = LoginDTO(
                    uuid = "7a0eed16-9430-4d68-901f-c0d4c1c3bf00",
                    username = "yellowpeacock117",
                    password = "addison",
                    salt = "sld1yGtd",
                    md5 = "ab54ac4c0be9480ae8fa5e9e2a5196a3",
                    sha1 = "edcf2ce613cbdea349133c52dc2f3b83168dc51b",
                    sha256 = "48df5229235ada28389b91e60a935e4f9b73eb4bdb855ef9258a1751f10bdc5d"
                ),
                dob = DobDTO(
                    date = "1992-03-08T15:13:16.688Z",
                    age = 30
                ),
                registered = RegisteredDTO(
                    date = "2007-07-09T05:51:59.390Z",
                    age = 14
                ),
                phone = "(272) 790-0888",
                cell = "(489) 330-2385",
                id = IdDTO(

                    name = "SSN",
                    value = "405-88-3636"
                ),
                picture = PictureDTO(
                    large = "https://randomuser.me/api/portraits/men/75.jpg",
                    medium = "https://randomuser.me/api/portraits/med/men/75.jpg",
                    thumbnail = "https://randomuser.me/api/portraits/thumb/men/75.jpg"
                ),
                nat = "US"
            )
        ),
        info = InfoDTO(
            seed = "56d27f4a53bd5441",
            results = 1,
            page = 1,
            version = "1.4"
        )
    )
}