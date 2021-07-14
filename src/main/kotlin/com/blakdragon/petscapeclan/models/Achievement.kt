package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.AchievementType

//stored in mongo
class Achievement(
    val type: AchievementType
) {
    fun toResponse(): AchievementResponse = AchievementResponse(this.type, this)
}

//combines mongo data with type data
class AchievementResponse(
    type: AchievementType,
    achievement: Achievement
) {
    val type: AchievementType = achievement.type
    val label: String = achievement.type.label
}
