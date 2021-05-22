package com.blakdragon.petscapeclan.utils

import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.WiseOldManPlayer
import com.blakdragon.petscapeclan.wiseoldman.wiseOldManClient
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min

const val MAX_POINTS_BOSSES = 30
const val MAX_POINTS_TIME = 24

fun ClanMember.possiblePoints(): Int {
    //todo catch WebClientResponseException
    val player = wiseOldManClient()
        .get()
        .uri("/players/username/${runescapeName}")
        .retrieve()
        .toEntity(WiseOldManPlayer::class.java)
        .block()
        ?.body ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found on Wise Old Man")

    val bossKcPoints = min(player.totalBossKc() / 1000, MAX_POINTS_BOSSES)
    val timePoints = min(ChronoUnit.MONTHS.between(joinDate, LocalDate.now()).toInt(), MAX_POINTS_TIME)
    val petPoints = pets.size
    val achievementPoints = achievements.size

    return bossKcPoints + timePoints + petPoints + achievementPoints
}
