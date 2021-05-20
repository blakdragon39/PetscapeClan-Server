package com.blakdragon.petscapeclan.utils

import com.blakdragon.petscapeclan.models.User
import com.blakdragon.petscapeclan.services.UserService
import java.util.*

class TestUserLogins(userService: UserService) {

    val admin: User = userService.insert(User(
        token = UUID.randomUUID().toString(),
        email = "testEmailAdmin",
        isAdmin = true
    ))

    val user1: User = userService.insert(User(
        token = UUID.randomUUID().toString(),
        email = "testEmailUser1"
    ))

    val user2: User = userService.insert(User(
        token = UUID.randomUUID().toString(),
        email = "testEmailUser2"
    ))
}
