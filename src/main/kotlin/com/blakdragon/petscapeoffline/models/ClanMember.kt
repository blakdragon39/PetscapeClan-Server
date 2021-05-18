package com.blakdragon.petscapeoffline.models

import com.blakdragon.petscapeoffline.models.enums.IronmanItem
import com.blakdragon.petscapeoffline.models.enums.Pet
import com.blakdragon.petscapeoffline.models.enums.Rank
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
class ClanMember(
    @Id var id: String? = null,
    val userId: String?,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val lastActivity: LocalDate,
    val splitsM: Int,
    val infernalCape: Boolean,
    val pets: Set<Pet>,

    val ironMan: Boolean,
    val ironManItems: Set<IronmanItem>
)
