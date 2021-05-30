package com.blakdragon.petscapeclan.utils

import com.blakdragon.petscapeclan.models.Achievement
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.Pet
import com.blakdragon.petscapeclan.models.WiseOldManPlayer
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min

const val MAX_POINTS_BOSSES = 30
const val MAX_POINTS_TIME = 24

val wiseOldManClient: WebClient by lazy { WebClient.create("https://api.wiseoldman.net/") }

fun getWiseOldMan(runescapeName: String): WiseOldManPlayer? {
    try {
        return wiseOldManClient
            .get()
            .uri("/players/username/$runescapeName")
            .retrieve()
            .toEntity(WiseOldManPlayer::class.java)
            .block()
            ?.body
    } catch (e: WebClientResponseException) {
        throw ResponseStatusException(e.statusCode, "Wise Old Man error: ${e.responseBodyAsString}")
    }
}

fun getPossiblePoints(wiseOldMan: WiseOldManPlayer?, joinDate: LocalDate, pets: List<Pet>, achievements: List<Achievement>): Int {
    val bossKcPoints = if (wiseOldMan != null) min(wiseOldMan.totalBossKc() / 1000, MAX_POINTS_BOSSES) else 0
    val timePoints = min(ChronoUnit.MONTHS.between(joinDate, LocalDate.now()).toInt(), MAX_POINTS_TIME)
    val petPoints = pets.size
    val achievementPoints = achievements.size

    return bossKcPoints + timePoints + petPoints + achievementPoints
}
