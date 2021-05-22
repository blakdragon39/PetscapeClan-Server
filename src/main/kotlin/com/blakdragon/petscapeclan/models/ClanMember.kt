package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.Rank
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
class ClanMember(
    @Id var id: String? = null,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val bossKc: Int,
    val pets: List<Pet>,
    val achievements: List<Achievement>,
    val points: Int
)

class ClanMemberPossiblePoints(
    clanMember: ClanMember,
    val possiblePoints: Int
) {
    val id = clanMember.id
    val rank = clanMember.rank
    val joinDate = clanMember.joinDate
    val bossKc = clanMember.bossKc
    val pets = clanMember.pets
    val achievements = clanMember.achievements
    val points = clanMember.points
}
