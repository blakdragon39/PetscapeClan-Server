package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.PetType

//stored in mongo
class Pet(
    val type: PetType
) {
    fun toResponse(): PetResponse = PetResponse(this)
}

//combines mongo data with type data
class PetResponse(pet: Pet) {
    val type: PetType = pet.type
    val label: String = pet.type.label
}
