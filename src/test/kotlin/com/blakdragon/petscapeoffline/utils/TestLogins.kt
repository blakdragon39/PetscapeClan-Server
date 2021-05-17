package com.blakdragon.petscapeoffline.utils

import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.services.UserService
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
