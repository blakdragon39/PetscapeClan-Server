package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.PetType

class Pet(
    val type: PetType
) {
    fun toResponse(): PetResponse = PetResponse(
        type = type,
        label = type.label
    )
}

class PetResponse(
    val type: PetType,
    val label: String
)
