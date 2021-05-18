package com.blakdragon.petscapeoffline.controllers.responses

import com.blakdragon.petscapeoffline.models.User


class UserResponse(user: User) {
    val id = user.id
    val token = user.token
    val clanMembers = user.clanMembers
    val displayName = user.displayName
    val isApproved = user.isApproved
    val isAdmin = user.isAdmin
    val isSuperAdmin = user.isSuperAdmin
}
