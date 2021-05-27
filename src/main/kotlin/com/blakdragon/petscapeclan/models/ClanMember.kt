package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.Rank
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
class ClanMember(
    @Id var id: String? = null,
    var runescapeName: String,
    var rank: Rank,
    var joinDate: LocalDate,
    var lastSeen: LocalDate?,
    var bossKc: Int,
    var pets: List<Pet>,
    var achievements: List<Achievement>,
    var points: Int
)

class ClanMemberRequest(
    val id: String?,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val pets: List<Pet>,
    val achievements: List<Achievement>
)
