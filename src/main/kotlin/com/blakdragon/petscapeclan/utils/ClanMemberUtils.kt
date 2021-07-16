package com.blakdragon.petscapeclan.utils

import com.blakdragon.petscapeclan.models.Achievement
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.WiseOldManPlayer
import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.Rank
import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min

const val MAX_POINTS_BOSSES = 30
const val MAX_POINTS_TIME = 24
const val MAX_TOTAL_LEVEL = 2277
const val MAX_COMBAT = 126

val wiseOldManClient: WebClient by lazy { WebClient.create("https://api.wiseoldman.net/") }

fun updateClanMemberStats(clanMember: ClanMember) {
    val wiseOldManPlayer = getWiseOldMan(clanMember.runescapeName)

    clanMember.bossKc = wiseOldManPlayer.totalBossKc()
    clanMember.achievements = addAchievements(clanMember, wiseOldManPlayer)
    clanMember.points = addUpPoints(clanMember)
}

fun determinePossibleRank(clanMember: ClanMember): Rank {
    return listOf(Rank.Dragon, Rank.Rune, Rank.Adamant, Rank.Mithril, Rank.Gold, Rank.Steel, Rank.Iron, Rank.Bronze)
        .firstOrNull { it.points <= clanMember.points } ?: Rank.Bronze
}

private fun getWiseOldMan(runescapeName: String): WiseOldManPlayer {
    try {
        return wiseOldManClient
            .get()
            .uri("/players/username/$runescapeName")
            .retrieve()
            .toEntity(WiseOldManPlayer::class.java)
            .block()
            ?.body ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Wise Old Man Error: response body null"
        )
    } catch (e: DecodingException) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Hiscores data not found for $runescapeName")
    } catch (e: WebClientResponseException) {
        throw ResponseStatusException(e.statusCode, "Wise Old Man error: ${e.responseBodyAsString}")
    }
}

private fun addAchievements(clanMember: ClanMember, wiseOldManPlayer: WiseOldManPlayer): List<Achievement> {
    val newAchievements = mutableListOf<Achievement>().apply { addAll(clanMember.achievements) }

    val totalLevel = wiseOldManPlayer.totalSkillLevel()

    newAchievements.addIfNotPresent(clanMember, AchievementType.Total1500, totalLevel >= 1500)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Total2000, totalLevel >= 2000)
    newAchievements.addIfNotPresent(clanMember, AchievementType.MaxCape, totalLevel >= MAX_TOTAL_LEVEL)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Combat126, wiseOldManPlayer.combatLevel >= MAX_COMBAT)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Base70Stats, wiseOldManPlayer.allSkillsOver(70))
    newAchievements.addIfNotPresent(clanMember, AchievementType.Base80Stats, wiseOldManPlayer.allSkillsOver(80))
    newAchievements.addIfNotPresent(clanMember, AchievementType.Base90Stats, wiseOldManPlayer.allSkillsOver(90))

    newAchievements.addIfNotPresent(clanMember, AchievementType.Clues600Beginner, wiseOldManPlayer.latestSnapshot.clue_scrolls_beginner.score >= 600)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Clues500Easy, wiseOldManPlayer.latestSnapshot.clue_scrolls_easy.score >= 500)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Clues400Medium, wiseOldManPlayer.latestSnapshot.clue_scrolls_medium.score >= 400)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Clues300Hard, wiseOldManPlayer.latestSnapshot.clue_scrolls_hard.score >= 300)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Clues200Elite, wiseOldManPlayer.latestSnapshot.clue_scrolls_elite.score >= 200)
    newAchievements.addIfNotPresent(clanMember, AchievementType.Clues100Master, wiseOldManPlayer.latestSnapshot.clue_scrolls_master.score >= 100)

    newAchievements.addIfNotPresent(clanMember, AchievementType.Skill50mExp, wiseOldManPlayer.anyExpOver(50_000_000))
    newAchievements.addIfNotPresent(clanMember, AchievementType.Skill100mExp, wiseOldManPlayer.anyExpOver(100_000_000))
    newAchievements.addIfNotPresent(clanMember, AchievementType.Skill200mExp, wiseOldManPlayer.anyExpOver(200_000_000))

    newAchievements.addIfNotPresent(clanMember, AchievementType.InfernalCape, wiseOldManPlayer.latestSnapshot.tzkal_zuk.kills >= 1)
    newAchievements.addIfNotPresent(clanMember, AchievementType.XericsCape, wiseOldManPlayer.latestSnapshot.chambers_of_xeric_challenge_mode.kills >= 100)
    newAchievements.addIfNotPresent(clanMember, AchievementType.SinhazaShroud, wiseOldManPlayer.latestSnapshot.theatre_of_blood.kills + wiseOldManPlayer.latestSnapshot.theatre_of_blood_hard_mode.kills >= 100)

    return newAchievements
}

private fun addUpPoints(clanMember: ClanMember): Int {
    val bossKcPoints = min(clanMember.bossKc / 1000, MAX_POINTS_BOSSES)
    val timePoints = min(ChronoUnit.MONTHS.between(clanMember.joinDate, LocalDate.now()).toInt(), MAX_POINTS_TIME)
    val petPoints = clanMember.pets.size
    val achievementPoints = clanMember.achievements.size

    return bossKcPoints + timePoints + petPoints + achievementPoints
}

private fun MutableList<Achievement>.addIfNotPresent(clanMember: ClanMember, type: AchievementType, obtained: Boolean) {
    if (!clanMember.hasAchievement(type) && obtained) {
        add(Achievement(type))
    }
}
