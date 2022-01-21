package com.blakdragon.petscapeclan.controllers.requests

import com.blakdragon.petscapeclan.models.CustomSquare

class CustomBingoGameRequest(
    val name: String,
    val blackout: Boolean,
    val squares: List<CustomSquare>
)
