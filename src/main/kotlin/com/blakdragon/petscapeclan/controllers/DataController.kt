package com.blakdragon.petscapeclan.controllers

import com.blakdragon.petscapeclan.models.enums.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/data")
class DataController {

    @GetMapping("/achievements")
    fun getAchievements(): List<AchievementTypeResponse> {
        return AchievementType.values().map { it.toResponse() }
    }

    @GetMapping("/pets")
    fun getPets(): List<PetTypeResponse> {
        return PetType.values().map { it.toResponse() }
    }

    @GetMapping("/ranks")
    fun getRanks(): List<RankResponse> {
        return Rank.values().map { it.toResponse() }
    }
}
