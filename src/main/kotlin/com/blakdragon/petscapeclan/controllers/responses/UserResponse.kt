package com.blakdragon.petscapeclan.controllers.responses

import com.blakdragon.petscapeclan.models.User

class UserResponse(user: User) {
    val id = user.id
    val token = user.token
    val displayName = user.displayName
    val isAdmin = user.isAdmin
}
