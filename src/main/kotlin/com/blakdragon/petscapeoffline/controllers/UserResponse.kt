package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.models.User


class UserResponse(user: User) {
    val id = user.id
    val token = user.token
    val clanMembers = user.clanMembers
    val displayName = user.displayName
    val approved = user.approved
    val admin = user.admin
    val superAdmin = user.superAdmin
}
