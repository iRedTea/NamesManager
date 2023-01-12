package me.redtea.namesmanager.domain

import kotlinx.serialization.Serializable
import me.redtea.namesmanager.util.UUIDSerializer
import java.util.*

@Serializable
class UserDto(
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID,
    val name: String,
    var kills: Int,
    var gender: Gender
)