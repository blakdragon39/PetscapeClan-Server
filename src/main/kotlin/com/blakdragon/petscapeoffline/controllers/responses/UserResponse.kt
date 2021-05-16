package com.blakdragon.petscapeoffline.controllers.responses

import com.blakdragon.petscapeoffline.models.User


class UserResponse(user: User) {
    val id = user.id
    val token = user.token
    val clanMembers = user.clanMembers
    val displayName = user.displayName
    val approved = user.isApproved
    val admin = user.isAdmin
    val superAdmin = user.isSuperAdmin
}
