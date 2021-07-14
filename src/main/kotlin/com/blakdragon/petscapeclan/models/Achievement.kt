package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.AchievementType

class Achievement(
    val type: AchievementType
) {
    fun toResponse(): AchievementResponse = AchievementResponse(
        type = type,
        label = type.label
    )
}

class AchievementResponse(
    val type: AchievementType,
    val label: String
)
