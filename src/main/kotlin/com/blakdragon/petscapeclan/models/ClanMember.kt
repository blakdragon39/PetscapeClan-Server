package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.Rank
import com.blakdragon.petscapeclan.models.enums.RankResponse
import com.blakdragon.petscapeclan.utils.determinePossibleRank
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
    var points: Int,
    var alts: List<String> = listOf()
) {
    fun toResponse(): ClanMemberResponse = ClanMemberResponse(
        id = id,
        runescapeName = runescapeName,
        rank = rank.toResponse(),
        joinDate = joinDate,
        lastSeen = lastSeen,
        bossKc = bossKc,
        pets = pets.map { it.toResponse() },
        achievements = achievements.map { it.toResponse() },
        points = points,
        alts = alts,
        possibleRank = determinePossibleRank(this).toResponse()
    )

    fun hasAchievement(type: AchievementType): Boolean = achievements.any { it.type == type }
}

class ClanMemberRequest(
    val id: String?,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val pets: List<Pet>,
    val achievements: List<Achievement>,
    val alts: List<String>
)

class ClanMemberResponse(
    val id: String? = null,
    val runescapeName: String,
    val rank: RankResponse,
    val joinDate: LocalDate,
    val lastSeen: LocalDate?,
    val bossKc: Int,
    val pets: List<PetResponse>,
    val achievements: List<AchievementResponse>,
    val points: Int,
    val alts: List<String> = listOf(),
    val possibleRank: RankResponse
)
